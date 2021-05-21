import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, NgForm, Validators } from '@angular/forms';
import { LoggerService } from 'src/app/_services/logger.service';
import { Location } from '@angular/common'
import { Options } from 'src/app/_models/options';
import { RoomService } from 'src/app/_services/room.service';
import { HotelService } from 'src/app/_services/hotel.service';
import { UserService } from 'src/app/_services/user.service';
import { BookingrateService } from '../../../_services/bookingrate.service';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
@Component({
    selector: 'app-create-bookingrate',
    templateUrl: './create-bookingrate.component.html',
    styleUrls: ['./create-bookingrate.component.css']
})
export class CreateBookingrateComponent implements OnInit {
    @ViewChild('myForm') myForm: NgForm;
    listUsers = [];
    listHotels: Array<Options> = [];
    listRooms: Array<Options> = [];
    filteredOptions: any;
    _minDate: Date = new Date();
    _id: number;
    _success: String = "";
    _error: String = "";
    _selectRoom = true;
    _selectHotel = true;
    _hotel = null;
    _checkInDate = null;
    _checkOutDate = null;

    formUpdateData = this.formBuilder.group({
        user: ['', [Validators.required]],
        hotel: ['', [Validators.required,]],
        rooms: ['', [Validators.required,]],
        checkInDate: ['', [Validators.required,]],
        checkOutDate: ['', [Validators.required,]],
    });

    constructor(
        private formBuilder: FormBuilder,
        private location: Location,
        private bookingrateService: BookingrateService,
        private roomService: RoomService,
        private userService: UserService,
        private hotelService: HotelService,
        private loggerService: LoggerService,
    ) { }


    filterData(enteredData) {
        this.filteredOptions = this.listUsers.filter(item => {
            return item.toLowerCase().indexOf(enteredData.toLowerCase()) > -1
        })
    }

    ngOnInit(): void {
        this.userService.getAllUser().subscribe((result: any) => {
            for (let index = 0; index < result.length; index++) {
                if (result[index].userType.keyName === "customer") {
                    this.listUsers.push(result[index].fullName);
                }
            }
            this.filteredOptions = this.listUsers;
        });

        this.hotelService.getHotelAll().subscribe((result: any) => {
            for (let index = 0; index < result.length; index++) {
                let hotel = new Options(result[index].hotelName, result[index].hotelName);
                this.listHotels.push(hotel);
            }
        });

        this.formUpdateData = this.formBuilder.group({
            user: ['', [Validators.required]],
            hotel: ['', [Validators.required,]],
            rooms: ['', [Validators.required,]],
            checkInDate: ['', [Validators.required,]],
            checkOutDate: ['', [Validators.required,]],
        });

        this.formUpdateData.get('user').valueChanges.subscribe(response => {
            this.loggerService.loggerData('data: ' + response);
            this.filterData(response);
        });


    }

    //Invalid error message
    get formValid() { return this.formUpdateData.controls; }

    getUserErrorMessage(): string {
        if (this.formValid.user.errors.required) {
            return 'Vui lòng chọn khách hàng.';
        }
        return '';
    }
    getHotelErrorMessage(): string {
        if (this.formValid.hotel.errors.required) {
            return 'Vui lòng sạn khách sạn.';
        }
        return '';
    }
    getRoomsErrorMessage(): string {
        if (this.formValid.rooms.errors.required) {
            return 'Vui lòng chọn phòng.';
        }
        return '';
    }

    getCheckDateErrorMessage(): string {
        return 'Vui lòng nhập ngày nhận và ngày trả.';
    }

    checkInDate(event: MatDatepickerInputEvent<Date>) {
        this._checkInDate = event.value;

    }
    checkOutDate(event: MatDatepickerInputEvent<Date>) {
        this._selectHotel = false;
        this._checkOutDate = event.value;
    }

    onSelectHotel(event) {
        this._selectRoom = false;
        this._hotel = event.value;
        this.listRooms = [];
        this.formUpdateData.get('rooms').setValue('');

        this.roomService.getRoomAll().subscribe((result: any) => {
            for (let index = 0; index < result.length; index++) {
                this.loggerService.loggerData(result[index]);
                if (result[index].hotel.hotelName == this._hotel) {
                    let room = new Options(result[index].roomNumber, result[index].roomNumber);
                    this.listRooms.push(room);
                }
            }
        });
    }

    comeBack() {
        this.location.back();
    }

    onSubmit() {
        this.loggerService.logger(this.formUpdateData.value);
        this.bookingrateService.createBookingrate(this.formUpdateData.value)
            .subscribe({
                next: (result) => {
                    this._error = '';
                    this.myForm.resetForm();
                    this._success = result.message;
                    this.loggerService.logger(result);
                },
                error: (error) => {
                    this._error = error.message;
                    this.loggerService.loggerError(error.message);
                }
            }),
            setTimeout(() => {
                this._success = '';
            }, 3000);
    }
}
