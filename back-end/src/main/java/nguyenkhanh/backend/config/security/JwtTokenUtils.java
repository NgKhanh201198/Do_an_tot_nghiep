package nguyenkhanh.backend.config.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import nguyenkhanh.backend.services.UserDetailsImpl;

@Component
public class JwtTokenUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtils.class);

	@Value("${config.jwt.secretKey}")
	private String JWT_SECRETKEY;

	@Value("${config.jwt.expirationTime}")
	private int JWT_EXPIRATIONTIME;

	public String generateToken(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();// xác thực

		return Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + JWT_EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, JWT_SECRETKEY).compact();
	}

	// Lấy username từ jwt
	public String getUsernameFromJWT(String token) {
		return Jwts.parser().setSigningKey(JWT_SECRETKEY).parseClaimsJws(token).getBody().getSubject();
	}

	// Kiểm tra token
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(JWT_SECRETKEY).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}

}
