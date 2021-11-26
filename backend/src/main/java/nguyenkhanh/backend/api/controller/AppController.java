package nguyenkhanh.backend.api.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import nguyenkhanh.backend.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nguyenkhanh.backend.dto.CheckRoomEmptyDTO;
import nguyenkhanh.backend.dto.CountDTO;
import nguyenkhanh.backend.entity.BookingRoomEntity;
import nguyenkhanh.backend.entity.HotelEntity;
import nguyenkhanh.backend.entity.RoomEntity;
import nguyenkhanh.backend.entity.UserTypeEntity;
import nguyenkhanh.backend.services.impl.BookingRoomServiceImpl;
import nguyenkhanh.backend.services.impl.HotelServiceImpl;
import nguyenkhanh.backend.services.impl.RoomServiceImpl;
import nguyenkhanh.backend.services.impl.UserServiceImpl;
import nguyenkhanh.backend.services.impl.UserTypeServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AppController {
    @Autowired
    IUserService userService;

    @Autowired
    IHotelService hotelService;

    @Autowired
    IBookingRoomService bookingRoomService;

    @Autowired
    IRoomService roomService;

    @Autowired
    IUserTypeService userTypeService;

    @PostMapping("/checkRoomEmpty")
    public ResponseEntity<?> checkRoomEmpty(@RequestBody @Valid CheckRoomEmptyDTO roomEmptyDTO) {

        Set<String> listRoomNameBookingrate = new HashSet<String>();//danh sách phòng đã đặt
        Common common = new Common();
        Date checkInDate = common.stringToDate(roomEmptyDTO.getCheckInDate());
        Date checkOutDate = common.stringToDate(roomEmptyDTO.getCheckOutDate());

        List<BookingRoomEntity> listBookingrate = bookingRoomService.getBookingRoomAll();
        listBookingrate.forEach(item -> {
            if (item.getStatus().equals("Đã đặt") && (checkInDate.before(item.getCheckOutDate()) || item.getCheckInDate().after(checkOutDate))) {
                item.getRooms().forEach(room -> {
                    if (room.getHotel().getHotelName().equals(roomEmptyDTO.getHotel())) {
                        listRoomNameBookingrate.add(room.getRoomNumber());
                    }
                });
            }
        });

        HotelEntity hotelEntity = hotelService.findByHotelName(roomEmptyDTO.getHotel()).orElseThrow(
                () -> new UsernameNotFoundException("Không tìm thấy khách sạn: " + roomEmptyDTO.getHotel()));

        List<RoomEntity> listRoomEtity = roomService.getRoomByHotel(hotelEntity);

        for (int i = 0; i < listRoomEtity.size(); i++) {
            if (listRoomNameBookingrate.contains(listRoomEtity.get(i).getRoomNumber())) {
                listRoomEtity.remove(i);
                i--;
            }
        }
        return new ResponseEntity<>(listRoomEtity, HttpStatus.OK);
    }


    @GetMapping("/count")
    public ResponseEntity<CountDTO> countCustomer() {
        UserTypeEntity userTypeEntity = userTypeService.findByKeyName("customer")
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy loại người dùng!"));
        long customer = userService.countByUserType(userTypeEntity);
        long order = bookingRoomService.countByStatus("Đã đặt");
        long hotel = hotelService.countHotelAll();
        long room = roomService.countRoomAll();

        CountDTO countDTO = new CountDTO();
        countDTO.setCustomer(customer);
        countDTO.setOrder(order);
        countDTO.setHotel(hotel);
        countDTO.setRoom(room);

        return new ResponseEntity<CountDTO>(countDTO, HttpStatus.OK);
    }


}
