import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';


const API_LOGIN = `${environment.baseUrlServe}` + 'api/auth/login';
const TOKEN_KEY = 'TOKEN';
const USER_KEY = 'USER';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor() { }
}
