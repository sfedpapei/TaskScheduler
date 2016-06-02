package org.timesheet.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.jdbc.SimpleJdbcTestUtils;
import org.timesheet.domain.Task;
import org.timesheet.service.dao.EmployeeDao;
import org.timesheet.service.impl.TimesheetServiceImpl;

@ContextConfiguration(locations= {"/persistence-beans.xml"})
public class TimesheetServiceTest extends AbstractJUnit4SpringContextTests {
	
	private final String createScript = "src/main/resources/sql/create.sql";
	private final String deleteScript = "src/main/resources/sql/cleanup.sql";
	
	@Autowired
	private SimpleJdbcTemplate jdbcTemplate;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	TimesheetService timesheetService;

	@Before
	public void insertData() {
		SimpleJdbcTestUtils.executeSqlScript(jdbcTemplate,
				new FileSystemResource(createScript), false);
	}
	
	@After
	public void cleanup() {
		SimpleJdbcTestUtils.executeSqlScript(jdbcTemplate,
				new FileSystemResource(deleteScript), false);
	}
	
	@Test
	public void testBusiestTask() {
		Task task = timesheetService.busiestTask();
		assertTrue(1 == task.getId());
	}
	
	

}
