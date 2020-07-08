/**
 * 
 */
package astorno.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import astorno.domain.Customer;

/**
 * @author oded
 *
 */
@Service
public class CustomerService {

	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public CustomerService()
	{
		
	}
	
	/**
	 * 
	 * @param user
	 */
	public void createCustomer(Customer c)
	{
		String query= "INSERT INTO customers(id,firstname,lastname,status,phone,email)	VALUES(?,?,?,?,?,?)";
				
		jdbcTemplate.update(query, c.getId(),c.getFirstName(),c.getLastName(),c.getStatus(),c.getPhone(),c.getEmail());
		
	}
	
	
	
	/**
	 * get cusromer by id
	 * @param id
	 * @return Customer
	 */
	public Customer findById(int id)
	{
		String query = "SELECT * FROM customers where id=?";
		
		Customer c = jdbcTemplate.queryForObject(query, new Object[] {id}, new BeanPropertyRowMapper<Customer>(Customer.class));
		return(c);
	}
	
	/**
	 * find all customers
	 * @return List of customers
	 */
	public List<Customer> findAll()
	{
		String query = "SELECT * FROM customers";
		List<Customer> list = jdbcTemplate.query(query, new BeanPropertyRowMapper<Customer>(Customer.class));
		return(list);
	}
	
}
