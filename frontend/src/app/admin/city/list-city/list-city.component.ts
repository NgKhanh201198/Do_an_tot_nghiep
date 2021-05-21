import { Component, OnInit } from '@angular/core';
import { CityService } from 'src/app/_services/city.service';
import { LoggerService } from 'src/app/_services/logger.service';

@Component({
    selector: 'app-list-city',
    templateUrl: './list-city.component.html',
    styleUrls: ['./list-city.component.css']
})
export class ListCityComponent implements OnInit {
    collection: Array<any> = [];
    _page: number = 1;
    _itemsPage = 3;
    _success = "";

    constructor(
        private cityService: CityService,
        private logger: LoggerService
    ) { }

    ngOnInit(): void {
        this.cityService.getCityAll().subscribe((result: any) => {
            this.collection = result;
        });
    }

    deleteCity(id: any) {
        if (confirm("Bạn có chắc muốn xóa thành phố này?")) {
            this.cityService.deleteCity(id).subscribe((result: any) => {
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
