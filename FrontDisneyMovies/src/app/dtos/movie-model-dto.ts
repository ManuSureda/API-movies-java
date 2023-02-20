import { CharacterModel } from "../models/character-model";
import { MovieGenreModel } from "../models/movie-genre-model";

export class MovieModelDto {
    private idMovie: number;
    private imgUrl: String;
    private tittle: String;
    private releaseDate: Date;
    private score: number;
    private genres: Array<MovieGenreModel> = [];
    private genresIdList: Array<number> = [];
    private characters: Array<CharacterModel> = [];
    private charactersIdList: Array<number> = [];
}
