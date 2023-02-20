import { Component, OnInit } from '@angular/core';
import { MovieModel } from 'src/app/models/movie-model';
import { MovieService } from 'src/app/services/movie.service';

@Component({
  selector: 'app-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.css']
})
export class MovieComponent implements OnInit {
  resumArray : Array<MovieModel> = [];
  
  constructor(private movieService : MovieService) { }
  
  ngOnInit(): void {
    const defaultMoviePoster : string = "assets/defaultMovie.jpg";
    const promise = this.movieService.getAllMovieResume();
    
    promise
      .then(response => {
        response.forEach(resume => {
          let movie : MovieModel = new MovieModel(); // crea una nueva instancia de MovieModel para cada elemento en la respuesta
          
          if (resume.img_url === '') {
            movie.setImgUrl(defaultMoviePoster);
          } else {
            movie.setImgUrl(resume.img_url);
          }

          movie.setReleaseDate(new Date(resume.release_date));
          movie.setTittle(resume.tittle);

          this.resumArray.push(movie);          
        });
      });

  }

}
