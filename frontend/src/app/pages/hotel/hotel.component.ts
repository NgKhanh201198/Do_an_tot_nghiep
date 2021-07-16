import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {HotelService} from 'src/app/_services/hotel.service';
import {LoggerService} from 'src/app/_services/logger.service';

@Component({
    selector: 'app-hotel',
    templateUrl: './hotel.component.html',
    styleUrls: ['./hotel.component.css']
})
export class HotelComponent implements OnInit {
    collection: Array<any> = [];
    page = 1;
    itemsPage = 4;
    errorCheckRoom: any;
    cityName: any;
    checkInDate: any;
    checkOutDate: any;

    constructor(
        private hotelService: HotelService,
        private router: Router,
        private route: ActivatedRoute,
        private logger: LoggerService
    ) {
    }

    ngOnInit(): void {
        this.route.queryParamMap
            .subscribe((params) => {
                this.cityName = params.get('cityName');
                this.checkInDate = params.get('checkInDate');
                this.checkOutDate = params.get('checkOutDate');

                this.logger.loggerData(params.get('cityName'));
                this.logger.loggerData(params.get('checkInDate'));
                this.logger.loggerData(params.get('checkOutDate'));
            });

        if (this.cityName) {
            this.hotelService.getHotelbyCity(this.cityName).subscribe((result) => {
                if (result.length === 0) {
                    this.errorCheckRoom = 'Thành phố ' + this.cityName + ' hiện tại chưa có khách sạn nào!';
                } else {
                    this.collection = result;
                }
            });
        } else {
            this.hotelService.getHotelAll().subscribe((result) => {
                this.collection = result;
            });
        }
    }

    redirectRoom(hotelName): void {
        this.router.navigate(['/room'], {
            queryParams: {
                hotelName,
                checkInDate: this.checkInDate,
                checkOutDate: this.checkOutDate
            }
        });
    }
}
