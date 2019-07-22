package api;

import Util.Hashing;
import Util.JWT;
import Util.JsonMapRepresentation;
import conf.Configuration;
import data.UsersDAO;
import model.User;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.springframework.dao.DataAccessException;

import java.util.HashMap;
import java.util.Map;

public class LoginResource extends ServerResource {

    private final UsersDAO usersDAO = Configuration.getInstance().getUsersDAO();

    @Override
    protected Representation post(Representation entity) throws ResourceException {
        try {
            Form form = new Form(entity);
            String email = form.getFirstValue("email");
            String hashedPassword = form.getFirstValue("password");
            if (email == null || hashedPassword == null || email.equals("") || hashedPassword.equals("")) {
                return JsonMapRepresentation.getJSONforError("Missing or empty parameter(s)");
            }
            // hash password for security
            hashedPassword = Hashing.getHashSHA256(hashedPassword);
            // check authentication and create Jason Web Token to return
            User u = usersDAO.authenticateUser(email, hashedPassword);
            if (u == null){
                return JsonMapRepresentation.getJSONforError("Incorrect credentials");
            }
            String jwt = JWT.createJWT(u, Configuration.getInstance().getLoginTTL());
            Map<String,Object> data = new HashMap<>();
            data.put("jwt", jwt);
            data.put("user", u);
            return JsonMapRepresentation.getJSONforMap(data);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return JsonMapRepresentation.getJSONforError("data base error");
        }
    }

}
