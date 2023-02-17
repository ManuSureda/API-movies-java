import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  apiUrl: string = "http://localhost:8080/api/movies";

  constructor(private http : HttpClient) { }

  getAllMovieResume(): Promise<any> {
    return this.http.get(this.apiUrl)
    .toPromise();
  }
}
