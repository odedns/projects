package il.co.menora.soaarchive.web.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import il.co.menora.soaarchive.ejb.IncomingDao;
import il.co.menora.soaarchive.entities.Incoming;
import il.co.menora.soaarchive.shared.IncomingDto;
import il.co.menora.soaarchive.shared.IncomingResponse;
import il.co.menora.soaarchive.shared.OutgoingResponse;
import il.co.menora.soaarchive.shared.SearchDto;

@Path("/rest/soaarchive")
public class SoaArchiveService {
	@EJB
	IncomingDao dao;
	
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public IncomingDto findById(@PathParam("id") long id)
	{
		IncomingDto dto = dao.findById(id);
		return(dto);
	}
	
	@GET
	@Produces("application/json")
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
	public String getPayload(@PathParam("id") long id)
	{
		String payload = dao.getPayload(id);
		return(payload);
	}
	
	@GET
	@Path("/error/{id}")
	@Produces("text/plain")
	public String getErrorMessage(@PathParam("id") long id)
	{
		String msg = dao.getErrorMessage(id);
		return(msg);
	}
	
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	public IncomingResponse findIncoming(SearchDto dto)
	{
		IncomingResponse resp = dao.findIncoming(dto);
		return(resp);
	}
}

