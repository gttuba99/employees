package nz.co.hamptonsoftware.employee.domain;

import java.text.SimpleDateFormat;

public class EmployeeDisplay {
	private String id;
	private String firstName;
	private String middleInitial;
	private String lastName;
	private String dateOfBirth;
	private String dateOfEmployment;
	private String status;
	
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private String[] statusArray = new String[]{ "INACTIVE", "ACTIVE" };

	public EmployeeDisplay(Employee employee) {
		this.id = Integer.toString(employee.getId());
		this.firstName = employee.getFirstName();
		this.middleInitial = employee.getMiddleInitial();
		this.lastName = employee.getLastName();
		this.dateOfBirth = simpleDateFormat.format(employee.getDateOfBirth());
		this.dateOfEmployment = simpleDateFormat.format(employee.getDateOfEmployment());
		this.status = statusArray[employee.getStatus()];
	}

	public String getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public String getDateOfEmployment() {
		return dateOfEmployment;
	}

	public String getStatus() {
		return status;
	}

}
