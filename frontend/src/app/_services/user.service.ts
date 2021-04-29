import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { User } from '../_models/user';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
    providedIn: 'root'
})
export class UserService {
    urlUser = `${environment.baseUrlServer}` + 'api/user';

    constructor(
        private http: HttpClient
    ) { }

    handleError(error: HttpErrorResponse) {
        return throwError(error);
    }

    getUserById(id: any): Observable<User> {
        return this.http.get<User>(`${this.urlUser}/${id}`)
            .pipe(
                catchError(this.handleError)
            );
    }

    updateUserById(id: any, data: any): Observable<any> {
        return this.http.put(`${this.urlUser}/${id}`, data, httpOptions)
            .pipe(
                catchError(this.handleError)
            );
    }


}
