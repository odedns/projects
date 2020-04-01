package il.co.menora.soaarchive.ejb;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import il.co.menora.soaarchive.shared.ApplicationInfoDto;

@Stateless
@LocalBean
public class GeneralBean {
	private static final Logger logger = LogManager.getLogger();

	@Resource
	private SessionContext ctx;

	// @PersistenceContext(unitName = "bpmPortalPersistenceUnit")
	// private EntityManager em;

	public ApplicationInfoDto getApplicationInfo() {
		logger.debug("Getting application info");
		String userName = ctx.getCallerPrincipal().getName();
		return new ApplicationInfoDto("1.0.0", userName);
	}
}
