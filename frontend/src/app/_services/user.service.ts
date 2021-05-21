import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, filter, map, tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { User } from '../_models/user';
import { UserType } from '../_models/user-type.enum';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
    providedIn: 'root'
})
export class UserService {
    url = `${environment.baseUrlServer}` + 'api/user';
    urlCustomer = `${environment.baseUrlServer}` + 'api/customer';
    urlAccount = `${environment.baseUrlServer}` + 'api/account';
    urlGetUserType = `${environment.baseUrlServer}` + 'api/userType';
    urlGetRoles = `${environment.baseUrlServer}` + 'api/role';

    constructor(
        private http: HttpClient
    ) { }



    public getNameAllUser() {
        return this.http.get(`${this.url}`)
            .pipe(
                // map((response: []) => response.map(item => item['fullName'])),
                catchError(this.handleError)
            );
    }

    handleError(error: HttpErrorResponse) {
        return throwError(error);
    }

    public createAccount(data: any): Observable<any> {
        return this.http.post<any>(`${this.urlAccount}`, data, httpOptions)
            .pipe(
                catchError(this.handleError)
            );
    }

    public getAllUser(): Observable<User> {
        return this.http.get<User>(`${this.url}`)
            .pipe(
                catchError(this.handleError)
            );
    }

    public getUserById(id: any): Observable<User> {
        return this.http.get<User>(`${this.url}/${id}`)
            .pipe(
                catchError(this.handleError)
            );
    }

    public updateUserById(id: any, data: any): Observable<any> {
        return this.http.put(`${this.urlCustomer}/${id}`, data, httpOptions)
            .pipe(
                catchError(this.handleError)
            );
    }

    public updateAccountById(id: any, data: any): Observable<any> {
        return this.http.put(`${this.urlAccount}/${id}`, data, httpOptions)
            .pipe(
                catchError(this.handleError)
            );
    }

    public updateAvatar(id: any, file: File): Observable<any> {
        const formData: FormData = new FormData();
        formData.append('file', file);

        return this.http.put(`${this.url + '/updateAvatar'}/${id}`, formData)
            .pipe(
                tap(response => { }),
                catchError(this.handleError)
            );
    }

    public resetPassword(email: any): Observable<any> {
        return this.http.post(`${this.url + '/resetPassword?email='}${email}`, httpOptions)
            .pipe(
                catchError(this.handleError)
            );
    }

    public updatePassword(token: any): Observable<any> {
        return this.http.post(`${this.url + '/updatePassword?token='}${token}`, httpOptions)
            .pipe(
                catchError(this.handleError)
            );
    }

    public savePassword(token: any, newPassword: any): Observable<any> {
        return this.http.put(`${this.url + '/savePassword?token='}${token}${'&newPassword='}${newPassword}`, httpOptions)
            .pipe(
                catchError(this.handleError)
            );
    }

    // User Type
    public getAllUserType(): Observable<User> {
        return this.http.get<User>(`${this.urlGetUserType}`)
            .pipe(
                catchError(this.handleError)
            );
    }

    //Role
    public getAllRole(): Observable<User> {
        return this.http.get<User>(`${this.urlGetRoles}`)
            .pipe(
                catchError(this.handleError)
            );
    }
}