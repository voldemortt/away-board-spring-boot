package com.awayboard.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import com.awayboard.model.Employee;
import com.awayboard.model.Team;
import com.awayboard.repository.EmployeeRepository;

/**
 * The Class EmployeeService.
 */
@Service
public class EmployeeService implements GenericService<Employee, String>{

	/** The employee repository. */
	private final EmployeeRepository employeeRepository;

	/** The team service. */
	private final TeamService teamService;

	/**
	 * Instantiates a new employee service.
	 *
	 * @param employeeRepository the employee repository
	 * @param teamService the team service
	 */
	public EmployeeService(final EmployeeRepository employeeRepository,final TeamService teamService) {
		this.employeeRepository = employeeRepository;
		this.teamService = teamService;
	}

	/* (non-Javadoc)
	 * @see com.awayboard.service.GenericService#getId(java.lang.Object)
	 */
	@Override
	public String getId(Employee employee) {
		return employee.getName();
	}

	/* (non-Javadoc)
	 * @see com.awayboard.service.GenericService#getRepository()
	 */
	@Override
	public CrudRepository<Employee, String> getRepository() {
		return this.employeeRepository;
	}

	/**
	 * Gets the employee by id.
	 *
	 * @param employeeName the employee name
	 * @return the employee by id
	 */
	public Employee getEmployeeById(final String employeeName) {
		Optional<Employee> employee = this.employeeRepository.findById(employeeName);
		if(employee.isPresent()) {
			return employee.get();
		}
		else {
			return null;
		}
	}

	/**
	 * Gets the employees by team.
	 *
	 * @param teamId the team id
	 * @return the employees by team
	 */
	public List<Employee> getEmployeesByTeam(final String teamId) {
		List<Employee> employees = this.employeeRepository.findAll();
		List<Employee> employeesOfTeam = new ArrayList<Employee>();
		for(Employee employee : employees) {
			for(String team : employee.getTeams()) {
				if(team.equalsIgnoreCase(teamId)) {
					employeesOfTeam.add(employee);
				}
			}
		}
		return employeesOfTeam;
	}

	/* (non-Javadoc)
	 * @see com.awayboard.service.GenericService#save(java.lang.Object)
	 */
	@Override
	public Employee save(Employee employee) {
		return GenericService.super.save(employee);
	}

	/* (non-Javadoc)
	 * @see com.awayboard.service.GenericService#update(java.lang.Object)
	 */
	@Override
	public void update(Employee employee) {
		GenericService.super.update(employee);
	}

	/* (non-Javadoc)
	 * @see com.awayboard.service.GenericService#delete(java.io.Serializable)
	 */
	@Override
	public void delete(String name) {
		GenericService.super.delete(name);
	}

	/**
	 * Update from board.
	 *
	 * @param employee the employee
	 * @return the string
	 */
	public String updateFromBoard(Employee employee) {
		Optional<Employee> emp = GenericService.super.get(employee.getName());
		if(emp.isPresent()) {
			employee.setTeams(emp.get().getTeams());
			GenericService.super.update(employee);
			return "Success";
		}
		else {
			return "No such employee found";
		}
	}

	/**
	 * Gets the all employees.
	 *
	 * @return the all employees
	 */
	public List<Employee> getAllEmployees(){
		List<Employee> employees = this.employeeRepository.findAll();	
		for(Employee employee : employees) {
			List<String> teams = employee.getTeams();
			List<String> teamNames = new ArrayList<String>();
			for(String teamId : teams) {
				Optional<Team> team = teamService.getTeamById(Long.parseLong(teamId));
				if(team.isPresent()) {
					teamNames.add(team.get().getTeamName());
				}
			}
			employee.setTeams(teamNames);
		}
		return employees;
	}

	/**
	 * Save from board.
	 *
	 * @param employee the employee
	 * @return the employee
	 */
	public Employee saveFromBoard(Employee employee) {
		Optional<Employee> emp = GenericService.super.get(employee.getName());
		if(emp.isPresent()) {
			Set<String> set = new HashSet<String>(emp.get().getTeams());
			if(null!=employee.getTeams().get(0)) {
				String teamId = employee.getTeams().get(0);
				if(set.add(teamId)) {
					List<String> list = new ArrayList<String>(set);
					employee.setTeams(list);
				}			
			}
		}
		return GenericService.super.save(employee);
	}

	/**
	 * Delete from board.
	 *
	 * @param employee the employee
	 * @return the string
	 */
	public String deleteFromBoard(Employee employee) {
		Optional<Employee> emp = GenericService.super.get(employee.getName());
		if(emp.isPresent()) {
			List<String> teams = emp.get().getTeams();
			String teamId = employee.getTeams().get(0);
			if(null!=teamId && teams.contains(teamId)) {
				Iterator<String> itr = teams.iterator();
				while (itr.hasNext())
				{
					String team = itr.next();
					if(team.equals(teamId)) {
						itr.remove();
					}
				}
				if(teams.isEmpty()) {
					GenericService.super.delete(employee.getName());
				}
				else {
					employee.setTeams(teams);
					GenericService.super.update(employee);
				}
			}
			return "Success";
		}
		else {
			return "No such employee found";
		}
	}

}