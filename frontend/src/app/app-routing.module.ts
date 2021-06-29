import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { RegisterComponent } from './pages/register/register.component';
import { AuthenticationateGuard } from './_helpers/authenticationate.guard';
import { Path } from './_models/path.enum';
import { Permission } from './_models/permission.enum';
import { ChangePasswordComponent } from './pages/change-password/change-password.component';
import { BookingRoomComponent } from './pages/booking-room/booking-room.component';
import { PostComponent } from './pages/post/post.component';
import { HotelComponent } from './pages/hotel/hotel.component';
import { ResetPasswordComponent } from './pages/reset-password/reset-password.component';
import { ForgotPasswordComponent } from './pages/forgot-password/forgot-password.component';
import { RoomComponent } from './pages/room/room.component';
import { VerifyEmailComponent } from './pages/verify-email/verify-email.component';

const routes: Routes = [
    {
        path: Path.HOME,
        component: HomeComponent
    },
    {
        path: Path.ADMIN_PAGE,
        loadChildren: () => import('./admin/admin.module').then(module => module.AdminModule),
        // canActivate:[AuthenticationateGuard],
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
        path: Path.CHANGEPASSWORD,
        component: ChangePasswordComponent
    },
    {
        path: Path.RESETPASSWORD,
        component: ResetPasswordComponent
    },
    {
        path: Path.FORGOTPASSWORD,
        component: ForgotPasswordComponent
    },
    {
        path: Path.VERIFYEMAIL,
        component: VerifyEmailComponent
    },
    {
        path: Path.BOOKINGROOM,
        component: BookingRoomComponent
    },
    {
        path: Path.HOTEL,
        component: HotelComponent
    },
    {
        path: Path.ROOM,
        component: RoomComponent
    },
    {
        path: Path.POST,
        component: PostComponent
    },
    {
        path: Path.PROFILE,
        component: ProfileComponent,
        canActivate:[AuthenticationateGuard],
        data: { permission: Permission.PROFILE }
    },
    { path: '**', redirectTo: 'home' }
];

@NgModule({
    imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
    exports: [RouterModule]
})
export class AppRoutingModule { }
