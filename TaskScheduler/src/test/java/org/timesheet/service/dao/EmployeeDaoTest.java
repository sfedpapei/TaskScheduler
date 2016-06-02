package org.timesheet.service.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.timesheet.DomainAwareBase;
import org.timesheet.domain.Employee;
import org.timesheet.domain.Manager;
import org.timesheet.domain.Task;
import org.timesheet.domain.Timesheet;
import org.timesheet.service.dao.EmployeeDao;

@ContextConfiguration(locations = { "/persistence-beans.xml" })
public class EmployeeDaoTest extends DomainAwareBase {

	@Autowired
	// get rid of setter and getters through Autowire annotation
	private EmployeeDao employeeDao;

	@Autowired
	private ManagerDao managerDao;

	@Autowired
	private TaskDao taskDao;

	@Autowired
	private TimesheetDao timesheetDao;

	// add an employee, check the size of the lists
	@Test
	public void testAdd() {
		int size = employeeDao.list().size();
		Employee employee = new Employee("Bob", "IT");
		employeeDao.add(employee);

		// list should have one more employee now
		assertTrue(size < employeeDao.list().size());
		assertEquals(1, employeeDao.list().size());
	}

	// add an employee update the name
	@Test
	public void testUpdate() {
		Employee employee = new Employee("test-employee", "IT");
		employeeDao.add(employee);
		employee.setName("updated");
		employeeDao.update(employee);

		Employee found = employeeDao.find(employee.getId());
		assertEquals("updated", found.getName());
	}

	// add an employee check if found
	@Test
	public void find() {
		Employee employee = new Employee("test-employee", "IT");
		employeeDao.add(employee);

		Employee found = employeeDao.find(employee.getId());
		assertEquals(employee, found);
	}

	@Test
	public void testList() {
		assertEquals(0, employeeDao.list().size());
		// add employees to list
		List<Employee> employees = Arrays.asList(new Employee("test-1",
				"testers"), new Employee("test-2", "testers"), new Employee(
				"test-3", "testers"));
		for (Employee employee : employees) {
			employeeDao.add(employee);
		}

		List<Employee> found = employeeDao.list();
		assertEquals(3, found.size());

		for (Employee employee : found) {
			assertTrue(employees.contains(employee));
		}

	}

	// remove
	@Test
	public void testRemove() {
		Employee e = new Employee("test-employee", "IT");
		employeeDao.add(e);

		// successfully added
		assertEquals(e, employeeDao.find(e.getId()));

		employeeDao.remove(e);
		assertNull(employeeDao.find(e.getId()));
	}

	@Test
	public void testRemoveEmployee() {
		Employee employee = new Employee("test-remove-employee", "IT");
		employeeDao.add(employee);

		Manager manager = new Manager("test-manager");
		managerDao.add(manager);

		Task task = new Task("test-task", manager, employee);
		taskDao.add(task);

		Timesheet timesheet = new Timesheet(employee, task, 100);
		timesheetDao.add(timesheet);

		// should not work, employee is on task and timesheet
		assertFalse(employeeDao.removeEmployee(employee));

		timesheetDao.remove(timesheet);
		taskDao.remove(task);

		assertTrue(employeeDao.removeEmployee(employee));

	}
}
