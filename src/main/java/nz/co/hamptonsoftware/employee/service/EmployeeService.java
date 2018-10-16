package nz.co.hamptonsoftware.employee.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import nz.co.hamptonsoftware.employee.domain.Employee;
import nz.co.hamptonsoftware.employee.domain.EmployeeDisplay;
import nz.co.hamptonsoftware.employee.exception.EmployeeServicesException;
import nz.co.hamptonsoftware.employee.exception.EmployeeServicesInvalidDataException;

public interface EmployeeService {

	/**
	 * Retrieves the employee information by a given id
	 * @param id
	 * @return an <pre>Employee</pre> object
	 * @throws EmployeeServicesException
	 */
	@Transactional(readOnly=true)
	@PreAuthorize("isAuthenticated()")
	Employee retrieveEmployeeById(Integer id) throws DataAccessException, EmployeeServicesInvalidDataException;

	/**
	 * Retrieves all active employees
	 * @return a list of <pre>Employee</pre>
	 * @throws EmployeeServicesException
	 */
	@Transactional(readOnly=true)
	@PreAuthorize("isAuthenticated()")
	List<EmployeeDisplay> retrieveActiveEmployees() throws DataAccessException;

	/**
	 * Updates an existing employee
	 * @param id
	 * @return Employee
	 * @throws EmployeeServicesException
	 * @throws EmployeeServicesInvalidDataException 
	 */
	@Transactional(readOnly=true)
	@PreAuthorize("isAuthenticated()")
	void updateEmployee(Integer id, Employee employee) throws DataAccessException, EmployeeServicesInvalidDataException;

	/**
	 * Marks an existing employee as inactive
	 * @param id
	 * @throws EmployeeServicesException
	 * @throws EmployeeServicesInvalidDataException 
	 */
	@Transactional(readOnly=false)
	@PreAuthorize("isAuthenticated()")
	void markEmployeeInactive(Integer id) throws DataAccessException, EmployeeServicesInvalidDataException;

	/**
	 * Creates a new employee
	 * @return id
	 * @throws EmployeeServicesException
	 */
	@Transactional(readOnly=false)
	@PreAuthorize("isAuthenticated()")
	Employee createEmployee(Employee employee) throws DataAccessException;

}
