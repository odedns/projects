/**
 * File: DumpMap.java
 * Date: 6 баеч 2014
 * Author: ODEDNI
 */
package menora.ccm.taxreport.tests;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import menora.ccm.utils.PropertyReader;

import org.mapdb.DB;
import org.mapdb.DBMaker;

/**
 * @author Oded Nissan
 *
 */
public class DumpMap {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Properties props = null;
		try {
			props = PropertyReader.read("taxreport.properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String dbFileName = props.getProperty("taxreport.mapdb.file");
		String mapName = props.getProperty("taxreport.mapdb.map");
		File f = new File(dbFileName);
		DB db = DBMaker.newFileDB(f).make();
		Map<Integer,Integer>map  = db.getTreeMap(mapName);
		Set <Integer>keys = map.keySet();
		System.out.println("num keys in map: " + keys.size());
		for(Integer n : keys) {
			System.out.println("key =" + n );
		}
		db.close();
	}

}
