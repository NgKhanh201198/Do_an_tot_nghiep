import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { RegisterComponent } from './pages/register/register.component';
import { Path } from './_models/path.enum';
import { Permission } from './_models/permission.enum';

const routes: Routes = [
    {
        path: Path.HOME,
        component: HomeComponent
    },
    {
        path: Path.ADMIN_PAGE,
        loadChildren: () => import('./admin/admin.module').then(module => module.AdminModule),
        data: { permission: Permission.ADMIN_PAGE }
    },
    {
        path: Path.LOGIN,
        component: LoginComponent
    },
    {
        path: Path.REGISTER,
        component: RegisterComponent
    },
    {
        path: Path.PROFILE,
        component: ProfileComponent
    },
    { path: '**', redirectTo: '' }
];

@NgModule({
    imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
    exports: [RouterModule]
})
export class AppRoutingModule { }
