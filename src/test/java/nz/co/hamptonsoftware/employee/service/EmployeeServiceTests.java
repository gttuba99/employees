package nz.co.hamptonsoftware.employee.service;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import nz.co.hamptonsoftware.employee.config.ServiceTestAppConfig;
import nz.co.hamptonsoftware.employee.data.EmployeeServiceDao;
import nz.co.hamptonsoftware.employee.domain.Employee;
import nz.co.hamptonsoftware.employee.domain.EmployeeDisplay;
import nz.co.hamptonsoftware.employee.exception.EmployeeServicesException;
import nz.co.hamptonsoftware.employee.exception.EmployeeServicesInvalidDataException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServiceTestAppConfig.class })
public class EmployeeServiceTests extends EasyMockSupport {

	private EmployeeService service;
	private EmployeeServiceDao serviceDaoMock;
	
	static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceTests.class);
	
	private String ID_STR;
	private String FIRST_NAME;
	private String MIDDLE_INITIAL;
	private String LAST_NAME;
	private String DATE_OF_BIRTH_STR;
	private String DATE_OF_EMPLOYMENT_STR;
	private String STATUS_STR;
	private Integer ID;
	private Date DATE_OF_BIRTH;
	private Date DATE_OF_EMPLOYMENT;
	private Integer STATUS;
	private Employee EMPLOYEE;
	private Employee NEW_EMPLOYEE;
	private EmployeeDisplay EMPLOYEE_DISPLAY;
	private EmployeeDisplay NEW_EMPLOYEE_DISPLAY;
	private List<EmployeeDisplay> EMPLOYEE_LIST;
	
	private SimpleDateFormat sdf;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		serviceDaoMock = createStrictMock(EmployeeServiceDao.class);
		service = new EmployeeServiceImpl(serviceDaoMock);
		
		ID_STR = "1";
		FIRST_NAME = "Paul";
		MIDDLE_INITIAL = "P";
		LAST_NAME = "Peterson";
		DATE_OF_BIRTH_STR = "1983-03-03";
		DATE_OF_EMPLOYMENT_STR = "2018-10-09";
		STATUS_STR = "1";
		
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		DATE_OF_BIRTH = sdf.parse(DATE_OF_BIRTH_STR);
		DATE_OF_EMPLOYMENT = sdf.parse(DATE_OF_EMPLOYMENT_STR);
		
		ID = new Integer(ID_STR);
		STATUS = new Integer(STATUS_STR);
		
		EMPLOYEE = new Employee(ID,FIRST_NAME,MIDDLE_INITIAL,LAST_NAME,DATE_OF_BIRTH,DATE_OF_EMPLOYMENT,STATUS);
		EMPLOYEE_DISPLAY = new EmployeeDisplay(EMPLOYEE);
		NEW_EMPLOYEE = new Employee(new Integer(2),"Peter",MIDDLE_INITIAL,LAST_NAME,DATE_OF_BIRTH,DATE_OF_EMPLOYMENT,STATUS);
		NEW_EMPLOYEE_DISPLAY = new EmployeeDisplay(NEW_EMPLOYEE);
		EMPLOYEE_LIST = new ArrayList<EmployeeDisplay>();
		EMPLOYEE_LIST.add(EMPLOYEE_DISPLAY);
		EMPLOYEE_LIST.add(NEW_EMPLOYEE_DISPLAY);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetAllEmployees() throws EmployeeServicesException {

		//Configure Test Data -->
		//<-- Configure Test Data
		
		EasyMock.expect(serviceDaoMock.getAllActiveEmployees()).andReturn(EMPLOYEE_LIST).once();

		EasyMock.replay(serviceDaoMock);
		
		List<EmployeeDisplay> result = service.retrieveActiveEmployees();
		LOG.debug("Result = [{}]", result);
		
		EasyMock.verify(serviceDaoMock);
		
		assertNotNull(result);
		assertEquals(2, result.size());
		
	}

	@Test
	public void testGetEmployeeById() throws EmployeeServicesException, DataAccessException, EmployeeServicesInvalidDataException {
		
		//Configure Test Data -->
		//<-- Configure Test Data
		
		EasyMock.expect(serviceDaoMock.getEmployeeById(ID)).andReturn(EMPLOYEE).once();

		EasyMock.replay(serviceDaoMock);
		
		Employee result = service.retrieveEmployeeById(ID);
		LOG.debug("Result = [{}]", result);
		
		EasyMock.verify(serviceDaoMock);
		
		assertNotNull(result);
		assertEquals(LAST_NAME, result.getLastName());
		
	}

}
