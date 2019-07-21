package Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.LinkedHashMap;
import model.User;
import org.restlet.Request;
import org.restlet.data.Header;
import org.restlet.util.Series;

// Jason Web Token Handling class
public class JWT {
    // TODO: Test when done with login logic

    // The secret key. This should be in a property file NOT under source
    // control and not hard coded in real life. We're putting it here for
    // simplicity.
    private static String SECRET_KEY = "oeRaYY7Wo24sDqKSX3IM9ASGmdGPmkTd9jo1QTy4b7P9Ze5_9hKolVX8xNrQDcNRfVEdTZNOuOyqEGhXEbdJI-ZQ19k_o9MI0y3eZN2lp9jow55FfXMiINEdt1XR85VipRLSOkT6kSpzs2x-jbLDiz9iFVzkd81YKxMgPA7VfZeQUm4n-mOmnWMaVX30zGFU4L3oPBctYKkl4dYfqYWqRNfrgPJVi5DGFjywgxx0ASEiJHtV72paI3fDR2XwlSkyhhmY-ICjCRmsJN4fX1pdoL8a18-aQrvyu4j0Os6dVPYIoPvvY0SAZtWYKHfM15g7A3HD4cVREf9cUsprCRK93w";

    public static String createJWT(User u, long ttlMillis){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        long nowMillis = System.currentTimeMillis();
        JwtBuilder b = Jwts.builder()
                .claim("user", u)
                .setIssuedAt(new Date(nowMillis))
                .setIssuer("CourseApp")
                .setSubject("user_session_info")
                .signWith(signingKey, signatureAlgorithm);
        if (ttlMillis > 0) {
            b.setExpiration(new Date(ttlMillis + nowMillis));
        }
        return b.compact();
    }

    public static User getUserFromJWT(String jwt) throws Exception {
        try {
            if (jwt == null) return null;
            Claims claims = decodeJWT(jwt);
            return User.getUserFromMap((LinkedHashMap) claims.get("user"));
        } catch (Exception e){
            throw new Exception("Could not decode JWT token");
        }
    }

    private static Claims decodeJWT(String jwt) throws Exception {
        //This line will throw an exception if it is not a signed JWT (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .setAllowedClockSkewSeconds(1000000)
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    public static String getJWTFromHeaders(Request request) {
        if (request == null) return null;
        try {
            Series<Header> headers = request.getHeaders();
            return (headers != null) ? headers.getFirstValue("token") : null;
        } catch (Exception e){
            return null;    // there was no jwt in Headers
        }
    }


}
