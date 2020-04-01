/**
 * File: FtpActor.java
 * Date: 9 бреб 2014
 * Author: ODEDNI
 */
package menora.ccm.taxreport.actors;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import menora.ccm.utils.CcmTrace;

import org.apache.log4j.Logger;

import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * @author Oded Nissan
 *
 */
public class FtpActor extends UntypedActor {

	private Properties props;
	private static Logger log = Logger.getLogger(FtpActor.class);
	private CcmTrace tracer;
	private long executionNumber;
	
	/**
	 * constructor
	 * @param props Properties object.
	 */
	public FtpActor(Properties props)
	{
		this.props = props;
		String persistenceUnit = (String) props.get("taxreport.persistenceUnit");
		this.tracer = new CcmTrace(persistenceUnit);
		this.executionNumber = (long) props.get("taxreport.executionNumber");


	}
	/* (non-Javadoc)
	 * @see akka.actor.UntypedActor#onReceive(java.lang.Object)
	 */
	@Override
	public void onReceive(Object obj) throws Exception {
		// TODO Auto-generated method stub
		if(obj instanceof ReportMessage) {
			ReportMessage msg = (ReportMessage) obj;
			if(msg.getType() == ReportMessage.TYPE_FTP_START) {
				log.debug("got FTP_START message");
				handleFtpMessage();
				getSender().tell(new ReportMessage(ReportMessage.TYPE_FTP_DONE), getSelf() );
			} else {
				log.error("invalid message type: " + msg.getType());
			}
		} else {
			unhandled(obj);
		}

	}
	
	public static Props mkProps(Properties props)
	{
		return(Props.create(FtpActor.class, props));
	}
	
	/**
	 * handle the ftp start message.
	 * ftp the xml files.
	 */
	private void handleFtpMessage()
	{
		log.debug("in handleFtpMessage");
		String path = props.getProperty("taxreport.outputDir");
		Path dirPath =  Paths.get(path);
		String fileName = null;
		try {
			DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath);
			for(Path p: stream) {
				log.info("ftp file: " + p.getFileName());
				this.executionNumber = (long) props.get("taxreport.executionNumber");
				fileName = p.getFileName().toString();

				tracer.trace(this.executionNumber, (long)0, (long)0, CcmTrace.TRACE_STATUS_OK,CcmTrace.TRACE_PDF,fileName,null);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e);
			tracer.trace(this.executionNumber, (long)0, (long)0, CcmTrace.TRACE_STATUS_ERROR,CcmTrace.TRACE_PDF,fileName,e.getMessage());			
		}
	}

}
