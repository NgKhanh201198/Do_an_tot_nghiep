import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { User } from '../_models/user';


const API_LOGIN = `${environment.baseUrlServer}` + 'api/auth/login';
const API_REGISTER = `${environment.baseUrlServer}` + 'api/auth/register';
const TOKEN_KEY = 'TOKEN';
const USER_KEY = 'CURRENT_USER';
const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {
    private currentUserSubject: BehaviorSubject<User>;
    public currentUser: Observable<User>;

    constructor(private http: HttpClient, private router: Router) {
        // this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(window.sessionStorage.getItem(USER_KEY)));
        // this.currentUser = this.currentUserSubject.asObservable();
        this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(window.localStorage.getItem(USER_KEY)));
        this.currentUser = this.currentUserSubject.asObservable();        
    }

    handleError(error: HttpErrorResponse) {
        return throwError(error);
    }

    public storeToken(token: string) {
        window.sessionStorage.removeItem(TOKEN_KEY);
        window.sessionStorage.setItem(TOKEN_KEY, token);
        // window.localStorage.removeItem(TOKEN_KEY);
        // window.localStorage.setItem(TOKEN_KEY, token);
    }
    public getToken(): string {
        return window.sessionStorage.getItem(TOKEN_KEY);
        // return window.localStorage.getItem(TOKEN_KEY);
    }
    public storeUser(user: string) {
        // window.sessionStorage.removeItem(USER_KEY);
        // window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
        window.localStorage.removeItem(USER_KEY);
        window.localStorage.setItem(USER_KEY, JSON.stringify(user));
    }
    public getUser(): any {
        // return JSON.parse(window.sessionStorage.getItem(USER_KEY));
        return JSON.parse(window.localStorage.getItem(USER_KEY));
    }

    public get currentUserValue(): User {
        return this.currentUserSubject.value;
    }

    login(username: any, password: any): Observable<any> {
        return this.http.post(API_LOGIN, { username, password }, httpOptions)
            .pipe(
                map((user: any) => {
                    this.storeToken(user.token);
                    this.storeUser(user);
                    this.currentUserSubject.next(user);
                    return user;
                }),
                catchError((error) => {
                    return throwError(error);
                })
            );
    }

    logout() {
        // window.sessionStorage.clear();
        window.localStorage.clear();
        this.currentUserSubject.next(null);
    }

    register(data: any): Observable<any> {
        return this.http.post(API_REGISTER, data, httpOptions)
            .pipe(
                catchError(this.handleError)
            );
    }

    isLoggedIn(): Boolean {
        return !!this.getToken();
    }
}
