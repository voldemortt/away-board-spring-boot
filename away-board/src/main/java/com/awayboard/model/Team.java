package com.awayboard.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class Team.
 */
@Entity
@Table(name = "team", schema = "awayboard")
public class Team {
	
	/** The id. */
	@Id
	@GeneratedValue
	private Long id;
	
	/** The team name. */
	private String teamName;
	
	/** The image URL. */
	private String imageURL;
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Gets the team name.
	 *
	 * @return the team name
	 */
	public String getTeamName() {
		return teamName;
	}
	
	/**
	 * Sets the team name.
	 *
	 * @param teamName the new team name
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	/**
	 * Gets the image URL.
	 *
	 * @return the image URL
	 */
	public String getImageURL() {
		return imageURL;
	}
	
	/**
	 * Sets the image URL.
	 *
	 * @param imageURL the new image URL
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	
}