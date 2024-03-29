import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { CityService } from 'src/app/_services/city.service';
import { LoggerService } from 'src/app/_services/logger.service';
import { Location } from '@angular/common'

@Component({
    selector: 'app-create-city',
    templateUrl: './create-city.component.html',
    styleUrls: ['./create-city.component.css']
})
export class CreateCityComponent implements OnInit {
    @ViewChild('myForm') myForm: NgForm;
    @ViewChild('uploadFile') uploadFile: ElementRef;
    formUpdateData: FormGroup;
    reader = new FileReader();
    currentFile: File = null;
    _imgURL: any = null;
    _success = '';
    _error = '';
    _errorImage = '';

    constructor(
        private formBuilder: FormBuilder,
        private cityService: CityService,
        private location: Location,
        private loggerService: LoggerService
    ) {
    }

    ngOnInit(): void {
        this.formUpdateData = this.formBuilder.group({
            cityName: ['', [Validators.required, Validators.pattern('[0-9a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{0,50}')]],
            description: ['', [Validators.required, Validators.pattern('[0-9a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{0,255}')]],
        })
    }

    //Invalid error message
    get formValid() { return this.formUpdateData.controls; }

    getCityNameErrorMessage(): string {
        if (this.formValid.cityName.errors.required) {
            return 'Vui lòng nhập tên thành phố.';
        }
        return this.formValid.cityName.errors.pattern ? 'Tên hợp lệ không quá 18 ký tự chỉ bao gồm chữ cái.' : '';
    }
    getDescriptionErrorMessage(): string {
        if (this.formValid.description.errors.required) {
            return 'Vui lòng điền mô tả.';
        }
        return this.formValid.description.errors.pattern ? 'Mô tả hợp lệ không quá 255 ký tự chỉ bao gồm chữ cái.' : '';
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
        } else

            return this.cityService.createCity(this.formUpdateData.value, this.currentFile)
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
