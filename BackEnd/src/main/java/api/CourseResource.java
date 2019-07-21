package api;

import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

public class CourseResource extends ServerResource {

    @Override
    protected Representation get() throws ResourceException {
        Integer courseId = getCourseId();

        // TODO

        return null;
    }


    ///////////////////////////////////////

    private Integer getCourseId(){
        Integer courseId;
        try {
            String courseIdStr = (String) getRequest().getAttributes().get("courseId");
            try {
                courseId = Integer.parseInt(courseIdStr);
            } catch (NumberFormatException e) {
                courseId = null;
            }
        } catch (NullPointerException e){
            System.err.println("Warning: NullPointerException in fetching variable from URI");
            courseId = null;
        }
        return courseId;
    }

}
