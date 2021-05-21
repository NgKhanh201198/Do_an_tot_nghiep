import { Component, OnInit } from '@angular/core';
import { LoggerService } from 'src/app/_services/logger.service';
import { RoomTypeService } from '../../../_services/room-type.service';

@Component({
    selector: 'app-list-room-type',
    templateUrl: './list-room-type.component.html',
    styleUrls: ['./list-room-type.component.css']
})
export class ListRoomTypeComponent implements OnInit {
    collection: Array<any> = [];

    //Phân trang
    _page: number = 1;
    _itemsPage = 5;
    _success = "";

    constructor(
        private roomTypeService: RoomTypeService,
        private logger: LoggerService
    ) { }

    ngOnInit(): void {
        this.roomTypeService.getRoomTypeAll().subscribe((result: any) => {
            this.collection = result;
        });
    }

    deleteCity(id: any) {
        if (confirm("Bạn có chắc muốn xóa thành phố này?")) {
            this.roomTypeService.deleteRoomType(id).subscribe((result: any) => {
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
