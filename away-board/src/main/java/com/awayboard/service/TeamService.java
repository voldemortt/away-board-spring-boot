package com.awayboard.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import com.awayboard.model.Team;
import com.awayboard.repository.TeamRepository;

/**
 * The Class TeamService.
 */
@Service
public class TeamService implements GenericService<Team, Long>{
	
	/** The team repository. */
	private final TeamRepository teamRepository;
	
	/**
	 * Instantiates a new team service.
	 *
	 * @param teamRepository the team repository
	 */
	public TeamService(final TeamRepository teamRepository) {
		this.teamRepository = teamRepository;
	}

	/* (non-Javadoc)
	 * @see com.awayboard.service.GenericService#getId(java.lang.Object)
	 */
	@Override
	public Long getId(Team team) {
		return team.getId();
	}

	/* (non-Javadoc)
	 * @see com.awayboard.service.GenericService#getRepository()
	 */
	@Override
	public CrudRepository<Team, Long> getRepository() {
		return this.teamRepository;
	}
	
	/**
	 * Gets the team by id.
	 *
	 * @param teamId the team id
	 * @return the team by id
	 */
	public Optional<Team> getTeamById(final Long teamId) {
		return this.teamRepository.findById(teamId);
	}
	
	/* (non-Javadoc)
	 * @see com.awayboard.service.GenericService#save(java.lang.Object)
	 */
	@Override
	public Team save(Team team) {
		return GenericService.super.save(team);
	}
	
	/**
	 * Gets the all teams.
	 *
	 * @return the all teams
	 */
	public List<Team> getAllTeams(){
		List<Team> teams = this.teamRepository.findAll();		
		return teams;
	}

}