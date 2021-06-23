import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { formatDate } from '@angular/common';
import { BookingRoomService } from 'src/app/_services/booking-room.service';
import { LoggerService } from 'src/app/_services/logger.service';

@Component({
    selector: 'app-list-booking-room',
    templateUrl: './list-booking-room.component.html',
    styleUrls: ['./list-booking-room.component.css']
})
export class ListBookingRoomComponent implements OnInit {
    collection: Array<any> = [];
    _page: number = 1;
    _itemsPage = 5;
    _success = "";

    //details
    showDetails = true;
    collectionDetails: any;
    rooms: any;
    _id: number;
    fullName: string;
    email: string;
    dateOfBirth: any;
    phoneNumber: string;
    gender: string;
    checkInDate: string;
    checkOutDate: string;
    totalRoom: any;
    status: string;

    constructor(
        private bookingRoomService: BookingRoomService,
        private route: ActivatedRoute,
        private router: Router,
        private logger: LoggerService
    ) { }

    ngOnInit(): void {
        this.bookingRoomService.getBookingRoomAll().subscribe((result) => {
            this.collection = result;
            this.logger.loggerData(this.collection);
        });
    }

    coverStringToDate(str: any) {
        var date = formatDate(str, 'yyyy-MM-dd', 'en');
        var splitString = date.split("-")
        var reverseArray = splitString.reverse();
        var joinArray = reverseArray.join("/");
        return joinArray;
    }

    showDeltailsBooking(id: any) {
        this.showDetails = false;
        this.bookingRoomService.getBookingRoomById(id).subscribe((data) => {
            this.logger.loggerData(data);

            this._id = data.id;
            this.fullName = data.user.fullName;
            this.email = data.user.username;
            this.dateOfBirth = this.coverStringToDate(data.user.dateOfBirth);
            this.phoneNumber = data.user.phoneNumber;
            this.gender = data.user.gender;
            this.checkInDate = this.coverStringToDate(data.checkInDate);
            this.checkOutDate = this.coverStringToDate(data.checkOutDate);
            this.rooms = data.rooms;
            this.totalRoom = data.rooms.length;
            this.status = data.status;
        });
    }

    hiddenDeltailsBooking() {
        this.showDetails = true;
    }

    deleteBookingRoom(id: any) {
        if (confirm("Bạn có chắc muốn xóa đơn đặt phòng này?")) {
            this.bookingRoomService.deleteBookingRoom(id).subscribe((result: any) => {
                for (var i = 0; i < this.collection.length; i++) {
                    if (this.collection[i].id === id) {
                        this.collection.splice(i, 1);
                    }
                }
                this._success = result.message;
                this.hiddenDeltailsBooking();
                this.logger.logger(this._success);
            });
        }
        setTimeout(() => {
            this._success = '';
            this.router.navigate(['/admin-page/list-bookingroom'])
        }, 2500);
    }

    confim(id) {
        this._success = "";
        if (confirm("Xác nhận đơn đặt phòng?")) {
            this.bookingRoomService.cancelBookingRoomById(id, 'Đã nhận').subscribe((reponse) => {
                for (var i = 0; i < this.collection.length; i++) {
                    if (this.collection[i].id === id) {
                        this.collection[i].status = "Đã nhận";
                    }
                }
                this.showDetails = true;
                this._success = "Xác nhận thành công!";
            });
        }

    }
}

