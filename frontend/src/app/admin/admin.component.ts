import { Component, OnInit } from '@angular/core';
import { User } from '../_models/user';
import { AuthenticationService } from '../_services/authentication.service';

@Component({
    selector: 'app-admin',
    templateUrl: './admin.component.html',
    styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
    loading: Boolean = false;
    currentUser: User;

    constructor(
        private authenticationService: AuthenticationService
    ) {
    }

    ngOnInit() {
        this.loading = true;
        setTimeout(() => {
            this.loading = false;
            this.currentUser = this.authenticationService.currentUserValue;
            console.log(this.currentUser);
            
        }, 800);
    }
}
