package api;

import conf.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorisationFilter implements Filter {


    @Override
    // init() method is invoked only once. It is used to initialize the filter
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    // doFilter() method is invoked every time when user request to any resource, to which the filter is mapped.
    // It is used to perform filtering tasks
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        /* PRE-FILTERING */
        boolean isAuthorized;
        if (Configuration.CHECK_AUTHORISATION) {
            if (request instanceof HttpServletRequest) {
                String url = ((HttpServletRequest) request).getRequestURL().toString();
                String queryString = ((HttpServletRequest) request).getQueryString();
                String method = ((HttpServletRequest) request).getMethod();
                // System.out.println(url + "?" + queryString);

                // TODO: authorization based on url, queryString and (http) method

                isAuthorized = true;
            } else {
                // should not happen
                System.err.println("\nWarning: FILTER ISSUE\n");
                isAuthorized = false;
            }
        } else {
            isAuthorized = true;
        }

        if (!isAuthorized){
            // if not acceptable
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        chain.doFilter(request, response);  // sends request to next resource
    }

    @Override
    // This is invoked only once when filter is taken out of the service
    public void destroy() {

    }

}
