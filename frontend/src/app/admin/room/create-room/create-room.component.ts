import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { LoggerService } from 'src/app/_services/logger.service';
import { Location } from '@angular/common'
import { HotelService } from '../../../_services/hotel.service';
import { Options } from 'src/app/_models/options';
import { RoomService } from '../../../_services/room.service';
import { RoomTypeService } from '../../../_services/room-type.service';

@Component({
    selector: 'app-create-room',
    templateUrl: './create-room.component.html',
    styleUrls: ['./create-room.component.css']
})
export class CreateRoomComponent implements OnInit {

    @ViewChild('myForm') myForm: NgForm;
    @ViewChild('uploadFile') uploadFile: ElementRef;
    listHotels: Array<Options> = [];
    listRoomType: Array<Options> = [];
    formUpdateData: FormGroup;
    reader = new FileReader();
    currentFile: File = null;
    _imgURL: any = null;
    _success = '';
    _error = '';
    _errorImage = '';

    status: Options[] = [
        { name: 'Đang sử dụng', value: 'Đang sử dụng' },
        { name: 'Đang trống', value: 'Đang trống' },
        { name: 'Đang bảo trì', value: 'Đang bảo trì' }
    ];

    numberOfPeoples: Options[] = [
        { name: '1 người lớn', value: "1" },
        { name: '2 người lớn', value: "2" },
        { name: '3 người lớn', value: "3" },
        { name: '4 người lớn', value: "4" },
        { name: '5 người lớn', value: "5" },
    ];

    constructor(
        private formBuilder: FormBuilder,
        private location: Location,
        private roomService: RoomService,
        private hotelService: HotelService,
        private roomTypeService: RoomTypeService,
        private loggerService: LoggerService
    ) {
        this.hotelService.getHotelAll().subscribe((result: any) => {
            for (let index = 0; index < result.length; index++) {
                let hotel = new Options(result[index].hotelName, result[index].hotelName);
                this.listHotels.push(hotel);
            }
        });
        this.roomTypeService.getRoomTypeAll().subscribe((result: any) => {
            for (let index = 0; index < result.length; index++) {
                let roomtype = new Options(result[index].roomTypeName, result[index].roomTypeName);
                this.listRoomType.push(roomtype);
            }
        });
    }

    ngOnInit(): void {
        this.formUpdateData = this.formBuilder.group({
            roomNumber: ['', [Validators.required, Validators.pattern('[0-9a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{0,18}')]],
            contents: ['', [Validators.required, Validators.pattern('[0-9a-zA-Z,ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{0,255}')]],
            roomCost: ['', [Validators.required, Validators.pattern('[0-9]{0,11}')]],
            discount: ['', [Validators.required, Validators.pattern('[0-9]{0,11}')]],
            numberOfPeople: ['', [Validators.required, Validators.pattern('[0-9]{0,11}')]],
            roomType: ['', [Validators.required]],
            hotel: ['', [Validators.required]],
            status: ['', [Validators.required]]
        });

    }

    //Invalid error message
    get formValid() { return this.formUpdateData.controls; }

    getHotelErrorMessage(): string {
        if (this.formValid.hotel.errors.required) {
            return 'Vui lòng chọn khách sạn.';
        }
        return '';
    }
    getRoomTypeErrorMessage(): string {
        if (this.formValid.roomType.errors.required) {
            return 'Vui lòng chọn loại phòng.';
        }
        return '';
    }
    getRoomNumberErrorMessage(): string {
        if (this.formValid.roomNumber.errors.required) {
            return 'Vui lòng nhập tên phòng.';
        }
        return this.formValid.roomNumber.errors.pattern ? 'Tên phòng hợp lệ không quá 50 ký tự chỉ bao gồm chữ cái và số.' : '';
    }
    getContentsErrorMessage(): string {
        if (this.formValid.contents.errors.required) {
            return 'Vui lòng nhập nội dung.';
        }
        return this.formValid.contents.errors.pattern ? 'Nội dung hợp lệ không quá 255 ký tự chỉ bao gồm chữ cái và số.' : '';
    }
    getRoomCostlErrorMessage(): string {
        if (this.formValid.roomCost.errors.required) {
            return 'Vui lòng nhập giá phòng.';
        }
        return this.formValid.roomCost.errors.pattern ? 'Giá phòng không hợp lệ, vui lòng kiểm tra lại.' : '';
    }
    getDiscountErrorMessage(): string {
        if (this.formValid.discount.errors.required) {
            return 'Vui lòng nhập giá giảm giá.';
        }
        return this.formValid.discount.errors.pattern ? 'Giá giảm giá không hợp lệ, vui lòng kiểm tra lại.' : '';
    }
    getNumberOfPeopleErrorMessage(): string {
        if (this.formValid.numberOfPeople.errors.required) {
            return 'Vui lòng chọn số người.';
        }
        return this.formValid.numberOfPeople.errors.pattern ? 'Số người không hợp lệ, vui lòng kiểm tra lại.' : '';
    }
    getStatusErrorMessage(): string {
        if (this.formValid.status.errors.required) {
            return 'Vui lòng chọn trạng thái.';
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
        this.loggerService.logger(this.formUpdateData.value);

        if (this.currentFile == null) {
            this._errorImage = "Vui lòng chọn ảnh.";
        } else

            return this.roomService.createRoom(this.formUpdateData.value, this.currentFile)
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
