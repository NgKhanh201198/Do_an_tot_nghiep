import { Component, OnInit } from '@angular/core';
import { User } from '../_models/user';
import { AuthenticationService } from '../_services/authentication.service';
import { UserService } from '../_services/user.service';
import { AppService } from '../_services/app.service';

@Component({
    selector: 'app-admin',
    templateUrl: './admin.component.html',
    styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
    loading: Boolean = false;
    currentUser: User;
    countOrder: any;
    countCustomer: any;
    countHotel: any;
    countRoom: any;

    constructor(
        private authenticationService: AuthenticationService,
        private appService: AppService,
    ) {
    }

    ngOnInit() {
        this.loading = true;
        setTimeout(() => {
            this.loading = false;
            this.currentUser = this.authenticationService.currentUserValue;
            console.log(this.currentUser);

        }, 800);

        this.appService.count().subscribe((result) => {
            this.countOrder = result.order;
            this.countCustomer = result.customer;
            this.countHotel = result.hotel;
            this.countRoom = result.room;
        });
    }
}
