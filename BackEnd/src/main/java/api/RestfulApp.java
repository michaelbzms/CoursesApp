package api;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class RestfulApp extends Application {

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
