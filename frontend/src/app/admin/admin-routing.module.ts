import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Path } from '../_models/path.enum';
import { Permission } from '../_models/permission.enum';
import { CreateAccountComponent } from './account/create-account/create-account.component';
import { ListAccountComponent } from './account/list-account/list-account.component';
import { UpdateAccountComponent } from './account/update-account/update-account.component';
import { AdminComponent } from './admin.component';

const routes: Routes = [
    {
        path: '',
        component: AdminComponent
    },
    {
        path: Path.CREATE_ACCOUNT,
        component: CreateAccountComponent,
        data: { permission: Permission.CREATE_ACCOUNT }
    },
    {
        path: Path.LIST_ACCOUNT,
        component: ListAccountComponent,
        data: { permission: Permission.LIST_ACCOUNT }
    },
    {
        path: Path.UPDATED_ACCOUNT,
        component: UpdateAccountComponent,
        data: { permission: Permission.UPDATED_ACCOUNT }
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AdminRoutingModule { }
