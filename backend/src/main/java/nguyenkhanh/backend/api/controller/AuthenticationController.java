package nguyenkhanh.backend.api.controller;

import nguyenkhanh.backend.config.security.JwtTokenUtils;
import nguyenkhanh.backend.entity.*;
import nguyenkhanh.backend.exception.NotFoundException;
import nguyenkhanh.backend.request.LoginRequest;
import nguyenkhanh.backend.request.RegisterRequest;
import nguyenkhanh.backend.response.JwtResponse;
import nguyenkhanh.backend.response.MessageResponse;
import nguyenkhanh.backend.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AuthenticationController {
    @Autowired
    private IUserService userServiceImpl;

    @Autowired
    private IUserTypeService userTypeServiceImpl;

    @Autowired
    private IRegisterLogService registerLogServiceImpl;

    @Autowired
    private IRoleService roleServiceImpl;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @PostMapping(path = "/auth/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest) {

        try {
            if (userServiceImpl.isUserExitsByUsername(registerRequest.getUsername())) {
                return ResponseEntity.badRequest().body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.name(), "Email này đã được sử dụng, vui lòng thử email khác!"));
            }
            if (registerRequest.getPassword() == null) {
                return ResponseEntity.badRequest().body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.name(), "Mật khẩu không được để trống!"));
            }

            UserEntity userEntity = new UserEntity();

            // Set User type
            UserTypeEntity userTypeEntity = userTypeServiceImpl.findByKeyName("customer")
                    .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy loại người dùng!"));
            userEntity.setUserType(userTypeEntity);

            Set<RoleEntity> roleEntity = new HashSet<RoleEntity>();
            System.out.print(ERoles.CUSTOMER.toString().toLowerCase());
            RoleEntity userRole = roleServiceImpl.finByKeyName(ERoles.CUSTOMER.toString().toUpperCase())
                    .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy quyền này"));
            roleEntity.add(userRole);
            userEntity.setRoles(roleEntity);

            userEntity.setFullName(registerRequest.getFullName());
            userEntity.setUsername(registerRequest.getUsername());
            userEntity.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            userEntity.setStatus(EStatus.INACTIVE.toString());

            // Save
            userServiceImpl.save(userEntity);

            return ResponseEntity.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Bạn đã đăng ký thành công!"));
        } catch (DataAccessException ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.name(), "Tài khoản đã được sử dụng. Vui lòng thử tài khoản khác!"));
        }
    }

    @PostMapping(path = "/auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Xác thực username password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            // Set thông tin authentication vào Security Context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Tạo token
            String jwtToken = jwtTokenUtils.generateToken(authentication);

            // Truy xuất thông tin người dùng đang đặng nhập.
            UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();

            if (userDetailsImpl == null) {
                return ResponseEntity.badRequest().body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.name(), "Không tìm thấy người dùng!"));
            }

            List<String> roles = new ArrayList<String>();
            userDetailsImpl.getRoles().forEach(role -> {
                roles.add(role.getRoleName());
            });

            List<String> permissions = userDetailsImpl.getAuthorities().stream()
                    .map(permission -> permission.getAuthority()).collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(userDetailsImpl.getId(), userDetailsImpl.getUsername(),
                    userDetailsImpl.getFullName(), userDetailsImpl.getPhoneNumber(), userDetailsImpl.getDateOfBirth(),
                    userDetailsImpl.getAvatar(), userDetailsImpl.getGender(), userDetailsImpl.getStatus(), roles,
                    permissions, userDetailsImpl.getUserType().getUserTypeName(), jwtToken));

        } catch (BadCredentialsException ex) {
            MessageResponse message = new MessageResponse(new Date(), HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name(),
                    "Email hoặc mật khẩu của bạn không chính xác!");
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        } catch (DisabledException ex) {
            MessageResponse message = new MessageResponse(new Date(), HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name(),
                    "Tài khoản của bạn chưa được kích hoạt!");
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        } catch (LockedException ex) {
            MessageResponse message = new MessageResponse(new Date(), HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name(),
                    "Tài khoản của bạn đã bị khóa!");
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        } catch (CredentialsExpiredException ex) {
            MessageResponse message = new MessageResponse(new Date(), HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name(),
                    "Tài khoản của bạn chưa xác minh email!");
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }

    }

    @GetMapping(path = "auth/verifyEmail")
    public ResponseEntity<?> verifyEmail(@RequestParam(required = false) String token) throws TimeoutException {

        RegisterLogEntity registerLogEntity = registerLogServiceImpl.getToken(token)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy token"));

        LocalDateTime dateActive = registerLogEntity.getDateActive();

        if (dateActive.isBefore(LocalDateTime.now())
                && registerLogEntity.getStatus().equals(EStatus.INACTIVE.toString())) {
            throw new TimeoutException("Mã xác nhận của bạn đã hết hạn.");
        }

        if (registerLogServiceImpl.getStatus(token).equals(EStatus.ACTIVE.toString())) {
            return ResponseEntity
                    .ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Tài khoản của bạn đã được xác nhận!"));
        } else {
            registerLogServiceImpl.updateStatus(token);
            userServiceImpl.updateStatus(registerLogEntity.getUser().getUsername());
            return ResponseEntity.ok(new MessageResponse(new Date(), HttpStatus.CONTINUE.value(),
                    "Xác minh địa chỉ email thành công."));
        }
    }

}
