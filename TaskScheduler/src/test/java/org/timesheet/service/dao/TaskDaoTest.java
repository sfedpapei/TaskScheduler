package org.timesheet.service.dao;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.timesheet.DomainAwareBase;
import org.timesheet.domain.Employee;
import org.timesheet.domain.Manager;
import org.timesheet.domain.Task;

@ContextConfiguration(locations = { "/persistence-beans.xml" })
public class TaskDaoTest extends DomainAwareBase {

	@Autowired
	private TaskDao taskDao;

	@Autowired
	private ManagerDao managerDao;

	@Autowired
	private EmployeeDao employeeDao;

	/**
	 * @return Dummy task for testing
	 */
	private Task newSpringTask() {
		Manager manager = new Manager("test-manager");
		managerDao.add(manager);

		List<Employee> employees = Arrays
				.asList(new Employee("test-employee-1", "IT"), new Employee(
						"test-employee-2", "IT"));
		for (Employee employee : employees) {
			employeeDao.add(employee);
		}

		// toArray new Array object
		return new Task("Spring Task", manager,
				employees.toArray(new Employee[employees.size()]));

	}

	/*
	 * Creates a copy of the newSpringTask(). To differentiate each task it adds
	 * a second argument called randomInfo
	 */
	private Task newTaskFromTemplate(Task templateTask, String randomInfo) {
		// gets the description of the task
		String description = templateTask.getDescription() + randomInfo;

		// gets manager name (need it to construct the manager object)
		Manager manager = new Manager(templateTask.getManager().getName());
		managerDao.add(manager);

		List<Employee> templateEmployees = templateTask.getAssignedEmployees();
		Employee[] employees = new Employee[templateEmployees.size()];

		int i = 0;
		for (Employee templateEmployee : templateEmployees) {
			Employee employee = new Employee(templateEmployee.getName()
					+ randomInfo, templateEmployee.getDepartment() + randomInfo);
			employees[i++] = employee;
			employeeDao.add(employee);
		}

		return new Task(description, manager, employees);
	}

	// add a task check the size
	@Test
	public void testAdd() {
		int size = taskDao.list().size();
		Task task = newSpringTask(); // newSpringTask() returns a Task object
		taskDao.add(task);

		assertTrue(size < taskDao.list().size());
	}

	// add a task update the name
	@Test
	public void testUpdate() {
		Task task = newSpringTask();
		taskDao.add(task);
		task.setDescription("Spring Task - Updated");
		taskDao.update(task);

		Task found = taskDao.find(task.getId());
		assertEquals("Spring Task - Updated", found.getDescription());
	}

	// add a task check if found
	@Test
	public void testFind() {
		Task task = newSpringTask();
		taskDao.add(task);
		// assertTrue(task.equals(taskDao.find(task.getId())));
		assertEquals(task, taskDao.find(task.getId()));
	}

	@Test
	public void testList() {
		assertEquals(0, taskDao.list().size());
		Task templateTask = newSpringTask();
		// add tasks to list
		List<Task> tasks = Arrays.asList(
				newTaskFromTemplate(templateTask, "1"),
				newTaskFromTemplate(templateTask, "2"),
				newTaskFromTemplate(templateTask, "3"));
		for (Task task : tasks) {
			taskDao.add(task);
		}

		List<Task> found = taskDao.list();
		assertEquals(3, found.size());

		for (Task task : found) {
			assertTrue(tasks.contains(task));
		}

	}

	// remove
	@Test
	public void testRemove() {
		Task task = newSpringTask();
		taskDao.add(task);

		// successfully added
		assertEquals(task, taskDao.find(task.getId()));

		// trying to remove
		taskDao.remove(task);
		assertNull(taskDao.find(task.getId()));
	}
}
