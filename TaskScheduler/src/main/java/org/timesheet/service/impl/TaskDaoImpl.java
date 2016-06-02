package org.timesheet.service.impl;

import org.springframework.stereotype.Repository;
import org.timesheet.domain.Task;
import org.timesheet.service.dao.TaskDao;

@Repository("taskDao")
public class TaskDaoImpl extends HibernateDao<Task, Long> implements TaskDao {

	@Override
	public boolean removeTask(Task task) {
		// TODO Auto-generated method stub
		return false;
	}

}
