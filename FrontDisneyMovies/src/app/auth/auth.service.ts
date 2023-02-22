import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { LoginCredential } from '../models/login-credential';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  token = undefined;
  redirectUrl = undefined;
  loginUrl = "http://localhost:8080/auth/login";

  constructor(private http : HttpClient, private router : Router) { }

  login(loginCredential : LoginCredential): Promise<any> {

    console.log("entre en el metodo login de Auth.Service.ts");    

    const promise = this.http.post(this.loginUrl, loginCredential, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }), 
      observe: 'response' //tendre que borrar esto?
    })
    .toPromise();
    // const promise = this.http.post(this.loginUrl, loginCredential, {
       
    //   observe: 'response'
    // })
    // .toPromise();

    console.log("promise the this.http.post(this.loginUrl, loginCredential, {headers: new HttpHeaders({ 'Content-Type': 'application/json' }),observe: 'response'})");
    
    console.log(promise);

    promise
      .then(response => {
        console.log("jamaica");
        
        console.log(response);

        // Verificar si la respuesta es una instancia de HttpResponse
        if (response instanceof HttpResponse) {
          // console.log(response);
          console.log(response.headers);
          console.log(response.headers.get('authorization'));// en spring boot lo mando como Authorization pero aca lo toma con minuscula
  
          // Buscar el header de autorización en los headers
          const authorizationHeader = response.headers.get('authorization');
          if (authorizationHeader) {
            this.token = authorizationHeader;
            sessionStorage.setItem('token', this.token);
          } else {
            console.error('No se encontró el header de Authorization en la respuesta.');
          }
        }
      })
      .catch(err => {
        console.log("puerto rico");
        
        console.log(err);
      });
  
    return promise;
  }
  
}
