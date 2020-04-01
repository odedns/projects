/**
 * File: FileServlet.java
 * Date: 30 баеч 2014
 * Author: ODEDNI
 */
package menora.ccm.servlet;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import menora.ccm.utils.PropertyReader;
import org.apache.log4j.Logger;

/**
 * @author Oded Nissan
 * This servlet is used to serve the pdf files from
 * a file system folder.
 */
public class FileServlet extends HttpServlet {

	private String filePath;
	private static Logger log = Logger.getLogger(FileServlet.class); 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String fileName = req.getPathInfo();
		log.info("in FileServlet fileName=  " + fileName);
		if(null == fileName) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		String fullPath = filePath + fileName;
		log.debug("fullpath = " + fullPath);
		File file = new File(URLDecoder.decode(fullPath,"UTF-8"));
		if(!file.exists()) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		String contentType = getServletContext().getMimeType(fileName);
		log.debug("content type = " + contentType);
		if(null == contentType) {
			
			contentType = "application/octet-Stream ";
		}
		long length = file.length();
		log.debug("file length = " + length);
		resp.setContentType(contentType);
		resp.setContentLength((int) length);
		BufferedOutputStream output = new BufferedOutputStream(resp.getOutputStream());
		Path p = Paths.get(file.getPath());
		Files.copy(p, output);
		output.close();
		log.debug("FileServlet done ...");
	
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		log.debug("in FileServlet init");
		try {
			Properties p = PropertyReader.read("taxreport.properties");
			this.filePath = p.getProperty("taxreport.rootDir") + "/pdf";
			log.debug("filePath = " + filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

}
