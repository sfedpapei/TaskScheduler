package org.timesheet.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.timesheet.domain.Employee;
import org.timesheet.domain.Manager;
import org.timesheet.domain.Task;
import org.timesheet.service.TimesheetService;
import org.timesheet.service.dao.TaskDao;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class TimesheetServiceImpl implements TimesheetService {

	private SessionFactory sessionFactory;

	@Autowired
	private TaskDao taskDao;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session currentSession() {
		return sessionFactory.getCurrentSession();

	}

	@Override
	public Task busiestTask() {
		List<Task> tasks = taskDao.list();
		if (tasks.isEmpty()) {
			return null;
		}

		Task busiestTask = tasks.get(0);

		for (Task task : tasks) {
			if (task.getAssignedEmployees().size() > busiestTask
					.getAssignedEmployees().size()) {
				busiestTask = task;
			}
		}
		return busiestTask;
	}

	@Override
	public List<Task> tasksForEmployee(Employee e) {
		List<Task> allTasks = taskDao.list();
		List<Task> tasksForEmployee = new ArrayList<Task>();

		for (Task task : allTasks) {
			if (task.getAssignedEmployees().contains(e)) {
				tasksForEmployee.add(task);
			}
		}
		return tasksForEmployee;
	}

	@Override
	public List<Task> tasksForManager(Manager m) {
		List<Task> allTasks = taskDao.list();
		List<Task> tasksForManager = new ArrayList<Task>();

		for (Task task : allTasks) {
			if (task.getManager().equals(m)) {
				tasksForManager.add(task);
			}
		}
		return tasksForManager;

	}

}
