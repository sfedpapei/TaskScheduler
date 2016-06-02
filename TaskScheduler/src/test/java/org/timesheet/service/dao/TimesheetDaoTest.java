package org.timesheet.service.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

@ContextConfiguration(locations = { "/persistence-beans.xml" })
public class TimesheetDaoTest extends DomainAwareBase {

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private ManagerDao managerDao;

	@Autowired
	private TaskDao taskDao;

	@Autowired
	private TimesheetDao timesheetDao;

	/**
	 * add employee, manager and task
	 */
	private Timesheet newTimesheet() {

		Employee employee = new Employee("test_employee", "IT");
		employeeDao.add(employee);

		Manager manager = new Manager("test-manager");
		managerDao.add(manager);

		Task task = new Task("Spring Task", manager, employee);
		taskDao.add(task);

		return new Timesheet(employee, task, 5);

	}

	private Timesheet newTimesheetFromTemplate(Timesheet template, Integer hours) {
		return new Timesheet(template.getWho(), template.getTask(), hours);

	}

	// add a task check the size
	@Test
	public void testAdd() {
		int size = timesheetDao.list().size();
		Timesheet timesheet = newTimesheet(); // newTimesheet() returns a
												// Timesheet object

		timesheetDao.add(timesheet);

		assertTrue(size < timesheetDao.list().size());
	}

	@Test
	public void testUpdate() {
		Timesheet timesheet = newTimesheet();
		timesheetDao.add(timesheet);

		timesheet.setHours(6);

		// which task should be updated?
		taskDao.update(timesheet.getTask());

		timesheetDao.update(timesheet);

		Timesheet found = timesheetDao.find(timesheet.getId());
		assertTrue(6 == found.getHours());
		// or
		assertEquals(Integer.valueOf(6), found.getHours());
	}

	// add a timesheet check if found
	@Test
	public void testFind() {
		Timesheet timesheet = newTimesheet();
		timesheetDao.add(timesheet);
		// assertTrue(task.equals(taskDao.find(task.getId())));
		assertEquals(timesheet, timesheetDao.find(timesheet.getId()));
	}

	@Test
	public void testList() {
		assertEquals(0, timesheetDao.list().size());
		Timesheet templateTimesheet = newTimesheet();
		// add tasks to list
		List<Timesheet> timesheets = Arrays.asList(
				newTimesheetFromTemplate(templateTimesheet, 4),
				newTimesheetFromTemplate(templateTimesheet, 7),
				newTimesheetFromTemplate(templateTimesheet, 10));
		for (Timesheet timesheet : timesheets) {
			timesheetDao.add(timesheet);
		}

		List<Timesheet> found = timesheetDao.list();
		assertEquals(3, found.size());

		for (Timesheet timesheet : found) {
			assertTrue(timesheets.contains(timesheet));
		}

	}

	// remove
	@Test
	public void testRemove() {
		Timesheet timesheet = newTimesheet();
		timesheetDao.add(timesheet);

		// successfully added
		assertEquals(timesheet, timesheetDao.find(timesheet.getId()));

		// trying to remove
		timesheetDao.remove(timesheet);
		assertNull(taskDao.find(timesheet.getId()));
	}

}
