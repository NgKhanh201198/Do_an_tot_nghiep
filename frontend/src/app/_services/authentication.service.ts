import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { User } from '../_models/user';


const API_LOGIN = `${environment.baseUrlServer}` + 'api/auth/login';
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

    constructor(private http: HttpClient) {
        this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
        this.currentUser = this.currentUserSubject.asObservable();
    }

    login(username: string, password: string) {
        return this.http.post<any>(API_LOGIN, { username, password }, httpOptions)
            .pipe(
                map((user: any) => {
                    this.storeToken(user.accessToken);
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
        window.sessionStorage.clear();
        this.currentUserSubject.next(null);
    }

    public get currentUserValue(): User {
        return this.currentUserSubject.value;
    }

    public storeToken(token: string) {
        window.sessionStorage.removeItem(TOKEN_KEY);
        window.sessionStorage.setItem(TOKEN_KEY, token);
    }
    public getToken(): string {
        return window.sessionStorage.getItem(TOKEN_KEY);
    }
    public storeUser(user: string) {
        window.sessionStorage.removeItem(USER_KEY);
        window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
    }
    public getUser(): any {
        return JSON.parse(window.sessionStorage.getItem(USER_KEY));
    }

    isLoggedIn(): Boolean {
        return !!this.getToken();
    }
}
