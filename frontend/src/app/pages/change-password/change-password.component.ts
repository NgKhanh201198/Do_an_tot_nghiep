import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/_models/user';
import { AuthenticationService } from 'src/app/_services/authentication.service';
import { LoggerService } from 'src/app/_services/logger.service';
import { UserService } from 'src/app/_services/user.service';

@Component({
    selector: 'app-change-password',
    templateUrl: './change-password.component.html',
    styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
    currentUser: User;
    show = false;
    loading = false;
    success = "";
    error = "";

    constructor(
        private authenticationService: AuthenticationService,
        private userService: UserService,
        // private formBuilder: FormBuilder,
        private loggerService: LoggerService,
    ) { }

    ngOnInit(): void {
        this.currentUser = this.authenticationService.currentUserValue;
    }

    resetPassword() {
        this.loading = true;
        return this.userService.resetPassword(this.currentUser.email)
            .subscribe({
                next: (res) => {
                    this.show = true;
                    this.loggerService.logger(res);
                    this.success = res.message;
                },
                error: (err) => {
                    this.loggerService.logger(err);
                }
            })
            , setTimeout(() => {
               
            }, 2000);
    }
}
