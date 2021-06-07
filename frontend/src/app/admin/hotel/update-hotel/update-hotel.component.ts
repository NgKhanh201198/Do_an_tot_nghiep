import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { LoggerService } from 'src/app/_services/logger.service';
import { Location } from '@angular/common'
import { emailValidator, phoneNumberValidator } from 'src/assets/customs/validation/CustomValidator';
import { Options } from 'src/app/_models/options';
import { HotelService } from 'src/app/_services/hotel.service';
import { CityService } from 'src/app/_services/city.service';

@Component({
    selector: 'app-update-hotel',
    templateUrl: './update-hotel.component.html',
    styleUrls: ['./update-hotel.component.css']
})
export class UpdateHotelComponent implements OnInit {
    @ViewChild('uploadFile') uploadFile: ElementRef;
    listCitys: Array<Options> = [];
    reader = new FileReader();
    currentFile: File = null;
    _imgURL: any = null;
    _image: any = null;
    _id: number;
    _onUpdate = false;
    _success = '';
    _error = '';
    _errorUpload = "";

    constructor(
        private formBuilder: FormBuilder,
        private location: Location,
        private route: ActivatedRoute,
        private hotelService: HotelService,
        private cityService: CityService,
        private loggerService: LoggerService
    ) {
        this.cityService.getCityAll().subscribe((result: any) => {
            for (let index = 0; index < result.length; index++) {
                let city = new Options(result[index].cityName, result[index].cityName);
                this.listCitys  .push(city);
            }
        })
    }

    formUpdateData = this.formBuilder.group({
        hotelName: ['', [Validators.required, Validators.pattern('[0-9a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{0,18}')]],
        address: ['', [Validators.required, Validators.pattern('[0-9a-zA-Z,ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s][0-9]{0,255}')]],
        email: ['', [Validators.required, emailValidator()]],
        phoneNumber: ['', [Validators.required, phoneNumberValidator()]],
        city: ['', [Validators.required]],
        description: ['', [Validators.required]]
    });

    ngOnInit(): void {
        this._id = +this.route.snapshot.paramMap.get('id');
        this.hotelService.getHotelById(this._id).subscribe((result: any) => {
            this.loggerService.logger(result);
            this._image = result.image;
            this._imgURL = result.image;

            this.formUpdateData = this.formBuilder.group({
                hotelName: [result.hotelName, [Validators.required, Validators.pattern('[0-9a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{0,50}')]],
                address: [result.address, [Validators.required, Validators.pattern('[0-9a-zA-Z,ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{0,255}')]],
                email: [result.email, [Validators.required, emailValidator()]],
                phoneNumber: [result.phoneNumber, [Validators.required, phoneNumberValidator()]],
                city: [result.city.cityName, [Validators.required]],
                description: [result.description, [Validators.required]]
            })
        });
    }

    //Invalid error message
    get formValid() { return this.formUpdateData.controls; }

    getHotelNameErrorMessage(): string {
        if (this.formValid.hotelName.errors.required) {
            return 'Vui lòng nhập tên khách sạn.';
        }
        return this.formValid.hotelName.errors.pattern ? 'Tên hợp lệ không quá 50 ký tự.' : '';
    }
    getAddressErrorMessage(): string {
        if (this.formValid.address.errors.required) {
            return 'Vui lòng nhập địa chỉ.';
        }
        return this.formValid.address.errors.pattern ? 'Địa chỉ hợp lệ không quá 255 ký tự chỉ bao gồm chữ cái.' : '';
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

    showForm() {
        this._onUpdate = true;
    }
    closeForm() {
        this._onUpdate = false;
        this.uploadFile.nativeElement.value = "";
        this._imgURL = this._image;
    }

    onSelectFile(event) {
        if (event.target.files.length > 0) {
            this.currentFile = event.target.files[0];

            this.reader.readAsDataURL(this.currentFile);
            this.reader.onload = (_event) => {
                this._imgURL = this.reader.result;
            }
        }
    }

    comeBack() {
        this.location.back();
    }

    updateImage() {
        console.log(this.currentFile);

        return this.hotelService.updateImage(this._id, this.currentFile)
            .subscribe({
                next: (res) => {
                    this.loggerService.logger(res);
                    this._image = this._imgURL
                    this._onUpdate = false;
                    this._error = '';
                    this._success = res.message;
                },
                error: (err) => {
                    this.loggerService.logger(err);
                    this._success = '';
                    this._errorUpload = err.message;
                }
            })
            , setTimeout(() => {
                this._imgURL = '';
                this._success = '';
            }, 3000);
    }

    onSubmit() {
        return this.hotelService.updateHotel(this._id, this.formUpdateData.value)
            .subscribe({
                next: (res) => {
                    this._error = '';

                    this._success = res.message;
                    this.loggerService.logger(res);
                },
                error: (err) => {
                    this._error = err.message;
                    this.loggerService.logger(err);
                }
            }),
            setTimeout(() => {
                this._success = '';
            }, 2500);
    }
}
