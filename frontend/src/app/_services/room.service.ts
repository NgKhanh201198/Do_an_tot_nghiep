import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {environment} from 'src/environments/environment';
import {LoggerService} from './logger.service';

const httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
    providedIn: 'root'
})
export class RoomService {

    url = `${environment.baseUrlServer}` + 'api/room';
    urlCheckRoomEmpty = `${environment.baseUrlServer}` + 'api/checkRoomEmpty';

    constructor(
        private http: HttpClient,
        private logger: LoggerService
    ) {
    }

    handleError(error: HttpErrorResponse): Observable<never> {
        return throwError(error);
    }

    public checkRoomEmpty(hotel: any, checkInDate: any, checkOutDate: any): Observable<any> {
        this.logger.loggerData('đã đến đây');
        const body = {
            hotel: hotel,
            checkInDate: checkInDate,
            checkOutDate: checkOutDate
        };
        this.logger.loggerData(body);
        return this.http.post<any>(`${this.urlCheckRoomEmpty}`, body)
            .pipe(
                catchError(this.handleError)
            );
    }

    public createRoom(data: any, file: File): Observable<any> {
        const formData: FormData = new FormData();
        formData.append('image', file);
        formData.append('roomNumber', data.roomNumber);
        formData.append('contents', data.contents);
        formData.append('roomCost', data.roomCost);
        formData.append('discount', data.discount);
        formData.append('numberOfPeople', data.numberOfPeople);
        formData.append('roomType', data.roomType);
        formData.append('hotel', data.hotel);
        formData.append('status', data.status);

        return this.http.post(`${this.url}`, formData)
            .pipe(
                tap(response => {
                    console.log(response);
                }),
                catchError(this.handleError)
            );
    }

    public getRoomAll(): Observable<any> {
        return this.http.get(`${this.url}`)
            .pipe(
                catchError(this.handleError)
            );
    }

    public getRoomById(id: any): Observable<any> {
        return this.http.get<any>(`${this.url}/${id}`)
            .pipe(
                catchError(this.handleError)
            );
    }

    public getRoomByHotel(hotelName: any): Observable<any> {
        const params = new HttpParams().append('hotelName', hotelName);
        return this.http.get<any>(`${this.url + '/byHotel'}`, {params})
            .pipe(
                catchError(this.handleError)
            );
    }

    public updateRoom(id: any, data: any): Observable<any> {
        return this.http.put(`${this.url}/${id}`, data)
            .pipe(
                tap(response => {
                }),
                catchError(this.handleError)
            );
    }

    public updateImage(id: any, file: File): Observable<any> {
        const formData: FormData = new FormData();
        formData.append('image', file);

        return this.http.put(`${this.url + '/updateImage'}/${id}`, formData)
            .pipe(
                tap(response => {
                }),
                catchError(this.handleError)
            );
    }

    public deleteRoom(id: any): Observable<any> {
        return this.http.delete(`${this.url}/${id}`)
            .pipe(
                tap(response => {
                }),
                catchError(this.handleError)
            );
    }
}
