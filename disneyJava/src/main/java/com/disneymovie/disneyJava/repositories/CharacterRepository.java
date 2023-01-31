package com.disneymovie.disneyJava.repositories;

import com.disneymovie.disneyJava.dtos.CharacterModelDto;
import com.disneymovie.disneyJava.models.CharacterModel;
import com.disneymovie.disneyJava.projections.CharacterProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterModel, Integer> {
    @Query(value = "select * from v_characters", nativeQuery = true)
    List<CharacterProjection> resumeAllCharacters();

    @Procedure(procedureName = "sp_create_character")
    Integer createCharacter(@Param("pImgUrl") String imgUrl,
                            @Param("pName") String name,
                            @Param("pAge") Integer age,
                            @Param("pWeight") Integer weight,
                            @Param("pStory") String story
    ) throws JpaSystemException;

    @Transactional
    @Modifying
    @Query(value = "insert into character_x_movie(id_movie, id_character) values (?, ?)", nativeQuery = true)
    void addMoviesToCharacter(Integer movieId, Integer newIdCharacter) throws SQLException;

    @Procedure(procedureName = "sp_update_character")
    void updateCharacter(@Param("pId_character") int idCharacter,
                         @Param("pImgUrl") String imgUrl,
                         @Param("pName") String name,
                         @Param("pAge") Integer age,
                         @Param("pWeight") Integer weight,
                         @Param("pStory") String story
    ) throws JpaSystemException;

    CharacterModel findByName(String name);

    List<CharacterModel> findByAge(Integer age);

    List<CharacterModel> findByWeight(Integer weight);

    @Query(value = "select * from v_characters_by_movie_name where id_movie = ? group by name", nativeQuery = true)
    List<CharacterModel> findByMovieId(Integer idMovie);
}
