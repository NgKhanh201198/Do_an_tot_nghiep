import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { AuthenticationService } from 'src/app/_services/authentication.service';
import { LoggerService } from 'src/app/_services/logger.service';
import { emailValidator } from 'src/assets/customs/validation/CustomValidator';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

    loginForm: FormGroup;
    loading = false;
    submitted = false;
    hide = true;
    error = '';

    constructor(
        private formBuilder: FormBuilder,
        private authenticationService: AuthenticationService,
        private route: ActivatedRoute,
        private router: Router,
        private logger: LoggerService
    ) {
        if (this.authenticationService.currentUserValue) {
            this.router.navigate(['/']);
        }
    }

    ngOnInit(): void {
        this.loginForm = this.formBuilder.group({
            username: ['', [Validators.required, emailValidator()]],
            password: ['', [Validators.required, Validators.pattern('[a-zA-Z0-9_@]{8,18}')]],
        });
    }

    //Invalid error message
    get formValid() { return this.loginForm.controls; }

    getUserErrorMessage(): string {
        if (this.formValid.username.errors.required) {
            return 'Vui lòng nhập địa chỉ email của bạn.';
        }
        return this.formValid.username ? 'Vui lòng nhập địa chỉ email hợp lệ.' : '';
    }
    getPasswordErrorMessage(): string {
        if (this.formValid.password.errors.required) {
            return 'Vui lòng nhập mật khẩu của bạn.';
        }
        return this.formValid.password.errors.pattern ? 'Mật khẩu hợp lệ phải có ít nhất 8 ký tự (a-z,A-Z,0-9,_,@).' : '';
    }

    onSubmit() {
        this.submitted = true;
        // this.logger.logger(this.loginForm.value);

        // stop here if form is invalid
        if (this.loginForm.invalid) {
            return;
        }
        this.loading = true;


        this.authenticationService.login(this.loginForm.value.username, this.loginForm.value.password)
            .pipe(first())
            .subscribe(
                {
                    next: () => {
                        this.logger.logger("Login success");
                        const returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
                        this.router.navigate([returnUrl]);
                    },
                    error: (error: any) => {
                        this.logger.loggerError(error.message);
                        this.error = error.message;
                        this.loading = false;
                    }
                }
            )
    }


}
