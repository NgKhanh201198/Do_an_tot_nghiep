import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Permission} from 'src/app/_models/permission.enum';
import {User} from 'src/app/_models/user';
import {AuthenticationService} from 'src/app/_services/authentication.service';
import {UserService} from 'src/app/_services/user.service';
import {Path} from '../../_models/path.enum';

@Component({
    selector: 'app-header-top',
    templateUrl: './header-top.component.html',
    styleUrls: ['./header-top.component.css']
})
export class HeaderTopComponent implements OnInit {
    currentUser: User;
    fullName: string;

    constructor(
        private authenticationService: AuthenticationService,
        private userService: UserService,
        private router: Router
    ) {

    }

    ngOnInit(): void {
        this.currentUser = this.authenticationService.currentUserValue;
        if (this.currentUser != null) {
            this.userService.getUserById(this.currentUser.id).subscribe((result: any) => {
                this.fullName = result.fullName;
            });
        }
    }

    logout(): void {
        this.authenticationService.logout();
        this.router.navigate([Path.LOGIN]);
    }

    get isAdmin(): boolean {
        // tslint:disable-next-line:prefer-for-of
        for (let index = 0; index < this.currentUser.permissions.length; index++) {
            if (this.currentUser && this.currentUser.permissions[index] === Permission.ADMIN_PAGE) {
                return true;
            }
        }
        return false;
    }
}
