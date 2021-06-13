import { Component, Input, OnInit } from '@angular/core';
import { User } from 'src/app/_models/user';
import { LoggerService } from 'src/app/_services/logger.service';
import { UserService } from 'src/app/_services/user.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { emailValidator } from 'src/assets/customs/validation/CustomValidator';

@Component({
    selector: 'app-forgot-password',
    templateUrl: './forgot-password.component.html',
    styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {
    @Input() childErrorMessage: string;
    formEmail: FormGroup;
    loading = false;
    currentUser: User;
    show = true;
    showResult = true;
    success = '';
    email: string = '';

    constructor(
        private formBuilder: FormBuilder,
        private userService: UserService,
        private logger: LoggerService
    ) { }

    ngOnInit(): void {
        this.formEmail = this.formBuilder.group({
            email: ['', [Validators.required, emailValidator()]]
        })
    }

    //Invalid error message
    get formValid() { return this.formEmail.controls; }

    getEmailErrorMessage(): string {
        if (this.formValid.email.errors.required) {
            return 'Vui lòng nhập email.';
        }
        return this.formValid.email ? 'Vui lòng nhập một địa chỉ email hợp lệ' : '';
    }

    onSubmit() {
        if (this.formEmail.valid) {
            this.loading = true;
            return this.userService.resetPassword(this.formEmail.value.email)
                .subscribe({
                    next: (res) => {
                        this.showResult = false;
                        this.logger.logger(res);
                        this.success = res.message;
                    },
                    error: (err) => {
                        this.logger.logger(err);
                    }
                });
        }
    }
}
