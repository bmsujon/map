package com.jachai.map.util;

import com.jachai.map.dto.ClientInfo;
import com.jachai.map.exception.UnAuthorizeException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class TokenUtil {
    @Value("${jwt.key}")
    private String jwtKey;
    @Value("${jwt.expiration.millis}")
    private long expirationInMillis;

    public String generateJwtToken(String issuer, String id) {
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuer(issuer)
                .signWith(SignatureAlgorithm.HS256, jwtKey);

        if (expirationInMillis >= 0) {
            long expMillis = System.currentTimeMillis() + expirationInMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        String token = builder.compact();
        return token;
    }

    public void printStructure(String token) {
        Jws<Claims> parseClaimsJws = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token);

        log.info("Header     : " + parseClaimsJws.getHeader());
        log.info("Body       : " + parseClaimsJws.getBody());
        log.info("Signature  : " + parseClaimsJws.getSignature());
    }

    public String verifyToken(String authorization) {
        if ( authorization == null ) throw new UnAuthorizeException("No token");
        String[] splitAuthorization = authorization.split(" ");
        if ( splitAuthorization.length < 2 ) throw new UnAuthorizeException("Bad token format");
        String token = splitAuthorization[1];

        try {
            Claims body = Jwts.parser()
                    .setSigningKey(jwtKey)
                    .parseClaimsJws(token)
                    .getBody();
            return body.getId();
        } catch (ClaimJwtException e) {
            throw new UnAuthorizeException("Bad token");
        } catch (Exception e) {
            throw new UnAuthorizeException("Bad token");
        }
    }

    public ClientInfo verifyTokenWithInfo(String authorization) {
        if ( authorization == null ) throw new UnAuthorizeException("No token");
        String[] splitAuthorization = authorization.split(" ");
        if ( splitAuthorization.length < 2 ) throw new UnAuthorizeException("Bad token format");
        String token = splitAuthorization[1];
        ClientInfo clientInfo = new ClientInfo();
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(jwtKey)
                    .parseClaimsJws(token)
                    .getBody();
            clientInfo.id = body.getId();
            clientInfo.mobileNumber = (String) body.get("mobileNumber");
            clientInfo.name = (String) body.get("name");
            clientInfo.type = (String) body.get("type");
            clientInfo.profilePicture = (String) body.get("profilePicture");
            return clientInfo;
        } catch (ClaimJwtException e) {
            throw new UnAuthorizeException("Bad token");
        } catch (Exception e) {
            throw new UnAuthorizeException("Bad token");
        }
    }
}
