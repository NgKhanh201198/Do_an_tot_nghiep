import { BrowserModule } from '@angular/platform-browser';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
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
import { UpdateBookingrateComponent } from './admin/bookingrate/update-bookingrate/update-bookingrate.component';
import { ListBookingrateComponent } from './admin/bookingrate/list-bookingrate/list-bookingrate.component';
import { CreateBookingrateComponent } from './admin/bookingrate/create-bookingrate/create-bookingrate.component';
import { ListRoomTypeComponent } from './admin/roomType/list-room-type/list-room-type.component';
import { CreateRoomTypeComponent } from './admin/roomType/create-room-type/create-room-type.component';
import { UpdateRoomTypeComponent } from './admin/roomType/update-room-type/update-room-type.component';
import { ListCustomerComponent } from './admin/customer/list-customer/list-customer.component';
import { CreateCustomerComponent } from './admin/customer/create-customer/create-customer.component';
import { UpdateCustomerComponent } from './admin/customer/update-customer/update-customer.component';
import { UserService } from './_services/user.service';
import { HotelService } from './_services/hotel.service';
import { RoomTypeService } from './_services/room-type.service';
import { RoomService } from './_services/room.service';
import { PostService } from './_services/post.service';
import { BookingrateService } from './_services/bookingrate.service';
import { BookingRoomService } from './_services/booking-room.service';
import { AuthenticationService } from './_services/authentication.service';
import { JwtInterceptor } from './_helpers/jwt.interceptor';
import { ErrorInterceptor } from './_helpers/error.interceptor';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { LoggerService } from './_services/logger.service';
import { FooterComponent } from './footer/footer.component';
import { HeaderTopComponent } from './header-top/header-top.component';
import { HeaderBottomComponent } from './header-bottom/header-bottom.component';


@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    HomeComponent,
    ProfileComponent,
    AdminComponent,
    NavbarComponent,

    CreateHotelComponent,
    ListHotelComponent,
    UpdateHotelComponent,

    CreateRoomComponent,
    ListRoomComponent,
    UpdateRoomComponent,

    CreateAccountComponent,
    ListAccountComponent,
    UpdateAccountComponent,

    CreateBookingRoomComponent,
    ListBookingRoomComponent,
    UpdateBookingRoomComponent,

    CreateBookingrateComponent,
    ListBookingrateComponent,
    UpdateBookingrateComponent,

    CreateRoomTypeComponent,
    ListRoomTypeComponent,
    UpdateRoomTypeComponent,

    CreateCustomerComponent,
    ListCustomerComponent,
    UpdateCustomerComponent,
    
    FooterComponent,
    HeaderTopComponent,
    HeaderBottomComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [
    UserService,
    HotelService,
    RoomService,
    RoomTypeService,
    PostService,
    BookingrateService,
    BookingRoomService,
    AuthenticationService,
    LoggerService,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }
  ],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule { }
