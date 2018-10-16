package nz.co.hamptonsoftware.employee.api;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nz.co.hamptonsoftware.employee.domain.Employee;
import nz.co.hamptonsoftware.employee.domain.EmployeeDisplay;
import nz.co.hamptonsoftware.employee.domain.RestErrorMessage;
import nz.co.hamptonsoftware.employee.exception.EmployeeServicesException;
import nz.co.hamptonsoftware.employee.exception.EmployeeServicesInvalidDataException;
import nz.co.hamptonsoftware.employee.service.EmployeeService;

@RestController("employeeController")
@RequestMapping("api/v1")
public class EmployeeController {
	
	private EmployeeService employeeService;
	
	static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

	@Inject @Named("employeeService")
	public EmployeeController(EmployeeService employeeService) {
		LOG.debug("Instantiating EmployeeController with employeeService[{}]", employeeService);
		this.employeeService = employeeService;
	}
	
	/**
	 * Retrieves the employee information by a given id
	 * @param id
	 * @return an <pre>Employee</pre> object
	 * @throws EmployeeServicesInvalidDataException 
	 * @throws DataAccessException 
	 */
	@ApiResponses(value = {	@ApiResponse(code = 403, message = "Access Denied - Must be authenticated to use this method"),
							@ApiResponse(code = 404, message = "No employee found for id", response=RestErrorMessage.class)})
	@ApiOperation(value = "Retrieves the employee information by a given id",
	 			  notes = "")
	@RequestMapping(value="/employee/{id}", method=RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<EmployeeDisplay> 
			handleRetrieveEmployeeById(@PathVariable("id") Integer id) 
							throws EmployeeServicesException, DataAccessException, EmployeeServicesInvalidDataException {
		
		LOG.info("Received REST call to retrieve employee id[{}]", id);
		
		Employee response = employeeService.retrieveEmployeeById(id);	
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri());
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		
		return new ResponseEntity<EmployeeDisplay>(new EmployeeDisplay(response), headers, HttpStatus.OK); 	
	}
	
	/**
	 * Retrieves all active employees
	 * @return a list of <pre>Employee</pre>
	 * @throws EmployeeServicesConfigurationMissingException
	 */
	@ApiResponses(value = {	@ApiResponse(code = 403, message = "Access Denied - Must be authenticated to use this method"),
							@ApiResponse(code = 404, message = "No employees found", response=RestErrorMessage.class)})
	@ApiOperation(value = "Retrieves all active employees",
	 			  notes = "")
	@RequestMapping(value="/employees", method=RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<EmployeeDisplay>> 
			handleRetrieveAllActiveEmployees() throws EmployeeServicesException {
		
		LOG.info("Received REST call to retrieve all active employees");
		
		List<EmployeeDisplay> response = employeeService.retrieveActiveEmployees();	
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ServletUriComponentsBuilder
				.fromCurrentRequest().build().toUri());
		headers.add("Content-Type", "application/json; charset=utf-8");
		
		return new ResponseEntity<List<EmployeeDisplay>>(response, headers, HttpStatus.OK); 	
	}
	
	/**
	 * Creates a new employee
	 * @return complete record of the newly created employee
	 * @throws EmployeeServicesConfigurationMissingException
	 */
	@ApiResponses(value = {	@ApiResponse(code = 403, message = "Access Denied - Must be authenticated to use this method"),
							@ApiResponse(code = 404, message = "Employee not found for id", response=RestErrorMessage.class)})
	@ApiOperation(value = "Creates a new employee",
	 			  notes = "")
	@RequestMapping(value="/employee", 
					method=RequestMethod.PUT, 
					produces={MediaType.APPLICATION_JSON_VALUE}, 
					consumes={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Employee>
				handleCreateEmployee(@RequestBody Employee employee)
												throws EmployeeServicesException {
		 
		LOG.info("Received REST call to create employee");

		Employee response = employeeService.createEmployee(employee);	
		HttpHeaders headers = new HttpHeaders();
		
		headers.setLocation(ServletUriComponentsBuilder
				.fromCurrentRequest().build().toUri());
		
		return new ResponseEntity<Employee>(response, headers, HttpStatus.ACCEPTED); 	
	}
	
	/**
	 * Updates an existing employee
	 * @param id
	 * @throws EmployeeServicesConfigurationMissingException
	 * @throws EmployeeServicesInvalidDataException 
	 */
	@ApiResponses(value = {	@ApiResponse(code = 403, message = "Access Denied - Must be authenticated to use this method"),
							@ApiResponse(code = 404, message = "Employee not found for id", response=RestErrorMessage.class)})
	@ApiOperation(value = "Updates an employee for the given id",
	 			  notes = "")
	@RequestMapping(value="/employee/{id}", 
					method=RequestMethod.PUT, 
					produces={MediaType.APPLICATION_JSON_VALUE}, 
					consumes={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Void>
				handleUpdateEmployee(@PathVariable("id") Integer id, 
													@RequestBody Employee employee)
												throws EmployeeServicesException, 
													   EmployeeServicesInvalidDataException {
		 
		LOG.info("Received REST call to update employee id[{}]", id);

		employeeService.updateEmployee(id, employee);	
		HttpHeaders headers = new HttpHeaders();
		
		headers.setLocation(ServletUriComponentsBuilder
				.fromCurrentRequest().build().toUri());
		
		return new ResponseEntity<Void>(headers, HttpStatus.ACCEPTED); 	
	}
	
	/**
	 * Marks an existing employee as inactive
	 * @param id
	 * @throws EmployeeServicesConfigurationMissingException
	 * @throws EmployeeServicesInvalidDataException 
	 */
	@ApiResponses(value = {	@ApiResponse(code = 403, message = "Access Denied - Must be authenticated to use this method"),
							@ApiResponse(code = 404, message = "Employee not found for id", response=RestErrorMessage.class)})
	@ApiOperation(value = "Marks an existing employee as inactive",
	 			  notes = "")
	@RequestMapping(value="/employee/{id}", 
					method=RequestMethod.DELETE, 
					produces={MediaType.APPLICATION_JSON_VALUE}, 
					consumes={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Void>
				handleMarkEmployeeInactive(@PathVariable("id") Integer id)
												throws EmployeeServicesException, 
													   EmployeeServicesInvalidDataException {
		 
		LOG.info("Received REST call to mark inactive employee id[{}]", id);

		employeeService.markEmployeeInactive(id);	
		HttpHeaders headers = new HttpHeaders();
		
		headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri());
		
		return new ResponseEntity<Void>(headers, HttpStatus.ACCEPTED); 	
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(EmployeeServicesInvalidDataException.class)
	@ResponseBody RestErrorMessage handleInvalidDataError(HttpServletRequest req, Exception ex) {
	    return new RestErrorMessage(ex.getMessage());
	} 
	
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(DataAccessException.class)
	@ResponseBody RestErrorMessage handleDatabaseError(HttpServletRequest req, Exception ex) {
	    return new RestErrorMessage(ex.getMessage());
	} 
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(EmployeeServicesException.class)
	@ResponseBody RestErrorMessage handleGeneralError(HttpServletRequest req, Exception ex) {
	    return new RestErrorMessage(ex.getMessage());
	} 
	
}
