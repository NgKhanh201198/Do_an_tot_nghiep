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
    checked = false;
    Role: any = Role;
    success = '';

    page: number = 1;

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
            this.logger.logger(result);
            for (const item of result) {
                if (item.userType.keyName === 'customer') {
                    this.collection.push(item);
                }
            }
            // this.totalLength += 1;
        });
    };

    // //Xóa tài khoản
    // deleteData(id: any) {
    //     if (confirm("Do you want to delete this account?")) {
    //         // this.userService.deleteUserById(id).subscribe((result: any) => {
    //         //     for (var i = 0; i < this.collection.length; i++) {
    //         //         if (this.collection[i].id === id) {
    //         //             this.collectionInActive.splice(0, 0, this.collection[i]);
    //         //             this.collection.splice(i, 1);
    //         //         }
    //         //     }
    //         //     this.success = result.message;
    //         //     this.logger.log(this.success);
    //         // });
    //     }
    //     setTimeout(() => {
    //         this.success = '';
    //     }, 2500);
    // };

    deleteAllData() {
        // if (this.listId.length == 0) {
        //     alert("You need to select at least one account!");
        // }
        // else if (confirm("Do you want to delete these accounts?")) {
        //     // this.userService.deleteMultipleUser(this.listId).subscribe((result: any) => {
        //     //     for (var i = 0; i < this.collection.length; i++) {
        //     //         for (let j = 0; j < this.listId.length; j++) {
        //     //             if (this.collection[i].id === this.listId[j]) {
        //     //                 this.collectionInActive.splice(0, 0, this.collection[i]);
        //     //                 this.collection.splice(i, 1);
        //     //             }
        //     //         }
        //     //     }
        //     //     this.success = result.message;
        //     // });
        // };
        // setTimeout(() => {
        //     this.success = '';
        // }, 2500);

    }

    // // Khôi phục tài khoản
    // restoreData(id: any) {
    //     if (confirm("Do you want to restore this account?")) {
    //         // this.userService.restoreUser(id).subscribe((result: any) => {
    //         //     for (var i = 0; i < this.collectionInActive.length; i++) {
    //         //         if (this.collectionInActive[i].id === id) {
    //         //             this.collection.splice(0, 0, this.collectionInActive[i]);
    //         //             this.collectionInActive.splice(i, 1);
    //         //         }
    //         //     }
    //         //     this.success = result.message;
    //         // });
    //     };
    //     setTimeout(() => {
    //         this.success = '';
    //     }, 2500);
    // };

    //Check box
    color: ThemePalette = 'primary';
    allComplete: boolean = false;

    //Kiểm tra id nào đã được checked
    isCheckSelectedId() {
        this.listId = [];
        this.collection.forEach(element => {
            if (element.isSelected) {
                this.listId.push(element.id);
            }
        });
    }

    setAll(selected: boolean) {
        this.allComplete = selected;
        if (this.collection == null) {
            return;
        }
        this.collection.forEach(t => t.isSelected = selected);
        this.isCheckSelectedId();
    }
    updateAllComplete() {
        this.allComplete = this.collection != null && this.collection.every(t => t.isSelected);
        this.isCheckSelectedId();
    }
    someComplete(): boolean {
        if (this.collection == null) {
            return false;
        }
        return this.collection.filter(t => t.isSelected).length > 0 && !this.allComplete;
    }

}
