package il.co.menora.soaarchive.web.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import il.co.menora.soaarchive.ejb.IncomingDao;
import il.co.menora.soaarchive.shared.IncomingDto;
import il.co.menora.soaarchive.shared.IncomingResponse;
import il.co.menora.soaarchive.shared.MQDto;
import il.co.menora.soaarchive.shared.MQResponse;
import il.co.menora.soaarchive.shared.OutgoingResponse;
import il.co.menora.soaarchive.shared.SearchDto;
import il.co.menora.soaarchive.shared.StatusDto;

@Path("/rest/soaarchive")
public class SoaArchiveService {
	
	private static final Logger log = LogManager.getLogger(SoaArchiveService.class);
	@EJB
	IncomingDao dao;
	
	@GET
	@Path("/incoming/{id}")
	@Produces("application/json")
	public IncomingDto findById(@PathParam("id") String id)
	{
		IncomingDto dto = dao.findById(id);
		return(dto);
	}
	
	@GET
	@Produces("application/json")
	@Path("/incoming")
	public IncomingResponse findAll()
	{
		IncomingResponse resp = dao.findAll();
		return(resp);
	}
	
	@GET
	@Path("/outgoing")
	@Produces("application/json")
	public OutgoingResponse findAllOutgoing()
	{
		OutgoingResponse resp = dao.findAllOutgoing();
		return(resp);
	}
	
	@GET
	@Path("/payload/{id}")
	@Produces("text/plain")
	public String getPayload(@PathParam("id") String id)
	{
		String payload = dao.getPayload(id);
		return(payload);
	}
	
	@GET
	@Path("/error/{id}")
	@Produces("text/plain")
	public String getErrorMessage(@PathParam("id") String id)
	{
		String msg = dao.getErrorMessage(id);
		return(msg);
	}
	
	
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/incoming")
	public IncomingResponse findIncoming(SearchDto dto)
	{
		IncomingResponse resp = dao.findIncoming(dto);
		return(resp);
	}
	
	@POST
	@Path("/mq")
	@Produces("application/json")
	@Consumes("application/json")
	public MQResponse sendToMQ(MQDto dto) 
	{
		log.debug("in sendToMQ dto = " + ToStringBuilder.reflectionToString(dto));
		MQResponse resp = dao.sendToMq(dto);
		return(resp);
	}
	
	@POST
	@Path("/status")
	@Consumes("application/json")
	public int updateStatus(StatusDto dto)
	{
		log.debug("updateStatus dto : " + dto.getStatus());
		int count = 0;
		SearchDto searchDto = dto.getSearchDto();
		if(searchDto != null) {
			count = dao.updateStatusAll(searchDto, dto.getStatus());
		} else {
			count = dao.updateStatus(dto);
		}
		return(count);
	}
}

