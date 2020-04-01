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

@WebFilter("/*")
public class ResponseFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Do nothing
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String requestURI = request.getRequestURI();

		if (requestURI.contains(".nocache.")) {
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
			response.setHeader("Pragma", "no-cache"); // HTTP 1.0
			response.setDateHeader("Expires", 0); // Proxies
		} else if (requestURI.contains(".cache.")) {
			response.setHeader("Cache-Control", "max-age=" + 24 * 60 * 60 * 365 * 10 + ", private"); // In seconds
		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// Do nothing
	}
}
