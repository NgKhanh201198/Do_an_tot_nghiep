import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class AppService {
    url = `${environment.baseUrlServer}` + 'api/count';

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
}
