package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Servlet Filter implementation class UserFilter
 */
@WebFilter(filterName="DriverFilter",urlPatterns = {"/driver/*"})
public class DriverFilter implements Filter {

    /**
     * Default constructor.
     */
    public DriverFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		HttpSession session =  ((HttpServletRequest)request).getSession();
		String user= (String) session.getAttribute("driver");
		if(user == null) {
			
			if (httpRequest.getRequestURI().endsWith(".json")){
				request.setAttribute("info", "你还没有登录1！");
			}
			else{
				request.setAttribute("info", "你还没有登录！");
				((HttpServletRequest)request).getRequestDispatcher("/drlogin.jsp").forward(request, response);
			}
	}else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
