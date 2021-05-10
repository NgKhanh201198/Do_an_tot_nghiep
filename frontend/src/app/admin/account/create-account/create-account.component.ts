import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Role } from 'src/app/_models/role.enum';
import { User } from 'src/app/_models/user';
import { LoggerService } from 'src/app/_services/logger.service';
import { emailValidator } from 'src/assets/customs/validation/CustomValidator';
import { UserService } from '../../../_services/user.service';

import * as moment from 'moment';
import { UserType } from 'src/app/_models/user-type.enum';
export class Options {
    name: string;
    value: string;
    constructor(name: string, value: string) {
        this.name = name;
        this.value = value;
    }
}

@Component({
    selector: 'app-create-account',
    templateUrl: './create-account.component.html',
    styleUrls: ['./create-account.component.css']
})
export class CreateAccountComponent implements OnInit {

    user: User;
    formData: FormGroup;
    roles = [];
    id: number;
    loading: boolean = false;
    submitted: boolean = false;
    success = '';
    error = '';
    hidePass = true;
    userTypeList: Array<Options> = [];
    rolesList: Array<Options> = [];
    _dateOfBirth: any = null;

    status: Options[] = [
        { name: 'Hoạt động', value: 'ACTIVE' },
        { name: 'Bị Khóa', value: 'LOCKED' }
    ];
    gender: Options[] = [
        { name: 'Nam', value: 'nam' },
        { name: 'Nữ', value: 'nu' }
    ];




    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private userService: UserService,
        private loggerService: LoggerService
    ) {

        this.userService.getAllUserType().subscribe((result: any) => {
            for (let index = 0; index < result.length; index++) {
                let userType = new Options(result[index].userTypeName, result[index].keyName);
                this.userTypeList.push(userType);
            }
        })

        this.userService.getAllRole().subscribe((result: any) => {
            for (let index = 0; index < result.length; index++) {
                let role = new Options(result[index].roleName, result[index].keyName);
                this.rolesList.push(role);
            }
        })
    }

    ngOnInit(): void {
        this.formData = this.formBuilder.group({
            fullName: ['', [Validators.required, Validators.pattern('[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{3,100}')]],
            username: ['', [Validators.required, emailValidator()]],
            password: ['', [Validators.required, Validators.pattern('[a-zA-Z0-9_@]{8,18}')]],
            gender: ['', [Validators.required]],
            dateOfBirth: ['', [Validators.required]],
            roles: ['', [Validators.required]],
            status: ['', [Validators.required]],
            userType: ['', [Validators.required]]
        })
    }

    //Invalid error message
    get formValid() { return this.formData.controls; }

    getFullnameErrorMessage(): string {
        if (this.formValid.fullName.errors.required) {
            return 'Vui lòng nhập họ và tên của bạn.';
        }
        return this.formValid.fullName.errors.pattern ? 'Tên hợp lệ phải có ít nhất 3 ký tự chỉ bao gồm chữ cái.' : '';
    }
    getEmailErrorMessage(): string {
        if (this.formValid.username.errors.required) {
            return 'Vui lòng nhập email.';
        }
        return this.formValid.username ? 'Vui lòng nhập một địa chỉ email hợp lệ' : '';
    }
    getPasswordErrorMessage(): string {
        if (this.formValid.password.errors.required) {
            return 'Vui lòng nhập mật khẩu của bạn.';
        }
        return this.formValid.password.errors.pattern ? 'Mật khẩu hợp lệ phải có ít nhất 8 ký tự (a-z,A-Z,0-9,_,@).' : '';
    }
    getGenderErrorMessage(): string {
        if (this.formValid.gender.errors.required) {
            return 'Vui lòng chọn giới tính.';
        }
        return '';
    }
    getRoleErrorMessage(): string {
        if (this.formValid.roles.errors.required) {
            return 'Vui lòng chọn vai trò.';
        }
        return '';
    }
    getDateOfBirthErrorMessage(): string {
        if (this.formValid.dateOfBirth.errors.required) {
            return 'Vui lòng chọn ngày sinh.';
        }
        return '';
    }
    getUserTypeErrorMessage(): string {
        if (this.formValid.userType.errors.required) {
            return 'Vui lòng chọn  kiểu người dùng.';
        }
        return '';
    }
    getStatusErrorMessage(): string {
        if (this.formValid.status.errors.required) {
            return 'Vui lòng chọn trạng thái.';
        }
        return '';
    }

    onSubmit() {
        this.loggerService.logger(this.formData.value);

        return this.userService.createAccount(this.formData.value)
            .subscribe({
                next: (result) => {
                    this.loading = false;
                    this.submitted = false;
                    this.error = '';
                    this.success = result.message;
                    this.loggerService.logger(result);
                },
                error: (error) => {
                    this.error = error.message;
                    this.loading = false;
                    this.loggerService.loggerError(error.message);
                }
            }),
            setTimeout(() => {
                this.success = '';
            }, 3000);
    }

}
