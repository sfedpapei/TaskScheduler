package org.timesheet.web.exceptions;

import org.timesheet.domain.Employee;

public class EmployeeDeleteException extends Exception {

	private Employee employee;

	public EmployeeDeleteException(Employee employee) {
		this.employee = employee;
	}

	public Employee getEmployee() {
		return employee;
	}

}
