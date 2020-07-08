/**
 * 
 */
package astorno.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import astorno.domain.Customer;
import astorno.service.CustomerService;

/**
 * @author oded
 *
 */
@Controller
@RequestMapping(value="/customer")
public class CustomerController {

	private static Logger log = Logger.getLogger(CustomerController.class);
	@Autowired
	CustomerService service;
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String addCustomer(@Valid Customer cust, BindingResult result, Model model)
	{
		log.debug("in CustomerController.add()");
		log.debug(ToStringBuilder.reflectionToString(cust));
		if(result.hasErrors()) {
			return("form");
		}
		model.addAttribute("msg","Customer saved");
		service.createCustomer(cust);
		return("form");
		
	}
	
	@RequestMapping(value="/customers", method=RequestMethod.GET)
	public String getAll(Model model)
	{	
		log.debug("in CustomerController.getAll()");
		List<Customer> list = service.findAll();
		model.addAttribute("customers",list);
		return("table");
	}
}
