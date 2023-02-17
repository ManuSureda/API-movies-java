import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { LoginCredential } from '../models/login-credential';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  token = undefined;
  redirectUrl = undefined;
  loginUrl = "http://localhost:8080/api/auth/login";
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
    }),
    withCredentials: true
  };

  constructor(private http : HttpClient, private router : Router) { }

  login(loginCredential : LoginCredential): Promise<any> {
    const promise = this.http.post(this.loginUrl, loginCredential, this.httpOptions)
      .toPromise();

    console.log(promise);
    
    promise
      .then(response => {
        console.log(response);
        this.token = response['Authorization'];
        sessionStorage.setItem('token', this.token);
      })
      .catch(err => {
        console.log(err);
      });
    
    return promise;
  }  

}
