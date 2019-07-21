package api;

import conf.Configuration;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import org.restlet.service.CorsService;

import java.util.Arrays;
import java.util.HashSet;

public class RestfulApp extends Application {

    public RestfulApp() {
        if (Configuration.ALLOW_CORS) {
            CorsService corsService = new CorsService();
            corsService.setAllowingAllRequestedHeaders(true);
            corsService.setAllowedOrigins(new HashSet(Arrays.asList("*")));
            corsService.setAllowedCredentials(true);
            getServices().add(corsService);
        }
    }

    @Override
    public synchronized Restlet createInboundRoot() {
        Router router = new Router(getContext());

        router.attach("/courses", CoursesResource.class);
        router.attach("/courses/{courseId}", CourseResource.class);

        router.attach("/students", StudentsResource.class);
        router.attach("/students/{studentId}", StudentResource.class);

        router.attach("/login", LoginResource.class);

        return router;
    }
}
