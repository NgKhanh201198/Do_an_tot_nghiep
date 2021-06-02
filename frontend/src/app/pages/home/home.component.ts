import { Component, OnInit } from '@angular/core';
import { OwlOptions } from 'ngx-owl-carousel-o';
import { RoomTypeService } from '../../_services/room-type.service';
import { HotelService } from '../../_services/hotel.service';
import { RoomService } from 'src/app/_services/room.service';
import { CityService } from 'src/app/_services/city.service';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
    listCity: any;
    listHotel: any;
    _minDate: Date = new Date();

    formData = this.formBuilder.group({
        city: ['', [Validators.required,]],
        checkInDate: ['', [Validators.required,]],
        checkOutDate: ['', [Validators.required,]],
    });

    constructor(
        private formBuilder: FormBuilder,
        private cityService: CityService,
        private hotelService: HotelService,
    ) { }

    ngOnInit(): void {
        this.cityService.getCityTop5().subscribe((result) => {
            this.listCity = result;
        });
        
        this.hotelService.getHotelAll().subscribe((result) => {
            this.listHotel = result;
        });
    }

    //Invalid error message
    get formValid() { return this.formData.controls; }

    getCityErrorMessage(): string {
        if (this.formValid.city.errors.required) {
            return 'Vui lòng sạn tên thành phố.';
        }
        return '';
    }

    getCheckDateErrorMessage(): string {
        return 'Vui lòng nhập ngày nhận và ngày trả.';
    }


    onSubmit(){

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
        // navText: ['<', '>'],

        // loop: true,
        // margin: 10,
        // nav: false,
        // dots: true,
        // lazyLoad: true,

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
