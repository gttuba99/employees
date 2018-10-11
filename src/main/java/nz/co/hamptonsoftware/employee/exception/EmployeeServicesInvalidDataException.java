package nz.co.hamptonsoftware.employee.exception;

public class EmployeeServicesInvalidDataException extends Exception {
	
	private static final long serialVersionUID = -1316334630850591370L;

	public EmployeeServicesInvalidDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmployeeServicesInvalidDataException(String message) {
		super(message);
	}

	public EmployeeServicesInvalidDataException(Throwable cause) {
		super(cause);
	}

}
