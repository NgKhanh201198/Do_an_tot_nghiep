import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { LoggerService } from 'src/app/_services/logger.service';
import { Location } from '@angular/common'
import { HotelService } from '../../../_services/hotel.service';
import { Options } from 'src/app/_models/options';
import { RoomService } from '../../../_services/room.service';
import { RoomTypeService } from '../../../_services/room-type.service';

@Component({
    selector: 'app-update-room',
    templateUrl: './update-room.component.html',
    styleUrls: ['./update-room.component.css']
})
export class UpdateRoomComponent implements OnInit {
    @ViewChild('uploadFile') uploadFile: ElementRef;
    listHotels: Array<Options> = [];
    listRoomType: Array<Options> = [];
    reader = new FileReader();
    currentFile: File = null;
    _imgURL: any = null;
    _image: any = null;
    _id: number;
    _onUpdate = false;
    _success = '';
    _error = "";
    _errorUpload = "";

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
        private route: ActivatedRoute,
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

    formUpdateData = this.formBuilder.group({
        roomNumber: ['', [Validators.required, Validators.pattern('[0-9a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{0,18}')]],
        contents: ['', [Validators.required, Validators.pattern('[0-9a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{0,18}')]],
        roomCost: ['', [Validators.required, Validators.pattern('[0-9]{0,11}')]],
        discount: ['', [Validators.required, Validators.pattern('[0-9]{0,11}')]],
        numberOfPeople: ['', [Validators.required, Validators.pattern('[0-9]{0,11}')]],
        roomType: ['', [Validators.required]],
        hotel: ['', [Validators.required]],
        status: ['', [Validators.required]]
    });

    ngOnInit(): void {
        this._id = +this.route.snapshot.paramMap.get('id');
        this.roomService.getRoomById(this._id).subscribe((result: any) => {
            this._image = result.image;
            this._imgURL = result.image;

            this.formUpdateData = this.formBuilder.group({
                roomNumber: [result.roomNumber, [Validators.required, Validators.pattern('[0-9a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{0,50}')]],
                contents: [result.contents, [Validators.required, Validators.pattern('[0-9a-zA-Z,ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{0,255}')]],
                roomCost: [result.roomCost, [Validators.required, Validators.pattern('[0-9]{0,11}')]],
                discount: [result.discount, [Validators.required, Validators.pattern('[0-9]{0,11}')]],
                numberOfPeople: [result.numberOfPeople.toString(), [Validators.required, Validators.pattern('[0-9]{0,11}')]],
                roomType: [result.roomType.roomTypeName, [Validators.required]],
                hotel: [result.hotel.hotelName, [Validators.required]],
                status: [result.status, [Validators.required]]
            });
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
        return this.formValid.roomCost.errors.pattern ? 'Giá giảm giá không hợp lệ, vui lòng kiểm tra lại.' : '';
    }
    getNumberOfPeopleErrorMessage(): string {
        if (this.formValid.numberOfPeople.errors.required) {
            return 'Vui lòng nhập số người.';
        }
        return this.formValid.numberOfPeople.errors.pattern ? 'numberOfPeople không hợp lệ, vui lòng kiểm tra lại.' : '';
    }
    getStatusErrorMessage(): string {
        if (this.formValid.status.errors.required) {
            return 'Vui lòng chọn trạng thái.';
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

        return this.roomService.updateImage(this._id, this.currentFile)
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
        console.log( this.formUpdateData.value);
        
        return this.roomService.updateRoom(this._id, this.formUpdateData.value)
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
