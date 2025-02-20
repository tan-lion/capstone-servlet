package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "authenFilter", urlPatterns = "/*")
public class AuthencationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//		HttpServletRequest req = (HttpServletRequest) request;
//		HttpServletResponse resp = (HttpServletResponse) response;
//
//		String context = req.getContextPath();
//		String path = req.getServletPath();
//		
//		String roleName = "";
//		Cookie[] cookies = req.getCookies();
//		for(Cookie data : cookies) {
//			if(data.getName().equals("role")) {
//				roleName = data.getValue();
//			}
//		}
//		if (path.equals("/login")) {
//			chain.doFilter(req, resp);
//			return;
//		}
//
//		if (roleName.isEmpty()) {
//			resp.sendRedirect(context + "/login");
//			return;
//		}
//
//		switch (roleName) {
//		case "ROLE_ADMIN":
//			chain.doFilter(req, resp); 
//			break;
//
//		case "ROLE_MANAGER":
//			if (path.equals("/users") || path.equals("/tasks") || path.equals("/jobs")) {
//				chain.doFilter(req, resp); 
//			} else {
//				resp.sendRedirect(context);
//			}
//			break;
//
//		case "ROLE_MEMBER":
//			if (path.equals("/tasks")) {
//				chain.doFilter(req, resp); 
//			} else {
//				resp.sendRedirect(context); 
//			}
//			break;
//
//		default:
//			resp.sendRedirect(context + "/login"); 
//			break;
//		}
	}

}
