import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {OwlOptions} from 'ngx-owl-carousel-o';
import {CityService} from 'src/app/_services/city.service';
import {FormBuilder, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {Options} from 'src/app/_models/options';
import * as moment from 'moment';
import {HotelService} from 'src/app/_services/hotel.service';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

    constructor(
        private formBuilder: FormBuilder,
        private cityService: CityService,
        private router: Router,
        private hotelService: HotelService,
    ) {
    }

    // Invalid error message
    // tslint:disable-next-line:typedef
    get formValid() {
        return this.formData.controls;
    }

    listCity: any;
    listHotel: any;
    listCitySelect: Array<Options> = [];
    minCheckInDate: Date = new Date();
    minCheckOutDate: Date = new Date();

    formData = this.formBuilder.group({
        city: ['', [Validators.required]],
        checkInDate: ['', [Validators.required]],
        checkOutDate: ['', [Validators.required]],
    });

    customOptions: OwlOptions = {
        loop: true,
        margin: 10,
        mouseDrag: true,
        touchDrag: true,
        pullDrag: true,
        dots: true,
        navSpeed: 700,
        nav: false,

        responsive: {
            0: {
                items: 1
            },
            400: {
                items: 2
            },
            740: {
                items: 3
            },
            940: {
                items: 4
            }
        }
    };

    ngOnInit(): void {
        this.cityService.getCityTop5().subscribe((result) => {
            this.listCity = result;
        });

        this.hotelService.getHotelAll().subscribe((result) => {
            this.listHotel = result;
        });

        this.cityService.getCityAll().subscribe((result) => {
            // tslint:disable-next-line:prefer-for-of
            for (let index = 0; index < result.length; index++) {
                const city = new Options(result[index].cityName, result[index].cityName);
                this.listCitySelect.push(city);
            }
        });
    }

    getCityErrorMessage(): string {
        if (this.formValid.city.errors.required) {
            return 'Vui lòng chọn thành phố.';
        }
        return '';
    }

    getCheckInDateErrorMessage(): string {
        if (this.formValid.checkInDate.errors.required) {
            return 'Vui lòng nhập ngày nhận phòng.';
        }
        return 'Vui lòng nhập ngày nhận phòng hợp lệ.';
    }

    getCheckOutDateErrorMessage(): string {
        if (this.formValid.checkOutDate.errors.required) {
            return 'Vui lòng nhập ngày trả phòng.';
        }
        return 'Vui lòng nhập ngày trả phòng hợp lệ.';
    }

    // end

    // tslint:disable-next-line:typedef
    redirectHotel(hotelName) {
        this.router.navigate(['/room/'], {queryParams: {hotelName: hotelName}});
    }

    // tslint:disable-next-line:typedef
    redirectHotelByCityName(cityName) {
        this.router.navigate(['/hotel'], {queryParams: {cityName: cityName}});
    }

    // tslint:disable-next-line:typedef
    changeCheckInDate(even) {
        const date = new Date(even.value);
        date.setDate(even.value.toDate().getDate() + 1);
        this.minCheckOutDate = date;
        this.formData.get('checkOutDate').setValue(moment(date).utc().format());
    }

    onSubmit(): void {
        this.router.navigate(['/hotel'], {
            queryParams: {
                cityName: this.formData.value.city,
                checkInDate: moment(this.formData.value.checkInDate).utc().format(),
                checkOutDate: moment(this.formData.value.checkOutDate).utc().format()
            }
        });
    }
}
