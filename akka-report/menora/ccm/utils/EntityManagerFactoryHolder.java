/**
 * File: EntityManagerFactoryHolder.java
 * Date: Aug 5, 2014
 * Author: Oded Nissan
 */
package menora.ccm.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Oded Nissan
 *
 */
public class EntityManagerFactoryHolder {
	
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("TAX");
	
	
	public static EntityManagerFactory getEntityManagerFactory()
	{
		return(factory);
	}
	

}
