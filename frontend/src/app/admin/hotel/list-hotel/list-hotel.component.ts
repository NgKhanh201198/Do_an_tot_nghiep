import { Component, OnInit } from '@angular/core';
import { HotelService } from '../../../_services/hotel.service';

@Component({
    selector: 'app-list-hotel',
    templateUrl: './list-hotel.component.html',
    styleUrls: ['./list-hotel.component.css']
})
export class ListHotelComponent implements OnInit {
    collection: Array<any> = [];
    success = '';
    page: number = 1;
    itemsPage: number = 3;

    constructor(
        private hotelService: HotelService
    ) { }

    ngOnInit(): void {
        this.hotelService.getHotelAll().subscribe((result) => {
            this.collection = result
        });
    }

}
