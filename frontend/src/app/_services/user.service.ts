import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { User } from '../_models/user';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
    providedIn: 'root'
})
export class UserService {
    url = `${environment.baseUrlServer}` + 'api/user';

    constructor(
        private http: HttpClient
    ) { }

    handleError(error: HttpErrorResponse) {
        return throwError(error);
    }

    getUserById(id: any): Observable<User> {
        return this.http.get<User>(`${this.url}/${id}`)
            .pipe(
                catchError(this.handleError)
            );
    }

    updateUserById(id: any, data: any): Observable<any> {
        return this.http.put(`${this.url}/${id}`, data, httpOptions)
            .pipe(
                catchError(this.handleError)
            );
    }

    updateAvatar(id: any, file: File): Observable<any> {
        const formData: FormData = new FormData();
        formData.append('file', file);
        console.log();

        return this.http.put(`${this.url + '/updateAvatar'}/${id}`, formData)
            .pipe(
                tap(response => { }),
                catchError(this.handleError)
            );
    }

    resetPassword(email: any): Observable<any> {
        return this.http.post(`${this.url + '/resetPassword?email='}${email}`, httpOptions)
            .pipe(
                catchError(this.handleError)
            );
    }

    updatePassword(token: any): Observable<any> {
        return this.http.post(`${this.url + '/updatePassword?token='}${token}`, httpOptions)
            .pipe(
                catchError(this.handleError)
            );
    }

    savePassword(token: any, newPassword: any): Observable<any> {
        return this.http.put(`${this.url + '/savePassword?token='}${token}${'&newPassword='}${newPassword}`, httpOptions)
            .pipe(
                catchError(this.handleError)
            );
    }
}