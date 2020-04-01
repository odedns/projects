package il.co.menora.soaarchive.client.rpc;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.InvocationException;

public abstract class AsyncCallbackDecorator<T> implements AsyncCallback<T> {
	@Override
	public void onFailure(Throwable caught) {
		// When the session expires, service calls fail with InvocationException and the message is the login.jsp file content
		if (caught instanceof InvocationException && caught.getMessage().contains("<!DOCTYPE html>")) {
			Window.Location.reload();
			return;
		}
		Window.alert("A server error has occured. Please try again.");
	}
}
