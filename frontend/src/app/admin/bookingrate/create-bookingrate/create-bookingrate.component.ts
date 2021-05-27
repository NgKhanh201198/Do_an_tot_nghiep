import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, NgForm, Validators } from '@angular/forms';
import { LoggerService } from 'src/app/_services/logger.service';
import { Location } from '@angular/common'
import { Options } from 'src/app/_models/options';
import { RoomService } from 'src/app/_services/room.service';
import { HotelService } from 'src/app/_services/hotel.service';
import { UserService } from 'src/app/_services/user.service';
import { BookingrateService } from '../../../_services/bookingrate.service';
import { Router } from '@angular/router';
import { ThemePalette } from '@angular/material/core';
@Component({
    selector: 'app-create-bookingrate',
    templateUrl: './create-bookingrate.component.html',
    styleUrls: ['./create-bookingrate.component.css']
})
export class CreateBookingrateComponent implements OnInit {
    @ViewChild('myForm') myForm: NgForm;
    collection = [];
    listUsers = [];
    listHotels: Array<Options> = [];
    filteredOptions;
    _minDate: Date = new Date();
    _id: number;
    _success: String = "";
    _error: String = "";
    _errorCheckRoom: String = "";
    _selectRoom = false;

    formUpdateData = this.formBuilder.group({
        user: ['', [Validators.required]],
        hotel: ['', [Validators.required,]],
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
        private loggerService: LoggerService
    ) {

    }


    filterData(enteredData) {
        this.filteredOptions = this.listUsers.filter(item => {
            this.loggerService.logger(item);
            return item.toLowerCase().indexOf(enteredData.toLowerCase()) > -1
        })
    }

    ngOnInit() {
        this.initForm();
        this.getNameCustomer();

        this.hotelService.getHotelAll().subscribe((result: any) => {
            for (let index = 0; index < result.length; index++) {
                let hotel = new Options(result[index].hotelName, result[index].hotelName);
                this.listHotels.push(hotel);
            }
        });

        console.log(this.formUpdateData.value);
        
    }

    initForm() {
        this.formUpdateData = this.formBuilder.group({
            user: ['', [Validators.required]],
            hotel: ['', [Validators.required,]],
            checkInDate: ['', [Validators.required,]],
            checkOutDate: ['', [Validators.required,]],
        });

        this.formUpdateData.get('user').valueChanges.subscribe(response => {
            this.loggerService.loggerData('data: ' + response);
            this.filterData(response);
        });
    }

    getNameCustomer() {
        this.userService.getNameCustomer().subscribe(response => {
            this.listUsers = response;
            this.filteredOptions = response;
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

    //Check box
    listRooms: any = [];
    color: ThemePalette = 'primary';
    allComplete: boolean = false;

    //Kiểm tra id nào đã được checked
    isCheckSelectedId() {
        this.listRooms = [];
        this.collection.forEach(element => {
            if (element.isSelected) {
                this.listRooms.push(element.roomNumber);
            }
        });
    }

    updateAllComplete() {
        this.allComplete = this.collection != null && this.collection.every(t => t.isSelected);
        this.isCheckSelectedId();
    }
    someComplete(): boolean {
        if (this.collection == null) {
            return false;
        }
        return this.collection.filter(t => t.isSelected).length > 0 && !this.allComplete;
    }

    comeBack() {
        this.location.back();
    }

    onSubmit() {
        this._errorCheckRoom = "";
        this.collection = [];
        this._selectRoom = false;
        this.loggerService.logger(this.formUpdateData.value);

        if (this.formUpdateData.value.user == "" || this.formUpdateData.value.checkInDate == "" || this.formUpdateData.value.checkOutDate == "") {
            this.collection = [];
        } else {
            this.roomService.checkRoomEmpty(
                this.formUpdateData.value.hotel,
                this.formUpdateData.value.checkInDate,
                this.formUpdateData.value.checkOutDate
            ).subscribe({
                next: (result) => {
                    this.loggerService.logger(result);
                    if (result.length === 0) {
                        this._errorCheckRoom = " Khách sạn " + this.formUpdateData.value.hotel + " hết phòng trong thời gian này";
                    } else {
                        this._selectRoom = true;
                        this.collection = result;
                    }
                },
                error: (error) => {
                    this.loggerService.loggerError(error);
                }
            });
        }
    }

    bookingRoom() {

        this.bookingrateService.createBookingrate(
            this.formUpdateData.value.checkInDate,
            this.formUpdateData.value.checkOutDate,
            this.formUpdateData.value.user,
            this.formUpdateData.value.hotel,
            this.listRooms
        )
            .subscribe({
                next: (result) => {
                    this._success = result.message;
                    this.collection = [];
                    this._selectRoom = false;
                },
                error: (error) => {
                    this._error = error.message
                }
            }),
            setTimeout(() => {
                this._success = '';
                this.comeBack();
            }, 2500);
    }
}
