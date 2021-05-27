import { Component, OnInit } from '@angular/core';
import { LoggerService } from 'src/app/_services/logger.service';
import { BookingrateService } from '../../../_services/bookingrate.service';

@Component({
    selector: 'app-list-bookingrate',
    templateUrl: './list-bookingrate.component.html',
    styleUrls: ['./list-bookingrate.component.css']
})
export class ListBookingrateComponent implements OnInit {
    collection: Array<any> = [];
    _page: number = 1;
    _itemsPage = 5;
    _success = "";

    constructor(
        private bookingrateService: BookingrateService,
        private logger: LoggerService
    ) { }

    ngOnInit(): void {
        this.bookingrateService.getBookingrateAll().subscribe((result) => {
            this.collection = result;
        });
    }

    deleteBookingrate(id: any) {
        if (confirm("Bạn có chắc muốn xóa thành phố này?")) {
            this.bookingrateService.deleteBookingrate(id).subscribe((result: any) => {
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
