import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { OwlOptions } from 'ngx-owl-carousel-o';
import { CityService } from 'src/app/_services/city.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Options } from 'src/app/_models/options';
import * as moment from 'moment';
import { HotelService } from 'src/app/_services/hotel.service';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
    listCity: any;
    listHotel: any;
    listCitySelect: Array<Options> = [];
    _minCheckInDate: Date = new Date();
    _minCheckOutDate: Date = new Date();

    formData = this.formBuilder.group({
        city: ['', [Validators.required,]],
        checkInDate: ['', [Validators.required,]],
        checkOutDate: ['', [Validators.required,]],
    });

    constructor(
        private formBuilder: FormBuilder,
        private cityService: CityService,
        private router: Router,
        private hotelService: HotelService,
    ) { }

    ngOnInit(): void {
        this.cityService.getCityTop5().subscribe((result) => {
            this.listCity = result;
        });

        this.hotelService.getHotelAll().subscribe((result) => {
            this.listHotel = result;
        });

        this.cityService.getCityAll().subscribe((result) => {
            for (let index = 0; index < result.length; index++) {
                let city = new Options(result[index].cityName, result[index].cityName);
                this.listCitySelect.push(city);
            }
        });
    }

    //Invalid error message
    get formValid() { return this.formData.controls; }

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
    //end

    redirectHotel(hotelName) {
        this.router.navigate(['/room/'], { queryParams: { hotelName: hotelName } })
    }

    redirectHotelByCityName(cityName) {
        this.router.navigate(['/hotel'], { queryParams: { cityName: cityName } });
    }

    changeCheckInDate(even) {
        var date = new Date(even.value);
        date.setDate(even.value.toDate().getDate() + 1);
        this._minCheckOutDate = date;
        this.formData.get("checkOutDate").setValue(moment(date).utc().format());
    }

    onSubmit() {
        this.router.navigate(['/hotel'], {
            queryParams: {
                cityName: this.formData.value.city, 
                checkInDate: moment(this.formData.value.checkInDate).utc().format(),
                checkOutDate: moment(this.formData.value.checkOutDate).utc().format()
            }
        });
    }

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
    }
}
