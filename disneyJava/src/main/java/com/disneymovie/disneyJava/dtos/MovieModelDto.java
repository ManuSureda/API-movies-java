package com.disneymovie.disneyJava.dtos;

import com.disneymovie.disneyJava.models.CharacterModel;
import com.disneymovie.disneyJava.models.MovieGenreModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.platform.commons.util.StringUtils;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieModelDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer idMovie;
    private String imgUrl;
    private String tittle;
    private Date releaseDate;
    private Integer score;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<MovieGenreModel> genres = new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Integer> genresIdList = new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CharacterModel> characters = new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Integer> charactersIdList = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public boolean isValid() {
        return !StringUtils.isBlank(imgUrl) &&
                !StringUtils.isBlank(tittle) &&
                releaseDate != null &&
                score > 0 && score < 6;
    }
}
