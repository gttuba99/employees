package nz.co.hamptonsoftware.employee.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import nz.co.hamptonsoftware.employee.domain.Employee;
import nz.co.hamptonsoftware.employee.domain.EmployeeDisplay;

public class EmployeeRowMapper implements RowMapper<EmployeeDisplay> {
		public EmployeeDisplay mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Employee emp = new Employee()
				.setId(rs.getInt("id"))
				.setFirstName(rs.getString("firstName"))
				.setMiddleInitial(rs.getString("middleInitial"))
				.setLastName(rs.getString("lastName"))
				.setDateOfBirth(rs.getDate("dateOfBirth"))
				.setDateOfEmployment(rs.getDate("dateOfEmployment"))
				.setStatus(rs.getInt("status"));

            return new EmployeeDisplay(emp);
		}

}
