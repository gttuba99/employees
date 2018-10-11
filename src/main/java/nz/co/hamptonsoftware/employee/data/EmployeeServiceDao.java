package nz.co.hamptonsoftware.employee.data;

import java.util.List;

import org.springframework.dao.DataAccessException;

import nz.co.hamptonsoftware.employee.domain.Employee;
import nz.co.hamptonsoftware.employee.domain.EmployeeDisplay;
import nz.co.hamptonsoftware.employee.exception.EmployeeServicesInvalidDataException;

public interface EmployeeServiceDao {

	public Employee getEmployeeById(Integer id) throws DataAccessException, EmployeeServicesInvalidDataException;

	public List<EmployeeDisplay> getAllActiveEmployees() throws DataAccessException;

	public void updateEmployee(Integer id, Employee employee) throws DataAccessException, EmployeeServicesInvalidDataException;

	public void deactivateEmployee(Integer id) throws DataAccessException, EmployeeServicesInvalidDataException;

	public Integer createEmployee(Employee employee) throws DataAccessException;

}
