import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminRoutingModule } from './admin-routing.module';
import { ListCityComponent } from './city/list-city/list-city.component';
import { UpdateCityComponent } from './city/update-city/update-city.component';
import { CreateCityComponent } from './city/create-city/create-city.component';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    AdminRoutingModule
  ]
})
export class AdminModule { }
