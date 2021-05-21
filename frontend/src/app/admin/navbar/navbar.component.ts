import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Path } from 'src/app/_models/path.enum';
import { Permission } from 'src/app/_models/permission.enum';
import { User } from 'src/app/_models/user';
import { AuthenticationService } from 'src/app/_services/authentication.service';

@Component({
	selector: 'app-navbar',
	templateUrl: './navbar.component.html',
	styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
	currentUser: User;

	constructor(
		private authenticationService: AuthenticationService,
		private router: Router
	) { }

	ngOnInit(): void {
		this.currentUser = this.authenticationService.currentUserValue;
	}

	get isAdmin() {
		for (let index = 0; index < this.currentUser.permissions.length; index++) {
			if (this.currentUser && this.currentUser.permissions[index] === Permission.ADMIN_PAGE)
				return true;
		}
		return false;
	}

	logout() {
		this.authenticationService.logout();
		this.router.navigate([Path.LOGIN]);
	}
}