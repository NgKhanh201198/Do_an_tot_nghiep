import { Component, OnInit } from '@angular/core';
import { RoomService } from 'src/app/_services/room.service';
import { LoggerService } from '../../../_services/logger.service';

@Component({
    selector: 'app-list-room',
    templateUrl: './list-room.component.html',
    styleUrls: ['./list-room.component.css']
})
export class ListRoomComponent implements OnInit {
    collection: Array<any> = [];
    _success = '';
    _page: number = 1;
    _itemsPage: number = 3;

    constructor(
        private roomService: RoomService,
        private logger: LoggerService
    ) { }

    ngOnInit(): void {
        this.roomService.getRoomAll().subscribe((result) => {
            this.collection = result
        });
    }

    deleteRoom(id: any) {
        if (confirm("Bạn có chắc muốn xóa phòng này?")) {
            this.roomService.deleteRoom(id).subscribe((result: any) => {
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
