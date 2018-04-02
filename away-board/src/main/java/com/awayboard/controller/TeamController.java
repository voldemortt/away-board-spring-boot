package com.awayboard.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.awayboard.model.Team;
import com.awayboard.service.TeamService;
import com.awayboard.vo.ResponseVO;

/**
 * The Class TeamController.
 */
@Controller
public class TeamController {

	/** The team service. */
	private final TeamService teamService;

	/**
	 * Instantiates a new team controller.
	 *
	 * @param teamService the team service
	 */
	TeamController(final TeamService teamService){
		this.teamService = teamService;
	}

	/**
	 * Show team page.
	 *
	 * @return the string
	 */
	@GetMapping("/team")
	public String showTeamPage() {
		return "team";
	}
	
	/**
	 * Show edit team page.
	 *
	 * @return the string
	 */
	@GetMapping("/editTeam")
	public String showEditTeamPage() {
		return "editTeam";
	}

	/**
	 * Away board.
	 *
	 * @param id the id
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/awayboard")
	public String awayBoard(@RequestParam(name="id", required=true) Long id, Model model) {
		model.addAttribute("teamID", id);
		return "awayboard";
	}

	/**
	 * Gets the all teams.
	 *
	 * @return the all teams
	 */
	@GetMapping(value = "/getAllTeams",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Team>> getAllTeams() {
		List<Team> teams= teamService.getAllTeams();
		return ResponseEntity
				.created(URI.create("/allTeams"))
				.body(teams);
	}

	/**
	 * Save rating.
	 *
	 * @param team the team
	 * @return the response entity
	 */
	@PostMapping(value = "/createTeam", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<Team>> saveRating(@RequestBody Team team) {

		Team savedTeam = teamService.save(team);
		return ResponseEntity
				.created(URI.create("/" + savedTeam.getId() + "/TeamName/" + savedTeam.getTeamName().replaceAll(" ","%20")))
				.body(new ResponseVO<>(savedTeam));
	}
	
	/**
	 * Update.
	 *
	 * @param team the team
	 * @return the response entity
	 */
	@PostMapping(value = "/updateTeam", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> update(@RequestBody Team team) {	
		teamService.update(team);
		return ResponseEntity.ok().body("Success");
	}

	/**
	 * Delete.
	 *
	 * @param team the team
	 * @return the response entity
	 */
	@PostMapping(value = "/deleteTeam", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> delete(@RequestBody Team team) {
		teamService.delete(team.getId());
		return ResponseEntity.ok().body("Success");
	}

}