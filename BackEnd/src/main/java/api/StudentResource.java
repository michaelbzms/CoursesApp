package api;

import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

public class StudentResource extends ServerResource {

    @Override
    protected Representation get() throws ResourceException {
        Integer studentId = getStudentId();

        // TODO

        return null;
    }


    ///////////////////////////////////////

    private Integer getStudentId(){
        Integer studentId;
        try {
            String courseIdStr = (String) getRequest().getAttributes().get("studentId");
            try {
                studentId = Integer.parseInt(courseIdStr);
            } catch (NumberFormatException e) {
                studentId = null;
            }
        } catch (NullPointerException e){
            System.err.println("Warning: NullPointerException in fetching variable from URI");
            studentId = null;
        }
        return studentId;
    }

}
