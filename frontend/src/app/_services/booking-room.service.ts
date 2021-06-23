import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class BookingRoomService {

    url = `${environment.baseUrlServer}` + 'api/bookingRoom';

    constructor(
        private http: HttpClient
    ) { }

    handleError(error: HttpErrorResponse) {
        return throwError(error);
    }

    public createBookingRoom(checkInDate: any, checkOutDate: any, numberofPeople: any, user: any, hotel: any, rooms: any): Observable<any> {
        const data = {
            user: user,
            hotel: hotel,
            checkInDate: checkInDate,
            checkOutDate: checkOutDate,
            totalNumberOfPeople: numberofPeople,
            rooms: rooms,
        };
        return this.http.post(`${this.url}`, data)
            .pipe(
                catchError(this.handleError)
            );
    }

    public getBookingRoomAll(): Observable<any> {
        return this.http.get(`${this.url}`)
            .pipe(
                catchError(this.handleError)
            );
    }

    public getBookingRoomById(id: any): Observable<any> {
        return this.http.get<any>(`${this.url}/${id}`)
            .pipe(
                catchError(this.handleError)
            );
    }

    public getBookingRoomByUser(fullName: any): Observable<any> {
        const params = new HttpParams().append('name', fullName);
        return this.http.get<any>(`${this.url + '/user'}`, { params })
            .pipe(
                catchError(this.handleError)
            );
    }

    public updateBookingRoom(id: any, data: any): Observable<any> {

        return this.http.put(`${this.url}/${id}`, data)
            .pipe(
                tap(response => { }),
                catchError(this.handleError)
            );
    }

    public cancelBookingRoomById(id: any, status:any): Observable<any> {
        const formData: FormData = new FormData();
        formData.append('id', id);
        formData.append('status', status);
        return this.http.put<any>(`${this.url + '/updateStatus'}`, formData)
            .pipe(
                catchError(this.handleError)
            );
    }

    public deleteBookingRoom(id: any): Observable<any> {
        return this.http.delete(`${this.url}/${id}`)
            .pipe(
                tap(response => { }),
                catchError(this.handleError)
            );
    }
}
