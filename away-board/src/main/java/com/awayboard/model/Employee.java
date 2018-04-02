package com.awayboard.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang3.StringUtils;

/**
 * The Class Employee.
 */
@Entity
@Table(name = "employee", schema = "awayboard")
public class Employee {
	
	/** The teams. */
	private String teams;
	
	/** The name. */
	@Id
	private String name;
	
	/** The image URL. */
	private String imageURL;
	
	/** The current status. */
	private String currentStatus;
	
	/**
	 * Gets the teams.
	 *
	 * @return the teams
	 */
	public List<String> getTeams() {
		if(null!=teams) {
			return new ArrayList<String>(Arrays.asList(teams.split(",")));
		}
		else {
			return null;
		}
	}
	
	/**
	 * Sets the teams.
	 *
	 * @param teams the new teams
	 */
	public void setTeams(List<String> teams) {
		this.teams = StringUtils.join(teams,",");
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
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
	
	/**
	 * Gets the current status.
	 *
	 * @return the current status
	 */
	public String getCurrentStatus() {
		return currentStatus;
	}
	
	/**
	 * Sets the current status.
	 *
	 * @param currentStatus the new current status
	 */
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	
}