package nz.co.hamptonsoftware.employee.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Employee {
/**
	* ID - Unique identifier for an employee
	* FirstName - Employees first name
	* MiddleInitial - Employees middle initial
	* LastName - Employees last name
	* DateOfBirth - Employee birthday and year
	* DateOfEmployment - Employee start date
	* Status - ACTIVE or INACTIVE
 */
	public static final int STATUS_INACTIVE = 0;
	public static final int STATUS_ACTIVE = 1;
	
	private int id;
	private String firstName;
	private String middleInitial;
	private String lastName;
	private Date dateOfBirth;
	private Date dateOfEmployment;
	private int status;
	
	public Employee() {}
	
	public Employee(Integer id, String firstName, String middleInitial, String lastName, Date dateOfBirth, Date dateOfEmployment, Integer status) {
		this.id = id.intValue();
		this.firstName = firstName;
		this.middleInitial = middleInitial;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.dateOfEmployment = dateOfEmployment;
		this.status = status.intValue();
	}
	
	public int getId() {
		return id;
	}
	public Employee setId(int id) {
		this.id = id;
		return this;
	}
	public String getFirstName() {
		return firstName;
	}
	public Employee setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}
	public String getMiddleInitial() {
		return middleInitial;
	}
	public Employee setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
		return this;
	}
	public String getLastName() {
		return lastName;
	}
	public Employee setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public Employee setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		return this;
	}
	public Date getDateOfEmployment() {
		return dateOfEmployment;
	}
	public Employee setDateOfEmployment(Date dateOfEmployment) {
		this.dateOfEmployment = dateOfEmployment;
		return this;
	}
	public int getStatus() {
		return status;
	}
	public Employee setStatus(int status) {
		this.status = status;
		return this;
	}
	
	public String toString() {
		String dateOfBirthStr = "";
		String dateOfEmploymentStr = "";
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		if (dateOfBirth != null) dateOfBirthStr = simpleDateFormat.format(dateOfBirth);
		if (dateOfEmployment != null) dateOfEmploymentStr = simpleDateFormat.format(dateOfEmployment);
		return "[Employee id=" + id + ", firstName=" + firstName + ", middleInitial=" + middleInitial + ", lastName=" + lastName +
				", dateOfBirth=" + dateOfBirthStr + ", dateOfEmployment=" + dateOfEmploymentStr + "]";
	}
}
