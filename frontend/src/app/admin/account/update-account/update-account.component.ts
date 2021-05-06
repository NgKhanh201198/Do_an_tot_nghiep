import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Role } from 'src/app/_models/role.enum';
import { User } from 'src/app/_models/user';
import { LoggerService } from 'src/app/_services/logger.service';
import { emailValidator } from 'src/assets/customs/validation/CustomValidator';
import { UserService } from '../../../_services/user.service';
import { UserType } from '../../../_models/user-type.enum';
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
        { name: 'Bị Khóa', value: 'LOCKED' }
    ];
    gender: Options[] = [
        { name: 'Nam', value: 'nam' },
        { name: 'Nữ', value: 'nu' }
    ];


    formUpdateData = this.formBuilder.group({
        fullName: ['', [Validators.required, Validators.pattern('[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{3,100}')]],
        username: ['', [Validators.required, emailValidator()]],
        gender: ['', [Validators.required]],
        roles: [[''], [Validators.required]],
        status: ['', [Validators.required]],
        userType: ['', [Validators.required]]
    })

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private userService: UserService,
        private logger: LoggerService
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
            this.formUpdateData = this.formBuilder.group({
                fullName: [result.fullName, [Validators.required, Validators.pattern('[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{3,100}')]],
                username: [result.username, [Validators.required, emailValidator()]],
                gender: [result.gender, [Validators.required]],
                roles: [this.roles, [Validators.required]],
                status: [result.status, [Validators.required]],
                userType: [result.userType.keyName, [Validators.required]]
            })
        });

    }

    //Invalid error message
    get formValid() { return this.formUpdateData.controls; }

    getEmailErrorMessage(): string {
        if (this.formValid.username.errors.required) {
            return 'Email không được để trống';
        }
        return this.formValid.username ? 'Vui lòng nhập một địa chỉ email hợp lệ' : '';
    }
    getFullnameErrorMessage(): string {
        if (this.formValid.fullName.errors.required) {
            return 'Vui lòng nhập họ và tên của bạn.';
        }
        return this.formValid.fullName.errors.pattern ? 'Tên hợp lệ phải có ít nhất 3 ký tự chỉ bao gồm chữ cái.' : '';
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

    onSubmit() {

        console.log(this.formUpdateData.value);

        
        //     this.submitted = true;
        //     return this.userService.updateUser(this.id, this.formUpdateData.value)
        //         .subscribe({
        //             next: (res) => {
        //                 this.error = '';
        //                 this.success = res.message;
        //                 this.logger.log(this.success);
        //                 this.logger.log(res);
        //             },
        //             error: (err) => {
        //                 this.error = err;
        //                 this.logger.logError(err);
        //             }
        //         }),
        //         setTimeout(() => {
        //             this.success = '';
        //         }, 2500);
    }
}
