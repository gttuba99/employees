package nz.co.hamptonsoftware.employee.exception;

public class EmployeeServicesException extends Exception {
	private static final long serialVersionUID = -8035663770872146378L;

	public EmployeeServicesException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmployeeServicesException(String message) {
		super(message);
	}

	public EmployeeServicesException(Throwable cause) {
		super(cause);
	}

}
