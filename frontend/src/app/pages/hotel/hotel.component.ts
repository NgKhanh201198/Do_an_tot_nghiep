import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HotelService } from 'src/app/_services/hotel.service';
import { LoggerService } from 'src/app/_services/logger.service';
import { Path } from '../../_models/path.enum';

@Component({
    selector: 'app-hotel',
    templateUrl: './hotel.component.html',
    styleUrls: ['./hotel.component.css']
})
export class HotelComponent implements OnInit {
    collection: Array<any> = [];
    _page: number = 1;
    _itemsPage: number = 4;
    _errorCheckRoom: any;
    cityName: any;
    _checkInDate:any;
    _checkOutDate:any;

    constructor(
        private hotelService: HotelService,
        private router: Router,
        private route: ActivatedRoute,
        private logger: LoggerService
    ) { }

    ngOnInit(): void {
        this.route.queryParamMap
            .subscribe((params) => {
                this.cityName = params.get('cityName');
                this._checkInDate = params.get('checkInDate');
                this._checkOutDate = params.get('checkOutDate');

                this.logger.loggerData(params.get('cityName'));
                this.logger.loggerData(params.get('checkInDate'));
                this.logger.loggerData(params.get('checkOutDate'));
            });

        if (this.cityName) {
            this.hotelService.getHotelbyCity(this.cityName).subscribe((result) => {
                if (result.length === 0) {
                    this._errorCheckRoom = "Thành phố " + this.cityName + " hiện tại chưa có khách sạn nào!";
                } else {
                    this.collection = result
                }
            });
        } else {
            this.hotelService.getHotelAll().subscribe((result) => {
                this.collection = result
            });
        }
    }

    redirectRoom(hotelName){
        this.router.navigate(['/room'], {
            queryParams: {
                hotelName: hotelName, 
                checkInDate: this._checkInDate,
                checkOutDate: this._checkOutDate 
            }
        });
    }
}
