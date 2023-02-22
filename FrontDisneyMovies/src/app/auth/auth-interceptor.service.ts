import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor {

  constructor(private router : Router) { }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    console.log("--------------------------------------------------------------------------");
    console.log("comienzo auth-interceptor");
    
    const token = sessionStorage.getItem('token');

    let request = req;
    
    if (token) {
      console.log("entre al if token");
      console.log(request);
      console.log(token);
      console.log(request.headers);
      
      request = req.clone({ 
        setHeaders: {
          'authorization': `${ token }`, 
        } 
      });

      console.log(request);
      console.log("salgo del if token");
      
    }
    
    console.log(token);
    console.log(request);

    console.log("fin auth-interceptor");
    console.log("--------------------------------------------------------------------------");
    
    

    return next.handle(request).pipe(
      catchError((err : HttpErrorResponse) => {
        if (err.status === 401) {
          this.router.navigateByUrl('/auth/login');
        } else if (err.status === 403) {
          this.router.navigateByUrl('/auth/login');
        }
        return throwError(err);
      })
    );

  }
}
