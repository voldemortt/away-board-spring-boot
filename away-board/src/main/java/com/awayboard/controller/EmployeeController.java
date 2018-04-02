package com.awayboard.controller;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.awayboard.model.Employee;
import com.awayboard.service.EmployeeService;
import com.awayboard.vo.ResponseVO;

/**
 * The Class EmployeeController.
 */
@Controller
public class EmployeeController {

	/** The employee service. */
	private final EmployeeService employeeService;

	@Autowired
	private SimpMessagingTemplate template;

	/**
	 * Instantiates a new employee controller.
	 *
	 * @param employeeService the employee service
	 */
	EmployeeController(final EmployeeService employeeService){
		this.employeeService = employeeService;
	}

	/**
	 * Show employee page.
	 *
	 * @return the string
	 */
	@GetMapping("/employee")
	public String showEmployeePage() {
		return "employee";
	}

	/**
	 * Show edit employee page.
	 *
	 * @return the string
	 */
	@GetMapping("/editEmployee")
	public String showEditEmployeePage() {
		return "editEmployee";
	}

	/**
	 * Gets the all employees.
	 *
	 * @return the all employees
	 */
	@GetMapping(value = "/getAllEmployees",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> employees= employeeService.getAllEmployees();
		return ResponseEntity
				.created(URI.create("/getAllEmployees/"))
				.body(employees);
	}

	/**
	 * Gets the employee by name.
	 *
	 * @param name the name
	 * @return the employee by name
	 */
	@GetMapping(value = "/getEmployeeByName",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> getEmployeeByName(@RequestParam(name="name", required=true) String name) {
		Employee employee= employeeService.getEmployeeById(name);
		return ResponseEntity
				.created(URI.create("/getEmployeeByName/"+name))
				.body(employee);
	}

	/**
	 * Creates the employee.
	 *
	 * @param employee the employee
	 * @return the response entity
	 */
	@PostMapping(value = "/createEmployee", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<Employee>> createEmployee(@RequestBody Employee employee) {

		Employee savedEmployee = employeeService.save(employee);
		return ResponseEntity
				.created(URI.create("/" + savedEmployee.getName()))
				.body(new ResponseVO<>(savedEmployee));
	}

	/**
	 * Creates the employee from board.
	 *
	 * @param employee the employee
	 * @return the response entity
	 */
	@PostMapping(value = "/createEmployeeFromBoard", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO<Employee>> createEmployeeFromBoard(@RequestBody Employee employee) {

		Employee savedEmployee = employeeService.saveFromBoard(employee);
		return ResponseEntity
				.created(URI.create("/" + savedEmployee.getName()))
				.body(new ResponseVO<>(savedEmployee));
	}

	/**
	 * Gets the employees by team.
	 *
	 * @param teamId the team id
	 * @return the employees by team
	 */
	@GetMapping(value = "/getEmployeesByTeam",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Employee>> getEmployeesByTeam(@RequestParam(name="teamId", required=true) Long teamId) {
		List<Employee> employees= employeeService.getEmployeesByTeam(""+teamId);
		return ResponseEntity
				.created(URI.create("/getEmployeesByTeam/"+teamId))
				.body(employees);
	}

	/**
	 * Update employee.
	 *
	 * @param employee the employee
	 * @return the response entity
	 */
	@PostMapping(value = "/updateEmployeeFromBoard", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> updateFromBoard(@RequestBody Employee employee) {	
		String res = employeeService.updateFromBoard(employee);
		sendMessage(employee.getTeams().get(0));	
		return ResponseEntity.ok().body(res);
	}

	/**
	 * Update.
	 *
	 * @param employee the employee
	 * @return the response entity
	 */
	@PostMapping(value = "/updateEmployee", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> update(@RequestBody Employee employee) {	
		employeeService.update(employee);
		return ResponseEntity.ok().body("Success");
	}

	/**
	 * Delete.
	 *
	 * @param employee the employee
	 * @return the response entity
	 */
	@PostMapping(value = "/deleteEmployee", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> delete(@RequestBody Employee employee) {
		employeeService.delete(employee.getName());
		return ResponseEntity.ok().body("Success");
	}

	/**
	 * Delete from board.
	 *
	 * @param employee the employee
	 * @return the response entity
	 */
	@PostMapping(value = "/deleteEmployeeFromBoard", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> deleteFromBoard(@RequestBody Employee employee) {		
		return ResponseEntity.ok().body(employeeService.deleteFromBoard(employee));
	}

	/**
	 * Send message.
	 *
	 * @param teamId the team id
	 */
	public void sendMessage(String teamId) {
		List<Employee> employees= employeeService.getEmployeesByTeam(""+teamId);
		this.template.convertAndSend("/topic/awayboard/getEmployeesByTeam",employees);
	}
}
