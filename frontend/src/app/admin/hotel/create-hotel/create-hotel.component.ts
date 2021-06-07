import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { CityService } from 'src/app/_services/city.service';
import { HotelService } from 'src/app/_services/hotel.service';
import { LoggerService } from 'src/app/_services/logger.service';
import { emailValidator, phoneNumberValidator } from 'src/assets/customs/validation/CustomValidator';
import { Location } from '@angular/common';
import { Options } from 'src/app/_models/options';
@Component({
    selector: 'app-create-hotel',
    templateUrl: './create-hotel.component.html',
    styleUrls: ['./create-hotel.component.css']
})
export class CreateHotelComponent implements OnInit {
    @ViewChild('myForm') myForm: NgForm;
    @ViewChild('uploadFile') uploadFile: ElementRef;
    listCitys: Array<Options> = [];
    formUpdateData: FormGroup;
    reader = new FileReader();
    currentFile: File = null;
    _imgURL: any = null;
    _success = '';
    _error = '';
    _errorImage = '';

    constructor(
        private formBuilder: FormBuilder,
        private hotelService: HotelService,
        private cityService: CityService,
        private location: Location,
        private loggerService: LoggerService
    ) {
        this.cityService.getCityAll().subscribe((result: any) => {
            for (let index = 0; index < result.length; index++) {
                let city = new Options(result[index].cityName, result[index].cityName);
                this.listCitys.push(city);
            }
        })
    }

    ngOnInit(): void {
        this.formUpdateData = this.formBuilder.group({
            hotelName: ['', [Validators.required, Validators.pattern('[0-9a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{0,50}')]],
            address: ['', [Validators.required, Validators.pattern('[/0-9a-zA-Z,ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{0,255}')]],
            email: ['', [Validators.required, emailValidator()]],
            phoneNumber: ['', [Validators.required, phoneNumberValidator()]],
            city: ['', [Validators.required]],
            description: ['', [Validators.required]]
        })
    }

    //Invalid error message
    get formValid() { return this.formUpdateData.controls; }

    getHotelNameErrorMessage(): string {
        if (this.formValid.hotelName.errors.required) {
            return 'Vui lòng nhập tên khách sạn.';
        }
        return this.formValid.hotelName.errors.pattern ? 'Tên hợp lệ không quá 50 ký tự chỉ bao gồm chữ cái và số.' : '';
    }
    getAddressErrorMessage(): string {
        if (this.formValid.address.errors.required) {
            return 'Vui lòng nhập địa chỉ.';
        }
        return this.formValid.address.errors.pattern ? 'Địa chỉ hợp lệ không quá 255 ký tự chỉ bao gồm chữ cái và số.' : '';
    }
    getEmailErrorMessage(): string {
        if (this.formValid.email.errors.required) {
            return 'Vui lòng nhập emai.';
        }
        return this.formValid.email ? 'Vui lòng nhập một địa chỉ email hợp lệ' : '';
    }
    getPhoneNumberErrorMessage(): string {
        if (this.formValid.phoneNumber.errors.required) {
            return 'Vui lòng nhập số điện thoại.';
        }
        return this.formValid.phoneNumber ? 'Vui lòng nhập số điện thoại hợp lệ' : '';
    }
    getCityErrorMessage(): string {
        if (this.formValid.city.errors.required) {
            return 'Vui lòng chọn thành phố.';
        }
        return '';
    }
    getDescriptionErrorMessage(): string {
        if (this.formValid.description.errors.required) {
            return 'Vui lòng nhập mô tả.';
        }
        return '';
    }

    onSelectFile(event) {
        if (event.target.files.length > 0) {
            this.currentFile = event.target.files[0];
            this._errorImage = "";

            this.reader.readAsDataURL(this.currentFile);
            this.reader.onload = (_event) => {
                this._imgURL = this.reader.result;
            }
        }
    }

    comeBack() {
        this.location.back();
    }

    onSubmit() {

        if (this.currentFile == null) {
            this._errorImage = "Vui lòng chọn ảnh.";
        } else {
            // alert("da den day")
            return this.hotelService.createHotel(this.formUpdateData.value, this.currentFile)
                .subscribe({
                    next: (res) => {
                        this._error = '';
                        this._imgURL = null;
                        this.uploadFile.nativeElement.value = "";
                        this.myForm.resetForm();
                        this._success = res.message;
                        this.loggerService.logger(res);
                    },
                    error: (err) => {
                        this._error = err.message;
                        this.loggerService.loggerError(err);
                    }
                }),
                setTimeout(() => {
                    this._success = '';
                }, 2500);
        }
    }
}
