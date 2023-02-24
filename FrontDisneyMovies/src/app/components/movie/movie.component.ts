import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MovieModel } from 'src/app/models/movie-model';
import { CharacterService } from 'src/app/services/character.service';
import { MovieService } from 'src/app/services/movie.service';

@Component({
  selector: 'app-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.css']
})
export class MovieComponent implements OnInit {
  resumArray : Array<MovieModel> = [];
  
  constructor(private movieService : MovieService, private characterService : CharacterService, private router: Router, private route: ActivatedRoute) { }
  
  ngOnInit(): void {

    const state = window.history.state;
    if (state && state.id) {
      console.log(state.id);
      
      this.showMovieById(state.id);
      
    } else {      
      const defaultMoviePoster : string = "assets/defaultMovie.jpg";
      const promise = this.movieService.getAllMovieResume();
      
      promise
        .then(response => {
          response.forEach(resume => {
            let movie : MovieModel = new MovieModel(); 
            
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

  showMovieById(idMovie : number) {
    
  }
}
