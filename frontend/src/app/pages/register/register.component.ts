import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/_services/authentication.service';
import { LoggerService } from 'src/app/_services/logger.service';
import { emailValidator } from 'src/assets/customs/validation/CustomValidator';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
    @ViewChild('myForm') myForm: NgForm;
    formData: FormGroup;
    loading = false;
    submitted = false;
    success = '';
    error = '';
    hide = true;

    constructor(
        private formBuilder: FormBuilder,
        private authenticationService: AuthenticationService,
        private router: Router,
        private logger: LoggerService
    ) { }


    ngOnInit() {
        this.formData = this.formBuilder.group({
            fullName: ['', [Validators.required, Validators.pattern('[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\s]{3,100}')]],
            username: ['', [Validators.required, emailValidator()]],
            password: ['', [Validators.required, Validators.pattern('[a-zA-Z0-9_@]{8,18}')]]
        });
    }

    //Invalid error message
    get formValid() {
        return this.formData.controls;
    }

    getFullnameErrorMessage(): string {
        if (this.formValid.fullName.errors.required) {
            return 'Vui lòng nhập họ và tên của bạn.';
        }
        return this.formValid.fullName.errors.pattern ? 'Tên hợp lệ phải có ít nhất 3 ký tự (a-z,A-Z).' : '';
    }
    getEmailErrorMessage(): string {
        if (this.formValid.username.errors.required) {
            return 'Vui lòng nhập địa chỉ email của bạn.';
        }
        return this.formValid.username ? 'Vui lòng nhập địa chỉ email hợp lệ.' : '';//this.formValid.email->emailValidator()
    }
    getPasswordErrorMessage(): string {
        if (this.formValid.password.errors.required) {
            return 'Vui lòng nhập mật khẩu của bạn.';
        }
        return this.formValid.password.errors.pattern ? 'Mật khẩu hợp lệ phải có ít nhất 8 ký tự (a-z,A-Z,0-9,_,@).' : '';
    }

    //Submit
    onSubmit() {
        this.submitted = true;
        this.loading = true;


        return this.authenticationService.register(this.formData.value)
            .subscribe({
                next: (result) => {
                    this.loading = false;
                    this.submitted = false;
                    this.error = '';
                    this.myForm.resetForm();
                    this.success = result.message;
                    this.logger.logger(result);
                },
                error: (error) => {
                    this.error = error.message;
                    this.loading = false;
                    this.logger.loggerError(error.message);
                }
            }),
            setTimeout(() => {
                this.success = '';
            }, 3000),
            setTimeout(() => {
                if (this.error == '') {
                    this.router.navigate(['/login']);
                }
            }, 3000);
    }
}
