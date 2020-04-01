package il.co.menora.soaarchive.web.rpc;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import il.co.menora.soaarchive.client.rpc.SoaArchiveService;
import il.co.menora.soaarchive.ejb.GeneralBean;
import il.co.menora.soaarchive.shared.ApplicationInfoDto;


@WebServlet(urlPatterns = { "/SoaArchiveService" })
public class SoaArchiveServiceImpl extends RemoteServiceServlet implements SoaArchiveService {
	private static final long serialVersionUID = -781730969733974234L;

	@EJB
	GeneralBean generalBean;

	@Override
	public ApplicationInfoDto getApplicationInfo() {
		return generalBean.getApplicationInfo();
	}
}
