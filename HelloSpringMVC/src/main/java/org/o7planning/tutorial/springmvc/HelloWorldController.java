package org.o7planning.tutorial.springmvc;

import org.baeldung.tutorial.springmvcform.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {
	
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
		//model.addAttribute("email", email)
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
		
		model.addAttribute("employee", new Employee());
		return "employeeHome";
		
	}
	
	@RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public String submit( @ModelAttribute("employee")Employee employee, 
      BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "error";
        }
        model.addAttribute("name", employee.getName());
        //model.addAttribute("contactNumber", employee.getContactNumber());
        //model.addAttribute("id", employee.getId());
        return "employeeView";
    }

}
