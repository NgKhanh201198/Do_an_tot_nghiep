import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { LoggerService } from 'src/app/_services/logger.service';
import { CityService } from '../../../_services/city.service';
import { Location } from '@angular/common'
@Component({
    selector: 'app-update-city',
    templateUrl: './update-city.component.html',
    styleUrls: ['./update-city.component.css']
})
export class UpdateCityComponent implements OnInit {
    @ViewChild('uploadFile') myInputVariable: ElementRef;
    reader = new FileReader();
    currentFile: File = null;
    imgURL: any = null;
    image: any = null;

    id: number;
    loading: boolean = false;
    submitted: boolean = false;
    onUpdate = false;
    success = '';
    error = '';
    errorUpload = "";

    formUpdateData = this.formBuilder.group({
        cityName: ['', [Validators.required, Validators.pattern('[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{0,18}')]],
        description: ['', [Validators.required, Validators.pattern('[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{0,255}')]],
    })

    constructor(
        private formBuilder: FormBuilder,
        private location: Location,
        private route: ActivatedRoute,
        private cityService: CityService,
        private loggerService: LoggerService
    ) {
    }

    ngOnInit(): void {
        this.id = +this.route.snapshot.paramMap.get('id');
        this.cityService.getCityById(this.id).subscribe((result: any) => {
            this.image = result.image;
            this.imgURL = result.image;

            this.formUpdateData = this.formBuilder.group({
                cityName: [result.cityName, [Validators.required, Validators.pattern('[0-9a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{0,50}')]],
                description: [result.description, [Validators.required, Validators.pattern('[0-9a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{0,255}')]],
            })
        });
    }

    //Invalid error message
    get formValid() { return this.formUpdateData.controls; }

    getCityNameErrorMessage(): string {
        if (this.formValid.cityName.errors.required) {
            return 'Vui lòng nhập tên thành phố.';
        }
        return this.formValid.cityName.errors.pattern ? 'Tên hợp lệ không quá 50 ký tự.' : '';
    }
    getDescriptionErrorMessage(): string {
        if (this.formValid.description.errors.required) {
            return 'Vui lòng điền mô tả.';
        }
        return this.formValid.description.errors.pattern ? 'Mô tả hợp lệ không quá 255 ký tự.' : '';
    }

    showForm() {
        this.onUpdate = true;
    }
    closeForm() {
        this.onUpdate = false;
        this.myInputVariable.nativeElement.value = "";
        this.imgURL = this.image;
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

    updateImage() {
        console.log(this.currentFile);

        return this.cityService.updateImage(this.id, this.currentFile)
            .subscribe({
                next: (res) => {
                    this.loggerService.logger(res);
                    this.image = this.imgURL
                    this.onUpdate = false;
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

    comeBack() {
        this.location.back();
    }

    onSubmit() {
        this.submitted = true;
        return this.cityService.updateCity(this.id, this.formUpdateData.value)
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
