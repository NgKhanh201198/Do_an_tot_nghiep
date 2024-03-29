import { Component, OnInit } from '@angular/core';
import { HotelService } from 'src/app/_services/hotel.service';
import { LoggerService } from 'src/app/_services/logger.service';

@Component({
    selector: 'app-list-hotel',
    templateUrl: './list-hotel.component.html',
    styleUrls: ['./list-hotel.component.css']
})
export class ListHotelComponent implements OnInit {
    collection: Array<any> = [];
    _success = '';
    _page: number = 1;
    _itemsPage: number = 3;

    constructor(
        private hotelService: HotelService,
        private logger: LoggerService
    ) { }

    ngOnInit(): void {
        this.hotelService.getHotelAll().subscribe((result) => {
            this.collection = result
        });
    }

    deleteHotel(id: any) {
        if (confirm("Bạn có chắc muốn xóa khách sạn này?")) {
            this.hotelService.deleteHotel(id).subscribe((result: any) => {
                for (var i = 0; i < this.collection.length; i++) {
                    if (this.collection[i].id === id) {
                        this.collection.splice(i, 1);
                    }
                }
                this._success = result.message;
                this.logger.logger(this._success);
            });
        }
        setTimeout(() => {
            this._success = '';
        }, 2500);
    }
}
