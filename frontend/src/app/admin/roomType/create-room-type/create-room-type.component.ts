import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators, FormGroup, NgForm } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { LoggerService } from 'src/app/_services/logger.service';
import { CityService } from '../../../_services/city.service';
import { Location } from '@angular/common'
import { RoomService } from '../../../_services/room.service';
import { RoomTypeService } from '../../../_services/room-type.service';

@Component({
    selector: 'app-create-room-type',
    templateUrl: './create-room-type.component.html',
    styleUrls: ['./create-room-type.component.css']
})
export class CreateRoomTypeComponent implements OnInit {
    @ViewChild('myForm') myForm: NgForm;
    formUpdateData: FormGroup;
    // id: number;
    _success: String = "";
    _error: String = "";

    constructor(
        private formBuilder: FormBuilder,
        private location: Location,
        private route: ActivatedRoute,
        private roomTypeService: RoomTypeService,
        private loggerService: LoggerService
    ) { }

    ngOnInit(): void {
        this.formUpdateData = this.formBuilder.group({
            roomTypeName: ['', [Validators.required, Validators.pattern('[0-9a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{0,18}')]],
            description: ['', [Validators.required, Validators.pattern('[0-9a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{0,255}')]],
        })
    }

    //Invalid error message
    get formValid() { return this.formUpdateData.controls; }

    getRoomTypeNameErrorMessage(): string {
        if (this.formValid.roomTypeName.errors.required) {
            return 'Vui lòng nhập tên loại phòng.';
        }
        return this.formValid.roomTypeName.errors.pattern ? 'Tên hợp lệ không quá 50 ký tự.' : '';
    }
    getDescriptionErrorMessage(): string {
        if (this.formValid.description.errors.required) {
            return 'Vui lòng điền mô tả.';
        }
        return this.formValid.description.errors.pattern ? 'Mô tả hợp lệ không quá 255 ký tự.' : '';
    }

    comeBack() {
        this.location.back();
    }

    onSubmit() {
        return this.roomTypeService.createRoomType(this.formUpdateData.value)
            .subscribe({
                next: (res) => {
                    this._error = '';
                    this.myForm.resetForm();
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
