import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/_models/user';
import { AuthenticationService } from 'src/app/_services/authentication.service';
import { UserService } from '../../_services/user.service';
import { LoggerService } from '../../_services/logger.service';
import { formatDate } from '@angular/common';

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
    @ViewChild('uploadFile') myInputVariable: ElementRef;
    currentUser: User;
    avatar: any;
    avatarDefault = "../../../assets/customs/images/user.png";
    email: any;
    dateOfBirth: any = null;

    loading = false;
    onupdate = false;
    onupdateAvatar = false;
    disabled = "true";
    success = "";
    error = "";
    errorUpload = "";

    reader = new FileReader();
    currentFile: File = null;
    imgURL: any = null;

    formUpdate = this.formBuilder.group({
        username: ['', [Validators.required]],
        fullName: ['', [Validators.required, Validators.pattern('[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\s]{3,100}')]],
        phoneNumber: ['', [Validators.required]],
        dateOfBirth: ['', [Validators.required]],
        gender: ['', [Validators.required]],
    });
    constructor(
        private authenticationService: AuthenticationService,
        private userService: UserService,
        private formBuilder: FormBuilder,
        private loggerService: LoggerService,
    ) { }

    ngOnInit(): void {

        // get current user
        this.currentUser = this.authenticationService.currentUserValue;
        this.userService.getUserById(this.currentUser.id).subscribe((result: any) => {
            if (result.avatar != null) {
                this.avatar = result.avatar;
            }
            if (result.dateOfBirth != null) {
                this.dateOfBirth = formatDate(result.dateOfBirth, 'yyyy-MM-dd', 'en');
            }
            this.email = result.username;

            this.formUpdate = this.formBuilder.group({
                fullName: [result.fullName, [Validators.required, Validators.pattern('[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\s]{3,100}')]],
                phoneNumber: [result.phoneNumber, [Validators.required]],
                dateOfBirth: [this.dateOfBirth, [Validators.required]],
                gender: [result.gender, [Validators.required]],
            });
        });


    }

    //Invalid error message
    get formValid() {
        return this.formUpdate.controls;
    }

    update() {
        this.onupdate = true;
        this.disabled = null;
    }
    showForm() {
        this.onupdateAvatar = true;
    }
    closeForm() {
        this.onupdateAvatar = false;
        this.myInputVariable.nativeElement.value = "";
        this.imgURL = null;
    }

    onSelectFile(event) {
        if (event.target.files.length > 0) {
            this.currentFile = event.target.files[0];

            this.reader.readAsDataURL(this.currentFile);
            this.reader.onload = (_event) => {
                this.imgURL = this.reader.result;
            }
        }
    }

    updateAvatar() {
        return this.userService.updateAvatar(this.currentUser.id, this.currentFile)
            .subscribe({
                next: (res) => {
                    this.loggerService.logger(res);
                    this.avatarDefault = this.imgURL
                    this.avatar = this.imgURL
                    this.onupdateAvatar = false;
                    this.error = '';
                    this.success = res.message;
                },
                error: (err) => {
                    this.loggerService.logger(err);
                    this.success = '';
                    this.errorUpload = err.message;
                }
            })
            , setTimeout(() => {
                this.imgURL = '';
                this.success = '';
            }, 3000);
    }

    onUpdate() {

        return this.userService.updateUserById(this.currentUser.id, this.formUpdate.value)
            .subscribe({
                next: (res) => {
                    this.onupdate = false;
                    this.disabled = "true";
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
