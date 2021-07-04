import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {ThemePalette} from '@angular/material/core';
import {ActivatedRoute, Router} from '@angular/router';
import {User} from 'src/app/_models/user';
import {AuthenticationService} from 'src/app/_services/authentication.service';
import {BookingRoomService} from 'src/app/_services/booking-room.service';
import {HotelService} from 'src/app/_services/hotel.service';
import {LoggerService} from 'src/app/_services/logger.service';
import {RoomService} from 'src/app/_services/room.service';
import * as moment from 'moment';

@Component({
    selector: 'app-room',
    templateUrl: './room.component.html',
    styleUrls: ['./room.component.css']
})
export class RoomComponent implements OnInit {
    currentUser: User;
    listRoom: Array<any> = [];
    _page: any = 1;
    _itemsPage: any = 4;
    _errorCheckRoom: any;
    _minCheckInDate: Date = new Date();
    _minCheckOutDate: Date = new Date();
    _status = true;
    _success: String = '';
    _error: String = '';


    // hotel
    idHotel: any;
    hotel = [];
    hotelName: any;
    address: any;
    image: any;
    description: any;

    formData = this.formBuilder.group({
        checkInDate: ['', [Validators.required]],
        checkOutDate: ['', [Validators.required]],
    });

    constructor(
        private formBuilder: FormBuilder,
        private roomService: RoomService,
        private hotelService: HotelService,
        private bookingRoomService: BookingRoomService,
        private authenticationService: AuthenticationService,
        private route: ActivatedRoute,
        private router: Router,
        private loggerService: LoggerService
    ) {
    }

    ngOnInit(): void {
        this.currentUser = this.authenticationService.currentUserValue;

        this.route.queryParamMap
            .subscribe((params) => {
                this.hotelName = params.get('hotelName');
                this.formData.get('checkInDate').setValue(params.get('checkInDate'));
                this.formData.get('checkOutDate').setValue(params.get('checkOutDate'));

                this.hotelService.getHotelbyHotelName(this.hotelName).subscribe((result) => {
                    this.hotelName = result.hotelName;
                    this.image = result.image;
                    this.address = result.address;
                    this.description = result.description;

                    this.roomService.getRoomByHotel(result.hotelName).subscribe((result) => {
                        if (result.length === 0) {
                            this._errorCheckRoom = ' Khách sạn ' + this.hotelName + ' hiện tại chưa có phòng!';
                        } else {
                            this.listRoom = result;
                        }
                    });
                });
            });


    }

    // Invalid error message
    get formValid() {
        return this.formData.controls;
    }

    getCheckInDateErrorMessage(): string {
        if (this.formValid.checkInDate.errors.required) {
            return 'Vui lòng nhập ngày nhận phòng.';
        }
        return 'Vui lòng nhập ngày nhận phòng hợp lệ.';
    }

    getCheckOutDateErrorMessage(): string {
        if (this.formValid.checkOutDate.errors.required) {
            return 'Vui lòng nhập ngày trả phòng.';
        }
        return 'Vui lòng nhập ngày trả phòng hợp lệ.';
    }

    changeCheckInDate(even): void {
        const date = new Date(even.value);
        date.setDate(even.value.toDate().getDate() + 1);
        this._minCheckOutDate = date;
        this.formData.get('checkOutDate').setValue(moment(date).utc().format());
    }

    // Check box
    listRooms: any = [];
    listId: any = [];
    numberOfPeople = 0;
    color: ThemePalette = 'primary';
    allComplete: boolean = false;

    // Kiểm tra id nào đã được checked
    isCheckSelectedId(): void {
        this.listRooms = [];
        this.listId = [];
        this.numberOfPeople = 0;
        this.listRoom.forEach(element => {
            if (element.isSelected) {
                this.listRooms.push(element.roomNumber);
                this.listId.push(element.id);
                this.numberOfPeople = this.numberOfPeople + element.numberOfPeople;
            }
        });
    }

    updateAllComplete(): void {
        this.allComplete = this.listRoom != null && this.listRoom.every(t => t.isSelected);
        this.isCheckSelectedId();
    }

    someComplete(): boolean {
        if (this.listRoom == null) {
            return false;
        }
        return this.listRoom.filter(t => t.isSelected).length > 0 && !this.allComplete;
    }

    // end

    onSubmit(): void {
        this.loggerService.logger(this.listRooms);

        this.listRooms = [];
        this._status = true;
        this._errorCheckRoom = '';


        this.formData.get('checkInDate').setValue(moment(this.formData.value.checkInDate).utc().format());
        this.formData.get('checkOutDate').setValue(moment(this.formData.value.checkOutDate).utc().format());

        this.roomService.checkRoomEmpty(
            this.hotelName,
            this.formData.value.checkInDate,
            this.formData.value.checkOutDate
        ).subscribe({
            next: (result) => {
                // this.loggerService.logger(result);
                if (result.length === 0) {
                    this._errorCheckRoom = ' Khách sạn ' + this.hotelName + ' hết phòng trong thời gian này!';
                } else {
                    this._status = false;
                    this.listRoom = result;
                }
            },
            error: (error) => {
                this.loggerService.loggerError(error);
            }
        });
    }

    bookingRoom(): void {
        console.log(this._status);
        this.loggerService.loggerData(this.listRooms);

        this._success = '';
        if (this.listRooms.length === 0) {
            alert('Vui lòng chọn phòng.');
        } else {
            if (this.currentUser == null) {
                if (confirm('Bạn cần đăng nhập để tiếp tục...?')) {
                    this.router.navigate(['/log-in']);
                }
            } else {
                if (this._status) {
                    alert('Vui lòng chọn kiểm tra phòng trống trước khi đặt phòng.');
                } else {
                    this.bookingRoomService.createBookingRoom(
                        this.formData.value.checkInDate,
                        this.formData.value.checkOutDate,
                        this.numberOfPeople,
                        this.currentUser.email,
                        this.hotelName,
                        this.listRooms
                    ).subscribe((result) => {
                        this.loggerService.loggerData(this.listRoom.length);
                        for (let i = 0; i < this.listRoom.length; i++) {
                            this.loggerService.loggerData(this.listRoom[i].roomNumber);
                            if (this.listRooms.indexOf(this.listRoom[i].roomNumber) !== -1) {
                                this.listRoom.splice(i, 1);
                                i--;
                            }
                        }
                        this.listRooms = [];
                        this._success = 'Đặt phòng thành công.';
                    });
                }
            }
        }

    }

}
