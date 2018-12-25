package com.fyp.utils.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.*;

public class JwtUtil {

    private static final String SECRET = "3734b137-1b5f-4434-8ce8-b68ed1ec1aaf";

    /**
     * 创建一个TOKEN
     * */
    public static JwtTokenResult createToken(Long userId){
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            Map<String, Object> map = new HashMap<String, Object>();

            Date nowDate = new Date();
            Calendar cal = new GregorianCalendar();
            cal.setTime(nowDate);
            cal.add(Calendar.HOUR_OF_DAY, 2);
            Date expireDate = cal.getTime();

            map.put("alg", "HS256");
            map.put("typ", "JWT");

            String token = JWT.create()
                    .withHeader(map)
                    .withClaim("uid", userId)
                    .withIssuer("LayIMServer")
                    .withSubject("LayIM_AccessToken")
                    .withAudience("Web")
                    .withIssuedAt(nowDate)
                    .withExpiresAt(expireDate)
                    .sign(algorithm);
            return new JwtTokenResult(token);

        } catch (JWTCreationException exception){
            exception.printStackTrace();
        }
        return new JwtTokenResult(null);
    }

    /**
     * 解析Token
     * */
    public static JwtVertifyResult verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("LayIMServer")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            Claim claim = claims.get("uid");
            if (claim != null) {
                return new JwtVertifyResult(claim.asLong());
            }
        } catch (JWTVerificationException exception) {
            return JwtVertifyResult.result(exception.getMessage());
        }
        return JwtVertifyResult.NotVertified;
    }
}
