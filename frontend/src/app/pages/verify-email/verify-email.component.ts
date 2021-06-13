import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { LoggerService } from 'src/app/_services/logger.service';
import { UserService } from 'src/app/_services/user.service';
import { emailValidator } from 'src/assets/customs/validation/CustomValidator';
import { AppService } from '../../_services/app.service';

@Component({
    selector: 'app-verify-email',
    templateUrl: './verify-email.component.html',
    styleUrls: ['./verify-email.component.css']
})
export class VerifyEmailComponent implements OnInit {
    formEmail: FormGroup;
    loading = false;
    show = false;
    success = '';
    error = '';
    token: any;

    constructor(
        private route: ActivatedRoute,
        private formBuilder: FormBuilder,
        private appService: AppService,
        private logger: LoggerService
    ) { }

    ngOnInit(): void {
        this.token = this.route.snapshot.queryParamMap.get('token');
        this.appService.verifyEmail(this.token)
            .subscribe({
                next: (res) => {
                    this.logger.logger(res);
                    this.success = res.message;
                },
                error: (err) => {
                    this.show = false;
                    this.error = err.message;
                    console.log(err);
                    
                }
            })


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
        // if (this.formEmail.valid) {
        //     this.loading = true;
        //     return this.userService.resetPassword(this.formEmail.value.email)
        //         .subscribe({
        //             next: (res) => {
        //                 this.logger.logger(res);
        //                 this.success = res.message;
        //             },
        //             error: (err) => {
        //                 this.logger.logger(err);
        //             }
        //         })
        //         , setTimeout(() => {

        //         }, 2000);
        // }
    }

}
