import { BrowserModule } from '@angular/platform-browser';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';


//Module
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatRadioModule } from '@angular/material/radio';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatCardModule } from '@angular/material/card';
import { MatSelectModule } from '@angular/material/select';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatTableModule } from '@angular/material/table';
import { MatSliderModule } from '@angular/material/slider';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { NgxPaginationModule } from 'ngx-pagination';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, MAT_DATE_FORMATS } from '@angular/material/core';
import { MatMomentDateModule } from '@angular/material-moment-adapter';
import { CommonModule } from '@angular/common';
import { TextFieldModule } from '@angular/cdk/text-field';
import {MatAutocompleteModule} from '@angular/material/autocomplete';

//Service
import { UserService } from './_services/user.service';
import { HotelService } from './_services/hotel.service';
import { RoomTypeService } from './_services/room-type.service';
import { RoomService } from './_services/room.service';
import { PostService } from './_services/post.service';
import { BookingRoomService } from './_services/booking-room.service';
import { AuthenticationService } from './_services/authentication.service';
import { JwtInterceptor } from './_helpers/jwt.interceptor';
import { ErrorInterceptor } from './_helpers/error.interceptor';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { LoggerService } from './_services/logger.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

//Component
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { FooterComponent } from './pages/footer/footer.component';
import { HeaderTopComponent } from './pages/header-top/header-top.component';
import { HeaderBottomComponent } from './pages/header-bottom/header-bottom.component';
import { AdminComponent } from './admin/admin.component';
import { NavbarComponent } from './admin/navbar/navbar.component';
import { CreateHotelComponent } from './admin/hotel/create-hotel/create-hotel.component';
import { ListHotelComponent } from './admin/hotel/list-hotel/list-hotel.component';
import { UpdateHotelComponent } from './admin/hotel/update-hotel/update-hotel.component';
import { CreateRoomComponent } from './admin/room/create-room/create-room.component';
import { ListRoomComponent } from './admin/room/list-room/list-room.component';
import { UpdateRoomComponent } from './admin/room/update-room/update-room.component';
import { UpdateAccountComponent } from './admin/account/update-account/update-account.component';
import { CreateAccountComponent } from './admin/account/create-account/create-account.component';
import { ListAccountComponent } from './admin/account/list-account/list-account.component';
import { ListBookingRoomComponent } from './admin/bookingRoom/list-booking-room/list-booking-room.component';
import { CreateBookingRoomComponent } from './admin/bookingRoom/create-booking-room/create-booking-room.component';
import { UpdateBookingRoomComponent } from './admin/bookingRoom/update-booking-room/update-booking-room.component';
import { ListRoomTypeComponent } from './admin/roomType/list-room-type/list-room-type.component';
import { CreateRoomTypeComponent } from './admin/roomType/create-room-type/create-room-type.component';
import { UpdateRoomTypeComponent } from './admin/roomType/update-room-type/update-room-type.component';
import { ListCustomerComponent } from './admin/customer/list-customer/list-customer.component';
import { UpdateCustomerComponent } from './admin/customer/update-customer/update-customer.component';
import { ChangePasswordComponent } from './pages/change-password/change-password.component';
import { BookingRoomComponent } from './pages/booking-room/booking-room.component';
import { NavProfileComponent } from './pages/nav-profile/nav-profile.component';
import { HotelComponent } from './pages/hotel/hotel.component';
import { PostComponent } from './pages/post/post.component';
import { ResetPasswordComponent } from './pages/reset-password/reset-password.component';
import { ForgotPasswordComponent } from './pages/forgot-password/forgot-password.component';
import { FooterAdminComponent } from './admin/footer-admin/footer-admin.component';
import { CreateCityComponent } from './admin/city/create-city/create-city.component';
import { ListCityComponent } from './admin/city/list-city/list-city.component';
import { UpdateCityComponent } from './admin/city/update-city/update-city.component';
import { RoomComponent } from './pages/room/room.component';
import { VerifyEmailComponent } from './pages/verify-email/verify-email.component';

@NgModule({
    declarations: [
        AppComponent,
        RegisterComponent,
        LoginComponent,
        HomeComponent,
        ProfileComponent,
        AdminComponent,
        NavbarComponent,

        ListCityComponent,
        UpdateCityComponent,
        CreateCityComponent,

        CreateHotelComponent,
        ListHotelComponent,
        UpdateHotelComponent,

        CreateRoomComponent,
        ListRoomComponent,
        UpdateRoomComponent,

        CreateAccountComponent,

        ListAccountComponent,
        UpdateAccountComponent,
        ListCustomerComponent,
        UpdateCustomerComponent,

        CreateBookingRoomComponent,
        ListBookingRoomComponent,
        UpdateBookingRoomComponent,

        CreateRoomTypeComponent,
        ListRoomTypeComponent,
        UpdateRoomTypeComponent,

        HeaderTopComponent,
        HeaderBottomComponent,
        FooterComponent,
        ChangePasswordComponent,
        BookingRoomComponent,
        NavProfileComponent,
        HotelComponent,
        PostComponent,
        ResetPasswordComponent,
        ForgotPasswordComponent,
        FooterAdminComponent,
        RoomComponent,
        VerifyEmailComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        BrowserAnimationsModule,
        CarouselModule,
        CommonModule,

        MatIconModule,
        MatInputModule,
        MatSliderModule,
        MatSlideToggleModule,
        MatCheckboxModule,
        MatCardModule,
        MatRadioModule,
        MatSelectModule,
        MatFormFieldModule,
        MatButtonModule,
        MatTooltipModule,
        MatProgressBarModule,
        MatTableModule,
        NgxPaginationModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatMomentDateModule,
        TextFieldModule,
        MatAutocompleteModule
    ],
    providers: [
        UserService,
        HotelService,
        RoomService,
        RoomTypeService,
        PostService,
        BookingRoomService,
        AuthenticationService,
        LoggerService,
        // MatDatepickerModule,
        { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
        { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
        {
            provide: MAT_DATE_FORMATS,
            useValue: {
                parse: {
                    dateInput: 'DD/MM/YYYY',
                },
                display: {
                    dateInput: 'DD/MM/YYYY',
                    monthYearLabel: 'MMMM YYYY',
                    dateA11yLabel: 'LL',
                    monthYearA11yLabel: 'MMMM YYYY '
                },
            },
        }
    ],
    bootstrap: [AppComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule { }
