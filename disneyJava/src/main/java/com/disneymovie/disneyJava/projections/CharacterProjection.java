package com.disneymovie.disneyJava.projections;

import com.fasterxml.jackson.annotation.JsonInclude;

public interface CharacterProjection {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer getId_character();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer getAge();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer getWeight();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String getImg_url();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String getName();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String getStory();
}
