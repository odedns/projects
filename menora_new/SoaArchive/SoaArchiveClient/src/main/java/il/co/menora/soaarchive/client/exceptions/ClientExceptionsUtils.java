package il.co.menora.soaarchive.client.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.fusesource.restygwt.client.Method;

import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.Window;

public class ClientExceptionsUtils {
	private static Logger logger = Logger.getLogger(ClientExceptionsUtils.class.getSimpleName());

	public static void onUncaughtException(Throwable e) {
		Throwable unwrappedException = unwrapUmbrellaException(e);
		logger.log(Level.SEVERE, "Exception caught: ", unwrappedException);
	}

	public static Throwable unwrapUmbrellaException(Throwable e) {
		if (e instanceof UmbrellaException) {
			UmbrellaException ue = (UmbrellaException) e;
			if (ue.getCauses().size() == 1) {
				return unwrapUmbrellaException(ue.getCauses().iterator().next());
			}
		}
		return e;
	}
	
	public static void handleSessionExpiration(Method method)
	{
		int statusCode = method.getResponse().getStatusCode();
		if(statusCode == 401) {
			Window.Location.replace("/archive/login.jsp");
		}
	}
}
