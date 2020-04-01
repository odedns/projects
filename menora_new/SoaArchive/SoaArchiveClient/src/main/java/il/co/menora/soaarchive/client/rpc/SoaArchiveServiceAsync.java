package il.co.menora.soaarchive.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

import il.co.menora.soaarchive.shared.ApplicationInfoDto;

public interface SoaArchiveServiceAsync {
	void getApplicationInfo(AsyncCallback<ApplicationInfoDto> callback);
}
