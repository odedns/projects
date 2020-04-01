package il.co.menora.soaarchive.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class ClientService {
	private final static SoaArchiveServiceAsync service;

	static {
		service = GWT.create(SoaArchiveService.class);
		((ServiceDefTarget) service).setServiceEntryPoint("/archive/SoaArchiveService");
	}

	public static SoaArchiveServiceAsync get() {
		return service;
	}
}
