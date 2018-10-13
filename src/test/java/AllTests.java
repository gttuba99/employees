import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import nz.co.hamptonsoftware.employee.api.EmployeeControllerTests;
import nz.co.hamptonsoftware.employee.data.EmployeeServicesDaoTests;
import nz.co.hamptonsoftware.employee.service.EmployeeServiceTests;

@RunWith(Suite.class)
@SuiteClasses({EmployeeServicesDaoTests.class,
			   EmployeeControllerTests.class,
			   EmployeeServiceTests.class
			   })
public class AllTests {

}
