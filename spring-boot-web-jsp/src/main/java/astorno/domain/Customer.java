/**
 * 
 */
package astorno.domain;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;

/**
 * @author oded
 *
 */
public class Customer implements Serializable {
	private int id;
	@NotEmpty
	private String firstName;
	@NotEmpty
	private String lastName;
	private int status;
	private String phone;
	@NotEmpty(message = "Please enter your email addresss.")
	private String email;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	

}
