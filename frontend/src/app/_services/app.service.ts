import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
    providedIn: 'root'
})
export class AppService {
    url = `${environment.baseUrlServer}` + 'api/count';
    urlEmail = `${environment.baseUrlServer}` + 'api/auth';

    constructor(
        private http: HttpClient
    ) { }

    handleError(error: HttpErrorResponse) {
        return throwError(error);
    }

    public count(): Observable<any> {
        return this.http.get<any>(`${this.url}`)
            .pipe(
                catchError(this.handleError)
            );
    }

    public verifyEmail(token: any): Observable<any> {
        const params = new HttpParams().append('token', token);
        return this.http.get(`${this.urlEmail + '/verifyEmail'}`, { params })
            .pipe(
                catchError(this.handleError)
            );
    }
}
