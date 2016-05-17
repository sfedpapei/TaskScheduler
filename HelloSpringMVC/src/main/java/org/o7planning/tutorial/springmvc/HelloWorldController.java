package org.o7planning.tutorial.springmvc;

import java.util.ArrayList;
import java.util.List;
//import java.util.Map;

import org.baeldung.tutorial.springmvcform.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {
	
	@Autowired //to get rid of properties
	EmployeeValidator validator;
	
	@RequestMapping("/hello")
	public String hello(Model model) {
		model.addAttribute("greeting", "Hello Spring MVC");
		return "helloworld";
	}
	
	@RequestMapping(value = "/redirect", method = RequestMethod.GET)
	public String redirect(Model model) {
		
		return "redirect:/hello";
	}
	
	@RequestMapping("/user")
	public String userInfo(Model model,
						@RequestParam(value ="name") String name){
		
		model.addAttribute("name", name);
		return "userInfo";
		
	}
	
	/*Employee class controller*/
	
	/*ModelAndView*/
	/*@RequestMapping(value = "/employee", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("employeeHome", "employee", new Employee());
    }*/
	
	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public String showForm(Model model) {
		
		initModelList(model);
		model.addAttribute("employee", new Employee());		
		return "employeeHome";		
	}
	
	@RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public String submit( @ModelAttribute("employee")Employee employee, 
      BindingResult result, ModelMap modelMap, Model model) {
		
		validator.validate(employee, result);
		
        if (result.hasErrors()) {
        	initModelList(model);
            return "employeeHome";
        }
        modelMap.addAttribute("employee", employee);
        return "employeeView";
    }
	
	private void initModelList(Model model) {
		List<String> professionList = new ArrayList<String>();
        professionList.add("Developer");
        professionList.add("Designer");
        professionList.add("IT Manager");
        model.addAttribute("professionList", professionList);		
	}

}
