/**
 * File: EMFFactory.java
 * Date: 18 בספט 2014
 * Author: ODEDNI
 */
package menora.ccm.utils;

import java.util.HashMap;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Oded Nissan
 * Support caching multiple EMF.
 *
 */
public class EntityManagerFactoryCache {
	private static HashMap<String,EntityManagerFactory> map = new HashMap<String,EntityManagerFactory>();

	/**
	 * get EntityManagerFactory by name from the map
	 * @param name Persistence unit name
	 * @return EntityManagerFactory
	 */
	public static EntityManagerFactory getEntityManagerFactory(String name) {
		EntityManagerFactory factory = map.get(name);
		if(null == factory) {
			factory = Persistence.createEntityManagerFactory(name);
			map.put(name, factory);
		}
		
		return(factory);
	}
	
	
}
