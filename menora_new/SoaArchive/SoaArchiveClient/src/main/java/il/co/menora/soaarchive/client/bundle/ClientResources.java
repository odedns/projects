package il.co.menora.soaarchive.client.bundle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

import il.co.menora.soaarchive.client.bundle.style.Style;

public interface ClientResources extends ClientBundle {
	public static final ClientResources INSTANCE = GWT.create(ClientResources.class);

	@Source("style/style.gss")
	Style style();

	@Source("menoraLogo.png")
    ImageResource menoraLogo();

}
