package com.awayboard.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.awayboard.model.Team;

/**
 * The Interface TeamRepository.
 */
@Transactional
public interface TeamRepository extends JpaRepository<Team,Long> {	

}