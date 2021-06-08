import { formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/_models/user';
import { AuthenticationService } from 'src/app/_services/authentication.service';
import { BookingRoomService } from 'src/app/_services/booking-room.service';
import { LoggerService } from 'src/app/_services/logger.service';

@Component({
    selector: 'app-booking-room',
    templateUrl: './booking-room.component.html',
    styleUrls: ['./booking-room.component.css']
})
export class BookingRoomComponent implements OnInit {
    currentUser: User;
    collection: Array<any> = [];
    _success = '';
    _page: number = 1;
    _itemsPage: number = 5;
    _change = false;

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

    constructor(
        private authenticationService: AuthenticationService,
        private bookingRoomService: BookingRoomService,
        private logger: LoggerService
    ) { }

    ngOnInit(): void {
        this.currentUser = this.authenticationService.currentUserValue;

        this.bookingRoomService.getBookingRoomByUser(this.currentUser.fullName).subscribe((result) => {
            this.collection = result;
            this.logger.loggerData(this.collection);
        });
    }

    showDeltailsBooking(id: any) {
        this.showDetails = false;
        this.bookingRoomService.getBookingRoomById(id).subscribe((data) => {
            this.fullName = data.user.fullName;
            this.email = data.user.username;
            this.dateOfBirth = this.coverStringToDate(data.user.dateOfBirth);
            this.phoneNumber = data.user.phoneNumber;
            this.gender = data.user.gender;
            this.checkInDate = this.coverStringToDate(data.checkInDate);
            this.checkOutDate = this.coverStringToDate(data.checkOutDate);
            this.rooms = data.rooms;
            this.totalRoom = data.rooms.length;
        });
    }

    coverStringToDate(str: any) {
        var date = formatDate(str, 'yyyy-MM-dd', 'en');
        var splitString = date.split("-")
        var reverseArray = splitString.reverse();
        var joinArray = reverseArray.join("/");
        return joinArray;
    }

    hiddenDeltailsBooking() {
        this.showDetails = true;
    }

    cancelBookingRoom(id) {
        if (confirm("Bạn có chắc muốn hủy đơn này?")) {
            this.bookingRoomService.cancelBookingRoomById(id).subscribe((reponse) => {
                this._change = true;
                this._success = reponse.message
            });
        }
    }
}
