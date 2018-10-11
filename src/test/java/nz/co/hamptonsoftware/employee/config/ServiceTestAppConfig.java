package nz.co.hamptonsoftware.employee.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
//@ComponentScan(basePackages = "nz.co.hamptonsoftware.employees")
public class ServiceTestAppConfig {

	static final Logger LOG = LoggerFactory.getLogger(ServiceTestAppConfig.class);



}
