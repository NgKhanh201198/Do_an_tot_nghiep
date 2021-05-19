import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class HotelService {
    url = `${environment.baseUrlServer}` + 'api/hotel';

    constructor(
        private http: HttpClient
    ) { }

    handleError(error: HttpErrorResponse) {
        return throwError(error);
    }

    public createHotel(data: any, file: File): Observable<any> {
        const formData: FormData = new FormData();
        formData.append('image', file);
        formData.append('hotelName', data.hotelName);
        formData.append('address', data.address);
        formData.append('email', data.email);
        formData.append('phoneNumber', data.phoneNumber);
        formData.append('city', data.city);

        return this.http.post(`${this.url}`, formData)
            .pipe(
                tap(response => {
                    console.log(response);
                }),
                catchError(this.handleError)
            );
    }

    public getHotelAll(): Observable<any> {
        return this.http.get(`${this.url}`)
            .pipe(
                catchError(this.handleError)
            );
    }

    public getHotelById(id: any): Observable<any> {
        return this.http.get<any>(`${this.url}/${id}`)
            .pipe(
                catchError(this.handleError)
            );
    }

    public updateHotel(id: any, data: any): Observable<any> {

        return this.http.put(`${this.url}/${id}`, data)
            .pipe(
                tap(response => { }),
                catchError(this.handleError)
            );
    }

    public updateImage(id: any, file: File): Observable<any> {
        const formData: FormData = new FormData();
        formData.append('image', file);

        return this.http.put(`${this.url + '/updateImage'}/${id}`, formData)
            .pipe(
                tap(response => { }),
                catchError(this.handleError)
            );
    }

    public deleteHotel(id: any): Observable<any> {
        return this.http.delete(`${this.url}/${id}`)
            .pipe(
                tap(response => { }),
                catchError(this.handleError)
            );
    }
}
