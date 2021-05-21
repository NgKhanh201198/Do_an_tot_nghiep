import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { LoggerService } from 'src/app/_services/logger.service';
import { Location } from '@angular/common'
import { Options } from 'src/app/_models/options';
import { RoomService } from 'src/app/_services/room.service';
import { HotelService } from 'src/app/_services/hotel.service';
import { UserService } from 'src/app/_services/user.service';

export interface User {
    name: string;
}
@Component({
    selector: 'app-update-bookingrate',
    templateUrl: './update-bookingrate.component.html',
    styleUrls: ['./update-bookingrate.component.css']
})
export class UpdateBookingrateComponent implements OnInit {
    listUsers = [];
    // listUsers: Array<Options> = [];
    listHotels: Array<Options> = [];
    listRooms: Array<Options> = [];
    _maxDate: Date = new Date();
    _id: number;
    _success: String = "";
    _error: String = "";


    // myControl = new FormControl();
    options = [];
    filteredOptions;

    formUpdateData = this.formBuilder.group({
        user: ['', [Validators.required]],
        hotel: ['', [Validators.required,]],
        rooms: ['', [Validators.required,]],
        checkInDate: ['', [Validators.required,]],
        checkOutDate: ['', [Validators.required,]],
    });

    constructor(
        private formBuilder: FormBuilder,
        private location: Location,
        private route: ActivatedRoute,
        private roomService: RoomService,
        private userService: UserService,
        private hotelService: HotelService,
        private loggerService: LoggerService,
    ) { }



    displayFn(user: User): string {
        return user && user.name ? user.name : '';
    }

    private _filter(name: string): User[] {
        return this.options.filter(option => option.name.toLowerCase().indexOf(name.toLowerCase()) === 0);
    }



    ngOnInit(): void {
        this.hotelService.getHotelAll().subscribe((result: any) => {
            for (let index = 0; index < result.length; index++) {
                let hotel = new Options(result[index].hotelName, result[index].hotelName);
                this.listHotels.push(hotel);
            }
        });

        this.roomService.getRoomAll().subscribe((result: any) => {
            for (let index = 0; index < result.length; index++) {
                let room = new Options(result[index].roomNumber, result[index].roomNumber);
                this.listRooms.push(room);
            }
        });

        this.userService.getAllUser().subscribe((result: any) => {
            for (let index = 0; index < result.length; index++) {
                if (result[index].userType.keyName === 'customer') {
                    let newAccount = { name: result[index].fullName }
                    this.options.push(newAccount);
                }
            }
        });

        // this.userService.getAllUserByName().subscribe(response => {
        //     this.options = response;
        //     this.filteredOptions = response;
        // })

        // this.formGroup = this.fb.group({
        //     'employee': ['']
        // })
        // this.formGroup.get('employee').valueChanges.subscribe(response => {
        //     console.log('data is ', response);
        //     this.filterData(response);
        // })







        this._id = +this.route.snapshot.paramMap.get('id');

        this.formUpdateData = this.formBuilder.group({
            user: ['', [Validators.required]],
            hotel: ['', [Validators.required,]],
            rooms: ['', [Validators.required,]],
            checkInDate: ['', [Validators.required,]],
            checkOutDate: ['', [Validators.required,]],
        });
    }

    //Invalid error message
    get formValid() { return this.formUpdateData.controls; }

    getUserErrorMessage(): string {
        if (this.formValid.user.errors.required) {
            return 'Vui lòng chọn khách hàng.';
        }
        return '';
    }
    getHotelErrorMessage(): string {
        if (this.formValid.hotel.errors.required) {
            return 'Vui lòng sạn khách sạn.';
        }
        return '';
    }
    getRoomsErrorMessage(): string {
        if (this.formValid.rooms.errors.required) {
            return 'Vui lòng chọn phòng.';
        }
        return '';
    }
    getCheckInDateErrorMessage(): string {
        if (this.formValid.checkInDate.errors.required) {
            return 'Vui lòng chọn ngày đặt.';
        }
        return '';
    }
    getCheckOutDateErrorMessage(): string {
        if (this.formValid.checkOutDate.errors.required) {
            return 'Vui lòng chọn ngày trả.';
        }
        return '';
    }

    comeBack() {
        this.location.back();
    }

    onSubmit() {

    }


    // title = 'autocomplete';

    // options = [];

    // filteredOptions;


    // formGroup: FormGroup;
    // constructor(private service: UserService, private fb: FormBuilder) { }

    // filterData(enteredData) {
    //     this.filteredOptions = this.options.filter(item => {
    //         return item.toLowerCase().indexOf(enteredData.toLowerCase()) > -1
    //     })
    // }

    // ngOnInit() {
    //     this.initForm();
    //     this.getNames();
    // }

    // initForm() {
    //     this.formGroup = this.fb.group({
    //         'employee': ['']
    //     })
    //     this.formGroup.get('employee').valueChanges.subscribe(response => {
    //         console.log('data is ', response);
    //         this.filterData(response);
    //     })
    // }

    // getNames() {
    //     this.service.getData().subscribe(response => {
    //         this.options = response;
    //         this.filteredOptions = response;
    //     })
    // }

}
