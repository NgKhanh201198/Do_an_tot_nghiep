import { AfterViewInit, Component, Input, OnInit, ViewChild } from '@angular/core';
import { User } from 'src/app/_models/user';
import { AuthenticationService } from 'src/app/_services/authentication.service';
import { ResetPasswordComponent } from '../reset-password/reset-password.component';

@Component({
    selector: 'app-forgot-password',
    templateUrl: './forgot-password.component.html',
    styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {
    @Input() childErrorMessage: string;
    @Input() childEmail: string;

    constructor(
    ) { }

    ngOnInit(): void {
    }

}
