package astorno.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import astorno.domain.Customer;

@Controller
public class HomeController {

	private static Logger log = Logger.getLogger(HomeController.class);
	// inject via application.properties
	@Value("${welcome.message:test}")
	private String message = "Hello World";

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("message", this.message);
		return "home";
	}
	
	@RequestMapping("/login")
	public String login()
	{
		log.info("in login controller");
		return("loginPage");
	}
	
	@RequestMapping("/form")
	public String form(Model model) {
		model.addAttribute("customer", new Customer());
		List<Integer> statusList = Arrays.asList(1,2,3,4);
		model.addAttribute("statusList", statusList);
		return "form";
	}
	
	@RequestMapping("/test")
	public String test() {
		return("test");
	}
	
}