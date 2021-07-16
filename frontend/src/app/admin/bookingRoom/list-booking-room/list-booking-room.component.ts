import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {formatDate} from '@angular/common';
import {BookingRoomService} from 'src/app/_services/booking-room.service';
import {LoggerService} from 'src/app/_services/logger.service';

@Component({
    selector: 'app-list-booking-room',
    templateUrl: './list-booking-room.component.html',
    styleUrls: ['./list-booking-room.component.css']
})
export class ListBookingRoomComponent implements OnInit {
    collection: Array<any> = [];
    _page = 1;
    _itemsPage = 5;
    _success = '';

    // details
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
    totalCost = 0;
    status: string;

    constructor(
        private bookingRoomService: BookingRoomService,
        private route: ActivatedRoute,
        private router: Router,
        private logger: LoggerService
    ) {
    }

    ngOnInit(): void {
        this.bookingRoomService.getBookingRoomAll().subscribe((result) => {
            this.collection = result;
            this.logger.loggerData(this.collection);
        });
    }

    coverStringToDate(str: any): string {
        const date = formatDate(str, 'yyyy-MM-dd', 'en');
        const splitString = date.split('-');
        const reverseArray = splitString.reverse();
        const joinArray = reverseArray.join('/');
        return joinArray;
    }

    showDeltailsBooking(id: any): void {
        this.totalCost = 0;
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
            data.rooms.forEach(item => {
                if (item.discount) {
                    this.totalCost += (item.roomCost - item.discount);
                } else {
                    this.totalCost += item.roomCost;
                }
            });

        });
    }

    hiddenDeltailsBooking(): void {
        this.showDetails = true;
    }

    deleteBookingRoom(id: any): void {
        if (confirm('Bạn có chắc muốn xóa đơn đặt phòng này?')) {
            this.bookingRoomService.deleteBookingRoom(id).subscribe((result: any) => {
                for (let i = 0; i < this.collection.length; i++) {
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
            this.router.navigate(['/admin-page/list-bookingroom']);
        }, 2500);
    }

    confim(id): void {
        this._success = '';
        if (confirm('Xác nhận đơn đặt phòng?')) {
            this.bookingRoomService.cancelBookingRoomById(id, 'Đã nhận').subscribe((reponse) => {
                // tslint:disable-next-line:prefer-for-of
                for (let i = 0; i < this.collection.length; i++) {
                    if (this.collection[i].id === id) {
                        this.collection[i].status = 'Đã nhận';
                    }
                }
                this.showDetails = true;
                this._success = 'Xác nhận thành công!';
            });
        }

    }

    thanhtoan(id): void {
        if (confirm('Tổng tiền cần phải thanh toán là: ' + this.totalCost + ' VNĐ')) {
            this.bookingRoomService.cancelBookingRoomById(id, 'Đã thanh toán').subscribe((reponse) => {
                // tslint:disable-next-line:prefer-for-of
                for (let i = 0; i < this.collection.length; i++) {
                    if (this.collection[i].id === id) {
                        this.collection[i].status = 'Đã thanh toán';
                    }
                }
                this.showDetails = true;
                this._success = 'Thanh toán thành công!';
            });
        }
    }
}

