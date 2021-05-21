import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { LoggerService } from 'src/app/_services/logger.service';
import { Location } from '@angular/common'
import { RoomTypeService } from '../../../_services/room-type.service';

@Component({
    selector: 'app-update-room-type',
    templateUrl: './update-room-type.component.html',
    styleUrls: ['./update-room-type.component.css']
})
export class UpdateRoomTypeComponent implements OnInit {
    _id: number;
    _success: String = "";
    _error: String = "";

    constructor(
        private formBuilder: FormBuilder,
        private location: Location,
        private route: ActivatedRoute,
        private roomTypeService: RoomTypeService,
        private loggerService: LoggerService
    ) { }

    formUpdateData = this.formBuilder.group({
        roomTypeName: ['', [Validators.required, Validators.pattern('[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{0,18}')]],
        description: ['', [Validators.required, Validators.pattern('[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{0,255}')]],
    })

    ngOnInit(): void {
        this._id = +this.route.snapshot.paramMap.get('id');
        this.roomTypeService.getRoomTypeById(this._id).subscribe((result: any) => {
            this.formUpdateData = this.formBuilder.group({
                roomTypeName: [result.roomTypeName, [Validators.required, Validators.pattern('[0-9a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{0,50}')]],
                description: [result.description, [Validators.required, Validators.pattern('[0-9a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{0,255}')]],
            })
        });
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
        return this.roomTypeService.updateRoomType(this._id, this.formUpdateData.value)
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
