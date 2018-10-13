package nz.co.hamptonsoftware.employee.data;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import nz.co.hamptonsoftware.employee.domain.Employee;
import nz.co.hamptonsoftware.employee.domain.EmployeeDisplay;
import nz.co.hamptonsoftware.employee.exception.EmployeeServicesInvalidDataException;

@Repository("employeeServiceDao")
public class EmployeeJdbcDaoImpl extends NamedParameterJdbcDaoSupport implements EmployeeServiceDao {
	static final Logger LOG = LoggerFactory.getLogger(EmployeeJdbcDaoImpl.class);

	@Inject @Named("dataSource")
	public EmployeeJdbcDaoImpl(DataSource dataSource) {
		LOG.debug("Instantiating EmployeeJdbcDao with datasource[{}]", dataSource);
		setDataSource(dataSource);
	}

	@Override
	public Employee getEmployeeById(Integer id) throws DataAccessException, EmployeeServicesInvalidDataException {
		String selectString = "SELECT * FROM employees WHERE id = :id";
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
		List<Employee> employeeList = getNamedParameterJdbcTemplate().query(selectString, namedParameters, new BeanPropertyRowMapper<Employee>(Employee.class));
		if (employeeList == null || employeeList.size() <= 0) {
			LOG.info("No employee found with id [{}]",id);
			throw new EmployeeServicesInvalidDataException("No employee found with id ["+id+"]");
		}
		LOG.debug("Found employee [{}]",employeeList.get(0));
		return employeeList.get(0);
	}

	@Override
	public List<EmployeeDisplay> getAllActiveEmployees() throws DataAccessException {
		String query = "SELECT * FROM employees WHERE status = :status";
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("status", Employee.STATUS_ACTIVE);
		List<EmployeeDisplay> response = getNamedParameterJdbcTemplate().query(
		    query, namedParameters, new EmployeeRowMapper());
		if (LOG.isDebugEnabled()) {
			for (EmployeeDisplay emp : response) {
				LOG.debug("Retrieved employee [{}]",emp);
			}
		}
		return response;
	}

	@Override
	public void updateEmployee(Integer id, Employee employee) throws DataAccessException, EmployeeServicesInvalidDataException {
		String query = "UPDATE employees set firstName = :firstName, middleInitial = :middleInitial, lastName = :lastName, " +
				"dateOfBirth = :dateOfBirth, dateOfEmployment = :dateOfEmployment, status = :status where id = :id";
		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("firstName", employee.getFirstName())
				.addValue("middleInitial", employee.getMiddleInitial())
				.addValue("lastName", employee.getLastName())
				.addValue("dateOfBirth", employee.getDateOfBirth())
				.addValue("dateOfEmployment", employee.getDateOfEmployment())
				.addValue("status", employee.getStatus())
				.addValue("id", id);
		int numRowsUpdated = getNamedParameterJdbcTemplate().update(query, namedParameters);
		if (numRowsUpdated < 1) {
			LOG.info("Employee status NOT updated for id [{}]",id);
			throw new EmployeeServicesInvalidDataException("Employee status NOT updated");
		}
	}

	@Override
	public void deactivateEmployee(Integer id) throws DataAccessException, EmployeeServicesInvalidDataException {
		String query = "UPDATE employees set status = :status where id = :id";
		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("status", Employee.STATUS_INACTIVE)
				.addValue("id", id);
		int numRowsUpdated = getNamedParameterJdbcTemplate().update(query, namedParameters);
		if (numRowsUpdated < 1) {
			LOG.info("Employee status NOT updated for id [{}]",id);
			throw new EmployeeServicesInvalidDataException("Employee status NOT updated");
		}
	}

	@Override
	public Integer createEmployee(Employee employee) throws DataAccessException {
	    String insertString = "INSERT INTO employees "
	    	      + "(firstName,middleInitial,lastName,dateOfBirth,dateOfEmployment) VALUES "
	    	      + "(:firstName, :middleInitial, :lastName, :dateOfBirth, :dateOfEmployment) ";
	    SqlParameterSource sqlParameters = new BeanPropertySqlParameterSource(employee);
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    getNamedParameterJdbcTemplate().update(insertString, sqlParameters, keyHolder);
	    Integer id = new Integer(keyHolder.getKey().intValue());
	    LOG.debug("Created employee with id [{}]",id.toString());
	    return id;
	}

}
