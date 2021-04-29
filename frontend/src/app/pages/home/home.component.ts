import { Component, OnInit } from '@angular/core';
import { OwlOptions } from 'ngx-owl-carousel-o';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

    constructor() { }

    ngOnInit(): void {
    }
    customOptions: OwlOptions = {
        loop: true,
        margin: 10,
        mouseDrag: true,
        touchDrag: true,
        pullDrag: true,
        dots: true,
        navSpeed: 700,
        nav: false,
        navText: ['<', '>'],

        // loop: true,
        // margin: 10,
        // nav: false,
        // dots: true,
        // lazyLoad: true,

        responsive: {
            0: {
                items: 1
            },
            400: {
                items: 2
            },
            740: {
                items: 3
            },
            940: {
                items: 4
            }
        }
    }
}
