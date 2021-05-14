import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Role } from 'src/app/_models/role.enum';
import { User } from 'src/app/_models/user';
import { LoggerService } from 'src/app/_services/logger.service';
import { emailValidator, phoneNumberValidator } from 'src/assets/customs/validation/CustomValidator';
import { UserService } from '../../../_services/user.service';
import { Location } from '@angular/common'
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
    selector: 'app-update-account',
    templateUrl: './update-account.component.html',
    styleUrls: ['./update-account.component.css']
})
export class UpdateAccountComponent implements OnInit {

    user: User;
    roles = [];
    id: number;
    loading: boolean = false;
    submitted: boolean = false;
    success = '';
    error = '';
    userTypeList: Array<Options> = [];
    rolesList: Array<Options> = [];

    status: Options[] = [
        { name: 'Hoạt động', value: 'ACTIVE' },
        { name: 'Khóa', value: 'LOCKED' }
    ];
    gender: Options[] = [
        { name: 'Nam', value: 'nam' },
        { name: 'Nữ', value: 'nu' }
    ];


    formUpdateData = this.formBuilder.group({
        fullName: ['', [Validators.required, Validators.pattern('[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{3,100}')]],
        username: ['', [Validators.required, emailValidator()]],
        phoneNumber: ['', [Validators.required, phoneNumberValidator()]],
        gender: ['', [Validators.required]],
        dateOfBirth: ['', [Validators.required]],
        roles: ['', [Validators.required]],
        status: ['', [Validators.required]],
        userType: ['', [Validators.required]]
    })

    constructor(
        private formBuilder: FormBuilder,
        private location: Location,
        private route: ActivatedRoute,
        private userService: UserService,
        private loggerService: LoggerService
    ) {

        this.userService.getAllUserType().subscribe((result: any) => {
            for (let index = 0; index < result.length; index++) {
                if (result[index].keyName !== UserType.CUSTOMER) {
                    let userType = new Options(result[index].userTypeName, result[index].keyName);
                    this.userTypeList.push(userType);
                }
            }
        })

        this.userService.getAllRole().subscribe((result: any) => {
            for (let index = 0; index < result.length; index++) {
                if (result[index].keyName !== Role.CUSTOMER) {
                    let role = new Options(result[index].roleName, result[index].keyName);
                    this.rolesList.push(role);
                }
            }
        })
    }

    ngOnInit(): void {
        this.id = +this.route.snapshot.paramMap.get('id');
        this.userService.getUserById(this.id).subscribe((result: any) => {
            for (let index = 0; index < result.roles.length; index++) {
                if (result.roles[index].keyName === Role.MANAGER) {
                    this.roles.push(Role.MANAGER)
                }
                else if (result.roles[index].keyName === Role.ADMIN) {
                    this.roles.push(Role.ADMIN)
                }
                else if (result.roles[index].keyName === Role.CUSTOMER) {
                    this.roles.push(Role.CUSTOMER)
                } else {
                    this.roles.push('')
                }
            }

            if (result.dateOfBirth != null) {
                result.dateOfBirth = moment(result.dateOfBirth).utc().format();
                this.loggerService.logger(result.dateOfBirth);
                this.loggerService.logger(moment(result.dateOfBirth).utc());
                this.loggerService.logger(moment(result.dateOfBirth).utc().format());
            }

            this.formUpdateData = this.formBuilder.group({
                fullName: [result.fullName, [Validators.required, Validators.pattern('[0-9a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{3,50}')]],
                username: [result.username, [Validators.required, emailValidator()]],
                phoneNumber: [result.phoneNumber, [Validators.required, phoneNumberValidator()]],
                gender: [result.gender, [Validators.required]],
                dateOfBirth: [result.dateOfBirth, [Validators.required]],
                roles: [this.roles, [Validators.required]],
                status: [result.status, [Validators.required]],
                userType: [result.userType.keyName, [Validators.required]]
            })
        });

    }

    //Invalid error message
    get formValid() { return this.formUpdateData.controls; }

    getFullnameErrorMessage(): string {
        if (this.formValid.fullName.errors.required) {
            return 'Vui lòng nhập họ và tên của bạn.';
        }
        return this.formValid.fullName.errors.pattern ? 'Tên hợp lệ phải có ít nhất 3 ký tự chỉ bao gồm chữ cái.' : '';
    }
    getEmailErrorMessage(): string {
        if (this.formValid.username.errors.required) {
            return 'Email không được để trống';
        }
        return this.formValid.username ? 'Vui lòng nhập một địa chỉ email hợp lệ.' : '';
    }
    getPhoneNumberErrorMessage(): string {
        if (this.formValid.phoneNumber.errors.required) {
            return 'Vui lòng nhập số điện thoại.';
        }
        return this.formValid.phoneNumber ? 'Vui lòng nhập số điện thoại hợp lệ.' : '';
    }
    getGenderErrorMessage(): string {
        if (this.formValid.gender.errors.required) {
            return 'Giới tính không được để trống.';
        }
        return '';
    }
    getRoleErrorMessage(): string {
        if (this.formValid.roles.errors.required) {
            return 'Vai trò không được để trống.';
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
            return 'Kiểu người dùng không được để trống.';
        }
        return '';
    }
    getStatusErrorMessage(): string {
        if (this.formValid.status.errors.required) {
            return 'Kiểu người dùng không được để trống.';
        }
        return '';
    }

    comeBack() {
        this.location.back();
    }
    
    onSubmit() {
        this.loggerService.logger(this.formUpdateData.value);

        this.submitted = true;
        return this.userService.updateAccountById(this.id, this.formUpdateData.value)
            .subscribe({
                next: (res) => {
                    this.error = '';

                    this.success = res.message;
                    this.loggerService.logger(res);
                },
                error: (err) => {
                    this.error = err.message;
                    this.loggerService.logger(err);
                }
            }),
            setTimeout(() => {
                this.success = '';
            }, 2500);
    }
}
