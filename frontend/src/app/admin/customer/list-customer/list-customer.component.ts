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
  selector: 'app-list-customer',
  templateUrl: './list-customer.component.html',
  styleUrls: ['./list-customer.component.css']
})
export class ListCustomerComponent implements OnInit {

    collection: Array<any> = [];
    listId: any = [];
    Role: any = Role;
    _page: number = 1;
    _itemsPage: number = 5;

    status: Status[] = [
        { value: 'ACTIVE', viewValue: 'Hoạt động' },
        { value: 'LOCKED', viewValue: 'Khóa' }
    ];

    constructor(
        private userService: UserService,
        private logger: LoggerService
    ) { };

    ngOnInit() {
        this.userService.getAllCustomer().subscribe((result: any) => {
            this.logger.logger(result);
            this.collection = result;
            // for (const item of result) {
            //     if (item.userType.keyName === 'customer') {
            //         this.collection.push(item);
            //     }
            // }
        });
    };
}
