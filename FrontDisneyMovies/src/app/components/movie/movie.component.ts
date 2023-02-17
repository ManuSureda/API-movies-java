import { Component, OnInit } from '@angular/core';
import { Movie } from 'src/app/models/movie';
import { MovieService } from 'src/app/services/movie.service';

@Component({
  selector: 'app-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.css']
})
export class MovieComponent implements OnInit {

  constructor(private movieService : MovieService) { }

  ngOnInit(): void {

  }

}
