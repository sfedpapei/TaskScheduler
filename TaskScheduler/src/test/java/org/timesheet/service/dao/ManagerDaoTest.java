package org.timesheet.service.dao;

import static org.junit.Assert.*;

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
import org.timesheet.service.GenericDao;

@ContextConfiguration(locations = { "/persistence-beans.xml" })
public class ManagerDaoTest extends DomainAwareBase {

	@Autowired
	private ManagerDao managerDao;
	
	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private TaskDao taskDao;

	// add a manager check the size
	@Test
	public void testAdd() {
		int size = managerDao.list().size();
		Manager manager = new Manager("test-manager");

		managerDao.add(manager);

		assertEquals(1, managerDao.list().size());
	}

	// add a manager update the name
	@Test
	public void testUpdate() {
		Manager manager = new Manager("test-manager");
		managerDao.add(manager);
		manager.setName("updated");
		managerDao.update(manager);

		Manager found = managerDao.find(manager.getId());
		assertEquals("updated", found.getName());
	}

	// add a manager check if found
	@Test
	public void find() {
		Manager manager = new Manager("test-manager");
		managerDao.add(manager);

		Manager found = managerDao.find(manager.getId());
		assertEquals(manager, found);
	}

	@Test
	public void testList() {
		assertEquals(0, managerDao.list().size());
		// add employees to list
		List<Manager> managers = Arrays.asList(new Manager("test-1"),
				new Manager("test-2"), new Manager("test-3"));
		for (Manager manager : managers) {
			managerDao.add(manager);
		}

		List<Manager> found = managerDao.list();
		assertEquals(3, found.size());

		for (Manager manager : found) {
			assertTrue(managers.contains(manager));
		}

	}

	// remove
	@Test
	public void testRemove() {
		Manager manager = new Manager("test-manager");
		managerDao.add(manager);

		// successfully added
		assertEquals(manager, managerDao.find(manager.getId()));

		//trying to remove
		managerDao.remove(manager);
		assertNull(managerDao.find(manager.getId()));
	}
	
	@Test
	public void testRemoveManager() {
		Employee employee = new Employee("test-remove-employee", "IT");
		employeeDao.add(employee);

		Manager manager = new Manager("test-manager");
		managerDao.add(manager);

		Task task = new Task("test-task", manager, employee);
		taskDao.add(task);

		// should not work, manager is on task and timesheet
		assertFalse(managerDao.removeManager(manager));
		
		//remove task
		taskDao.remove(task);

		//should work no more tasks for the manager
		assertTrue(managerDao.removeManager(manager));

	}

}
