package nz.co.hamptonsoftware.employee.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import nz.co.hamptonsoftware.employee.data.EmployeeServiceDao;
import nz.co.hamptonsoftware.employee.domain.Employee;
import nz.co.hamptonsoftware.employee.domain.EmployeeDisplay;
import nz.co.hamptonsoftware.employee.exception.EmployeeServicesInvalidDataException;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeServiceDao employeeServiceDao;
	
	static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	
	@Inject
	public EmployeeServiceImpl(EmployeeServiceDao employeeServiceDao) throws DataAccessException {
		LOG.debug("Instantiating EmployeeServiceImple with dao[{}]", employeeServiceDao);
		this.employeeServiceDao = employeeServiceDao;
	}
	
	@Override
	public Employee retrieveEmployeeById(Integer id) throws DataAccessException, EmployeeServicesInvalidDataException {
		LOG.debug("Retrieve employee by id [{}]", id);
		return employeeServiceDao.getEmployeeById(id);
	}

	@Override
	public List<EmployeeDisplay> retrieveActiveEmployees() throws DataAccessException {
		LOG.debug("Retrieve all active employees");
		return employeeServiceDao.getAllActiveEmployees();
	}

	@Override
	public void updateEmployee(Integer id, Employee employee) throws DataAccessException, EmployeeServicesInvalidDataException {
		LOG.debug("Updating employee id [{}] with data [{}]",id,employee);
		employeeServiceDao.updateEmployee(id,employee);
	}

	@Override
	public void markEmployeeInactive(Integer id) throws DataAccessException, EmployeeServicesInvalidDataException {
		LOG.debug("Deactivate employee id [{}]",id);
		employeeServiceDao.deactivateEmployee(id);
	}

	@Override
	public Integer createEmployee(Employee employee) throws DataAccessException {
		LOG.debug("Create employee [{}]",employee);
		if (employee.getDateOfEmployment() == null) employee.setDateOfEmployment(new Date());
		employee.setStatus(Employee.STATUS_ACTIVE);
		return employeeServiceDao.createEmployee(employee);
	}

}
