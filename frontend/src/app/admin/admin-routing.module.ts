import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Path } from '../_models/path.enum';
import { Permission } from '../_models/permission.enum';
import { CreateAccountComponent } from './account/create-account/create-account.component';
import { ListAccountComponent } from './account/list-account/list-account.component';
import { UpdateAccountComponent } from './account/update-account/update-account.component';
import { AdminComponent } from './admin.component';
import { CreateCustomerComponent } from './customer/create-customer/create-customer.component';
import { ListCustomerComponent } from './customer/list-customer/list-customer.component';
import { UpdateCustomerComponent } from './customer/update-customer/update-customer.component';

const routes: Routes = [
    {
        path: '',
        component: AdminComponent
    },
    //account
    {
        path: Path.LIST_ACCOUNT,
        component: ListAccountComponent,
        // data: { permission: Permission.LIST_ACCOUNT }
    },
    {
        path: Path.UPDATED_ACCOUNT,
        component: UpdateAccountComponent,
        // data: { permission: Permission.UPDATED_ACCOUNT }
    }, {
        path: Path.CREATE_ACCOUNT,
        component: CreateAccountComponent,
        // data: { permission: Permission.CREATE_ACCOUNT }
    },
    //customer
    {
        path: Path.LIST_CUSTOMER,
        component: ListCustomerComponent,
        // data: { permission: Permission.LIST_CUSTOMER }
    },
    {
        path: Path.UPDATED_CUSTOMER,
        component: UpdateCustomerComponent,
        // data: { permission: Permission.UPDATED_CUSTOMER }
    }, {
        path: Path.CREATE_CUSTOMER,
        component: CreateCustomerComponent,
        // data: { permission: Permission.CREATE_CUSTOMER }
    },
    // //city
    // {
    //     path: Path.LIST_ACCOUNT,
    //     component: ListAccountComponent,
    //     data: { permission: Permission.LIST_ACCOUNT }
    // },
    // {
    //     path: Path.UPDATED_ACCOUNT,
    //     component: UpdateAccountComponent,
    //     data: { permission: Permission.UPDATED_ACCOUNT }
    // }, {
    //     path: Path.CREATE_ACCOUNT,
    //     component: CreateAccountComponent,
    //     data: { permission: Permission.CREATE_ACCOUNT }
    // },
    // //hotel
    // {
    //     path: Path.LIST_ACCOUNT,
    //     component: ListAccountComponent,
    //     data: { permission: Permission.LIST_ACCOUNT }
    // },
    // {
    //     path: Path.UPDATED_ACCOUNT,
    //     component: UpdateAccountComponent,
    //     data: { permission: Permission.UPDATED_ACCOUNT }
    // }, {
    //     path: Path.CREATE_ACCOUNT,
    //     component: CreateAccountComponent,
    //     data: { permission: Permission.CREATE_ACCOUNT }
    // },
    // //room
    // {
    //     path: Path.LIST_ACCOUNT,
    //     component: ListAccountComponent,
    //     data: { permission: Permission.LIST_ACCOUNT }
    // },
    // {
    //     path: Path.UPDATED_ACCOUNT,
    //     component: UpdateAccountComponent,
    //     data: { permission: Permission.UPDATED_ACCOUNT }
    // }, {
    //     path: Path.CREATE_ACCOUNT,
    //     component: CreateAccountComponent,
    //     data: { permission: Permission.CREATE_ACCOUNT }
    // },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AdminRoutingModule { }
