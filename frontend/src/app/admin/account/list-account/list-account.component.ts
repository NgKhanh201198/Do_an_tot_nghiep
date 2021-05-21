import { Component, OnInit } from '@angular/core';
import { ThemePalette } from '@angular/material/core';
import { Role } from 'src/app/_models/role.enum';
import { LoggerService } from 'src/app/_services/logger.service';
import { UserService } from 'src/app/_services/user.service';
interface Status {
    value: string;
    viewValue: string;
}
@Component({
    selector: 'app-list-account',
    templateUrl: './list-account.component.html',
    styleUrls: ['./list-account.component.css']
})
export class ListAccountComponent implements OnInit {

    collection: Array<any> = [];
    _page: number = 1;

    status: Status[] = [
        { value: 'ACTIVE', viewValue: 'Hoạt động' },
        { value: 'LOCKED', viewValue: 'Khóa' }
    ];

    constructor(
        private userService: UserService,
        private logger: LoggerService
    ) { };

    ngOnInit() {
        this.userService.getAllUser().subscribe((result: any) => {
            for (const item of result) {
                if (item.userType.keyName != 'customer') {
                    this.collection.push(item);
                }
            }
        });
    };
}
