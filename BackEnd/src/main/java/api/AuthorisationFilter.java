package api;

import Util.JWT;
import conf.Configuration;
import model.User;

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
                //String queryString = ((HttpServletRequest) request).getQueryString();
                String method = ((HttpServletRequest) request).getMethod();
                try {
                    User u = JWT.getUserFromJWT(((HttpServletRequest) request).getHeader("jwt"));
                    isAuthorized = determineIfAllowed(url, method, u);
                } catch (Exception e) {
                    e.printStackTrace();
                    isAuthorized = false;
                }
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

    private boolean determineIfAllowed(String url, String method, User user){
        // example: http://localhost:8765/CoursesApp/api/courses/6
        // 0 -> http: , 1 -> , 2 -> localhost:8765, 3-> CoursesApp, 4-> api, 5-> courses, 6 -> 5
        String[] segments = url.split("/");
        if (segments.length < 6) return true;
        boolean allowed = true;
        try {
            switch (segments[5]) {
                case "courses":
                    if (segments.length == 7){   // courses/{courseId}
                        switch (method) {
                            case "POST":         // only students can post grades for courses
                                // Note: if user is null then backend will give back 200 OK but with an error message
                                allowed = user == null || !user.isAdmin();
                                break;
                            case "PUT":          // only an admin should be able to change a course
                                allowed = user != null && user.isAdmin();
                                break;
                            case "DELETE":       // only an admin should be able to delete a course
                                allowed = user != null && user.isAdmin();
                                break;
                        }
                    } else {                     // courses
                        switch (method) {
                            case "POST":         // only an admin should be able to post a new course
                                allowed = user != null && user.isAdmin();
                                break;
                        }
                    }
                    break;
                case "students":
                    if (segments.length == 7){   // students/{studentId}
                        int sid = Integer.parseInt(segments[6]);
                        switch (method) {
                            case "GET":          // only student himself or admin can get his info
                                allowed = user != null && (user.isAdmin() || (sid == user.getId()));
                                break;
                            case "PUT":          // only student himself can change his info
                                allowed = user != null && (sid == user.getId());
                                break;
                            case "DELETE":       // only student himself or admin can delete the user
                                allowed = user != null && (user.isAdmin() || (sid == user.getId()));
                                break;
                        }
                    } else {                     // courses
                        switch(method) {
                            case "GET":          // only an admin should be able to get the information of all students
                                allowed = user != null && user.isAdmin();
                                break;
                        }
                    }
                    break;
                case "users":
                    if (segments.length == 7) {
                        switch (method) {
                            case "PUT":
                                // Only user himself should be able to change his password
                                int uid = Integer.parseInt(segments[6]);
                                allowed = user != null && (uid == user.getId());
                                break;
                        }
                    }
                    break;
                case "login":
                    break;
                default:
                    break;
            }
        } catch (NumberFormatException e){
            allowed = true;   // Note: allow -> it will probably cause a 404 Error
        }
        return allowed;
    }

}
