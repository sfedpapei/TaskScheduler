package org.test;

import org.junit.Test;
import org.o7planning.tutorial.springmvc.HelloWorldController;
import org.springframework.test.web.servlet.MockMvc;

import static
org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static
org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static
org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

public class HomeControllerTest {
	@Test
	public void testHomePage() throws Exception {
		HelloWorldController controller = new HelloWorldController();
		MockMvc mockMvc =
				standaloneSetup(controller).build();
		mockMvc.perform(get("/hello"))
		.andExpect(view().name("helloworld"));
	}

}
