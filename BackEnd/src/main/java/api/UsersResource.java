package api;

import Util.Feedback;
import Util.Hashing;
import Util.JsonMapRepresentation;
import conf.Configuration;
import data.UsersDAO;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.springframework.dao.DataAccessException;


public class UsersResource extends ServerResource {

    public final UsersDAO usersDAO = Configuration.getInstance().getUsersDAO();

    @Override
    protected Representation put(Representation entity) throws ResourceException {
        // changes password for user
        try {
            int userId = getUserId();
            Form form = new Form(entity);
            String oldpassword = form.getFirstValue("oldpassword");
            String newpassword = form.getFirstValue("newpassword");
            if (oldpassword == null || newpassword == null || oldpassword.equals("") || newpassword.equals("")) {
                return JsonMapRepresentation.getJSONforError("Missing or empty parameter(s)");
            }
            // hash passwords
            String oldHashedPassword = Hashing.getHashSHA256(oldpassword);
            String newHashedPassword = Hashing.getHashSHA256(newpassword);
            // change password
            Feedback fb = usersDAO.changeUserPassword(userId, oldHashedPassword, newHashedPassword);
            // noinspection Duplicates
            if (!fb.SUCCESS && fb.STATUS == -1) {
                this.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
                return null;
            } else if (!fb.SUCCESS) return JsonMapRepresentation.getJSONforError(fb.MESSAGE);
            return JsonMapRepresentation.SUCCESS_JSON;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return JsonMapRepresentation.getJSONforError("data base error");
        } catch (Exception e){
            return JsonMapRepresentation.getJSONforError(e.getMessage());
        }
    }


    private int getUserId() throws Exception {
        int userId;
        try {
            String userIdStr = (String) getRequest().getAttributes().get("userId");
            try {
                userId = Integer.parseInt(userIdStr);
            } catch (NumberFormatException e) {
                throw new Exception("URI user id is not an integer");
            }
        } catch (NullPointerException e){
            System.err.println("Warning: NullPointerException in fetching variable from URI");
            throw new Exception("Could not fetch URI user id");
        }
        return userId;
    }

}
