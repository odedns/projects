package il.co.menora.soaarchive.client.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import com.google.gwt.place.shared.Prefix;

import il.co.menora.soaarchive.shared.IncomingResponse;

@Path("/rest/soaarchive")
public interface SoaArchiveClientService extends RestService {
	
	@GET
	public void findAll(MethodCallback<IncomingResponse> callback);
	
	
	@GET
	@Path("/payload/{id}")
	public void getPayload(@PathParam("id") long id,MethodCallback<String> callback);

}
