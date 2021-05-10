import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-list-city',
  templateUrl: './list-city.component.html',
  styleUrls: ['./list-city.component.css']
})
export class ListCityComponent implements OnInit {
  collection: Array<any> = [];
  listId: any = [];
  checked = false;
  
  success = '';

  page: number = 1;
  
  constructor() { }

  ngOnInit(): void {
  }

}
