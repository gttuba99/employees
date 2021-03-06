package nz.co.hamptonsoftware.employee.config;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import nz.co.hamptonsoftware.employee.api.EmployeeController;
import nz.co.hamptonsoftware.employee.data.EmployeeJdbcDaoImpl;
import nz.co.hamptonsoftware.employee.service.EmployeeServiceImpl;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "nz.co.hamptonsoftware.employee")
public class ControllerTestAppConfig {

	static final Logger LOG = LoggerFactory.getLogger(ControllerTestAppConfig.class);

	@Bean(name = "dataSource")
	public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/employees");
        dataSource.setUsername("guest_user");
        dataSource.setPassword("guest_password");
		LOG.info("Found datasource [{}]", dataSource);
		return dataSource;
//	    return new EmbeddedDatabaseBuilder()
//	            .setType(EmbeddedDatabaseType.HSQL)
//	            .addScript("classpath:jdbc/schema.sql")
//	            .addScript("classpath:jdbc/test-data.sql").build();
	}

	@Bean(name = "txManager")
	@Inject
	public DataSourceTransactionManager getTxManager(DataSource ds) {
		final DataSourceTransactionManager dsTM = new DataSourceTransactionManager(ds);
		LOG.info("Created transaction manager [{}]", dsTM);
		return dsTM;
	}
	
	@Bean
	public EmployeeController employeeController() {
		return new EmployeeController(new EmployeeServiceImpl(new EmployeeJdbcDaoImpl(dataSource())));
	}
}
