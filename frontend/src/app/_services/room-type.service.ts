import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class RoomTypeService {

    url = `${environment.baseUrlServer}` + 'api/roomtype';

    constructor(
        private http: HttpClient
    ) { }

    handleError(error: HttpErrorResponse) {
        return throwError(error);
    }

    public createRoomType(data: any): Observable<any> {
        return this.http.post(`${this.url}`, data)
            .pipe(
                catchError(this.handleError)
            );
    }

    public getRoomTypeAll(): Observable<any> {
        return this.http.get(`${this.url}`)
            .pipe(
                catchError(this.handleError)
            );
    }

    public getRoomTypeById(id: any): Observable<any> {
        return this.http.get<any>(`${this.url}/${id}`)
            .pipe(
                catchError(this.handleError)
            );
    }

    public updateRoomType(id: any, data: any): Observable<any> {

        return this.http.put(`${this.url}/${id}`, data)
            .pipe(
                tap(response => { }),
                catchError(this.handleError)
            );
    }

    public deleteRoomType(id: any): Observable<any> {
        return this.http.delete(`${this.url}/${id}`)
            .pipe(
                tap(response => { }),
                catchError(this.handleError)
            );
    }

}
