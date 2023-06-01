package com.wabs.website.repository;

import com.wabs.website.models.Match;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MatchRepository extends CrudRepository<Match, Long> {

    @Query("SELECT m FROM Match m JOIN m.playerMatchStats pms WHERE pms.player.id = :playerId")
    List<Match> findByPlayerId(@Param("playerId") Long playerId);

    @Query("SELECT count(m) FROM Match m")
    Long getNumberOfMatches();

}
