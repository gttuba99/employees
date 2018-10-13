package nz.co.hamptonsoftware.employee.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import nz.co.hamptonsoftware.employee.config.ControllerTestAppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ControllerTestAppConfig.class}, loader=AnnotationConfigWebContextLoader.class)
public class EmployeeControllerTests extends EasyMockSupport {
	//TODO: add additional pass and fail tests for remaining methods 

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		this.mockMvc = builder.build();
	}
		
	@Test
	@WithMockUser
	public final void testRetrieveEmployee_SUCCESS() throws Exception {

		String EXPECTED_JSON = "{`id`: `3`,`firstName`: `Paul`,`middleInitial`: `P`,`lastName`: `Peterson`,`dateOfBirth`: `1983-03-03`,`dateOfEmployment`:`2018-10-09`,`status`: `ACTIVE`}"
				.replace('`', '"');
		
		mockMvc.perform(get("/EmployeeService/api/v1/employee/3")
				.contextPath("/EmployeeService")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(EXPECTED_JSON));

	}
	
	@Test
	@WithMockUser
	public final void testRetrieveEmployee_NOT_FOUND() throws Exception {

		String EXPECTED_JSON = "{`summaryMessage`:`No employee found with id [99]`,`detailMessage`:null}".replace('`', '"');
		
		mockMvc.perform(get("/EmployeeService/api/v1/employee/99")
				.contextPath("/EmployeeService")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8)).andDo(print())
													    .andExpect(status().isNotFound())
													    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
													    .andExpect(content().json(EXPECTED_JSON));

	}
	
}
