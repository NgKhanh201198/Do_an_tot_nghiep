import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
    providedIn: 'root'
})
export class CityService {
    url = `${environment.baseUrlServer}` + 'api/city';
    urlTop5 = `${environment.baseUrlServer}` + 'api/cityTop5';

    constructor(
        private http: HttpClient
    ) { }

    handleError(error: HttpErrorResponse) {
        return throwError(error);
    }

    public createCity(data: any, file: File): Observable<any> {
        const formData: FormData = new FormData();
        formData.append('image', file);
        formData.append('cityName', data.cityName);
        formData.append('description', data.description);

        return this.http.post(`${this.url}`, formData)
            .pipe(
                tap(response => {
                    console.log(response);
                }),
                catchError(this.handleError)
            );
    }

    public getCityAll(): Observable<any> {
        return this.http.get(`${this.url}`)
            .pipe(
                catchError(this.handleError)
            );
    }

    public getCityTop5(): Observable<any> {
        return this.http.get(`${this.urlTop5}`)
            .pipe(
                catchError(this.handleError)
            );
    }

    public getCityById(id: any): Observable<any> {
        return this.http.get<any>(`${this.url}/${id}`)
            .pipe(
                catchError(this.handleError)
            );
    }

    public updateCity(id: any, data: any): Observable<any> {

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

    public deleteCity(id: any): Observable<any> {
        return this.http.delete(`${this.url}/${id}`)
            .pipe(
                tap(response => { }),
                catchError(this.handleError)
            );
    }
}
