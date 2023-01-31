package com.disneymovie.disneyJava.projections;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

public interface MovieProjection {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer getIdMovie();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer getScore();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String getImg_url();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String getTittle();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Date getRelease_date();
}
