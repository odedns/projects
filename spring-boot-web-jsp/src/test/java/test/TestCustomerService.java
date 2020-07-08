/**
 * 
 */
package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import astorno.domain.Customer;
import astorno.service.CustomerService;



/**
 * @author oded
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=astorno.app.SpringBootWebApplication.class)
public class TestCustomerService {
	@Autowired
	CustomerService service;
	
	
	@Test
	public void testFindCustomer()
	{
		int id = 1;
		
		Customer c = service.findById(id);
		System.out.println("c = " + c.getFirstName());
		assertEquals(c.getFirstName(), "Dale");				
	}
	


	
	
}
