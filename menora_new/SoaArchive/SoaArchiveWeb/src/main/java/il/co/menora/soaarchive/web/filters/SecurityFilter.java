package il.co.menora.soaarchive.web.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import il.co.menora.soaarchive.web.rest.SoaArchiveService;

@WebFilter("/rest/soaarchive/*")
public class SecurityFilter implements Filter {
	private static final Logger log = LogManager.getLogger(SecurityFilter.class);

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		log.debug("in SecurityFilter.destroy");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.debug("in SecurityFilter.doFilter");
		HttpSession session = ((HttpServletRequest)req).getSession(false);
		log.debug("session = " +session);
		if(session == null) {
			log.debug("Session is invalid - redirect to login page...");
			//session.invalidate();
			//throw new ServletException("session Exception");
	         ((HttpServletResponse)resp).setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		} else {
			chain.doFilter(req, resp);
		}
		


	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		log.debug("in SecurityFilter.init");

	}

}
