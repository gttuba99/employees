package nz.co.hamptonsoftware.employee.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import nz.co.hamptonsoftware.employee.config.DaoTestAppConfig;
import nz.co.hamptonsoftware.employee.domain.Employee;
import nz.co.hamptonsoftware.employee.domain.EmployeeDisplay;
import nz.co.hamptonsoftware.employee.exception.EmployeeServicesException;
import nz.co.hamptonsoftware.employee.exception.EmployeeServicesInvalidDataException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DaoTestAppConfig.class })
@Transactional
@Rollback(true)
public class EmployeeServicesDaoTests extends AbstractTransactionalJUnit4SpringContextTests {

	private EmployeeServiceDao dao;
	
	static final Logger LOG = LoggerFactory.getLogger(EmployeeServicesDaoTests.class);
	
	@Before
	public void setUp() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://localhost:3306/employees");
//        dataSource.setUsername("guest_user");
//        dataSource.setPassword("guest_password");
        EmbeddedDatabase dataSource = new EmbeddedDatabaseBuilder()
	            .setType(EmbeddedDatabaseType.HSQL)
	            .addScript("classpath:jdbc/schema.sql")
	            .addScript("classpath:jdbc/test-data.sql").build();
        dao = new EmployeeJdbcDaoImpl(dataSource);
	}

	@After
	public void tearDown() throws Exception {
	}

    @Rule
    public ExpectedException thrown= ExpectedException.none();
	
	@Test
	public final void testGetEmployee_SUCCESS() 
			throws EmployeeServicesException, DataAccessException, EmployeeServicesInvalidDataException {
		
		final String FIRST_NAME = "Paul";
		final String MIDDLE_INITIAL = "P";
		final String LAST_NAME = "Peterson";
		final String DATE_OF_BIRTH = "1983-03-03";
		final String DATE_OF_EMPLOYMENT = "2018-10-10";
		Employee emp = dao.getEmployeeById(2);

		EmployeeDisplay ed = new EmployeeDisplay(emp);
		assertEquals(FIRST_NAME, ed.getFirstName());
		assertEquals(MIDDLE_INITIAL, ed.getMiddleInitial());
		assertEquals(LAST_NAME, ed.getLastName());
		assertEquals(DATE_OF_BIRTH, ed.getDateOfBirth());
		assertEquals(DATE_OF_EMPLOYMENT, ed.getDateOfEmployment());
	}
	
	@Test
	public final void testGetEmployee_INVALID() 
			throws EmployeeServicesException, DataAccessException, EmployeeServicesInvalidDataException {
		
		thrown.expect(EmployeeServicesInvalidDataException.class);
		thrown.expectMessage("No employee found with id [999]");
	     
		dao.getEmployeeById(999);
	}
	
	@Test 
	public final void testGetAllEmployees() 
			throws EmployeeServicesException {
		
		List<EmployeeDisplay> emps = dao.getAllActiveEmployees();
		assertNotNull(emps);
		
	}
	
	@Test
	public final void testCreateEmployee_SUCCESS() 
			throws EmployeeServicesException, DataAccessException, EmployeeServicesInvalidDataException, ParseException {
		
		final String FIRST_NAME = "Fred";
		final String MIDDLE_INITIAL = "F";
		final String LAST_NAME = "Furgason";
		final String DATE_OF_BIRTH = "1978-12-12";
		final String DATE_OF_EMPLOYMENT = "2018-10-10";
		Employee employee = new Employee(new Integer(0), FIRST_NAME, MIDDLE_INITIAL, LAST_NAME, 
				new SimpleDateFormat("yyyy-MM-dd").parse(DATE_OF_BIRTH), new SimpleDateFormat("yyyy-MM-dd").parse(DATE_OF_EMPLOYMENT), new Integer(0));
		Integer id = dao.createEmployee(employee);
		
		assertTrue(id.intValue() > 0);
	}
	
	@Test
	public final void testUpdateEmployee_SUCCESS() 
			throws EmployeeServicesException, ParseException, DataAccessException, EmployeeServicesInvalidDataException {
		
		final String FIRST_NAME = "Paul";
		final String MIDDLE_INITIAL = "P";
		final String LAST_NAME = "Peterson";
		final String DATE_OF_BIRTH = "1983-03-03";
		final String DATE_OF_EMPLOYMENT = "2018-10-09";
		Employee employee = new Employee(new Integer(0), FIRST_NAME, MIDDLE_INITIAL, LAST_NAME, 
				new SimpleDateFormat("yyyy-MM-dd").parse(DATE_OF_BIRTH), new SimpleDateFormat("yyyy-MM-dd").parse(DATE_OF_EMPLOYMENT), 
				new Integer(Employee.STATUS_ACTIVE));
		dao.updateEmployee(new Integer(3), employee);
	}
	
	@Test
	public final void testDeactivateEmployee()
			throws EmployeeServicesException, DataAccessException, EmployeeServicesInvalidDataException {
		
		dao.deactivateEmployee(4);
	
	}

}
