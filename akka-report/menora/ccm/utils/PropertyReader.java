/*
 * Created on 06/06/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package menora.ccm.utils;
import java.io.*;
import java.util.Properties;

/**
 * @author Oded Nissan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PropertyReader {
	private static final String env = System.getProperty("env.landscape", null);
	
	/**
	 * read properties from a properties file in the classpath.
	 * @param fName the name of the file
	 * @return the Properties object read.
	 * @throws IOException in case of error.
	 */
	public static Properties read(String fName) throws IOException
	{		
		String envFname = fName;
		if(null != env) {
			envFname = env + '_' + fName;
		}
		Properties props = new Properties();
		InputStream is = fName.getClass().getResourceAsStream(envFname);
		if(is == null) {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			is = cl.getResourceAsStream(envFname);
		}
		props.load(is);
		return(props);
	}
	
	
}
