package cn.yajienet.oauthjwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Slf4j
@SpringBootTest
class OauthJwtApplicationTests {

    private String secret = "hlU5H0WEJV5SoxR01DzGLbG3zE2FXiDs95EH6WrkySK7Itggs0F2ClhILENl6+2NFeTDuqc2CUJUr9mG9tWa6Q==";

    @Test
    void contextLoads() {
        Date expirationDate = new Date(System.currentTimeMillis()+604800*1000);
        Key key =  new SecretKeySpec(Decoders.BASE64.decode(secret),SignatureAlgorithm.HS512.getJcaName());
        String secretString = Encoders.BASE64.encode(key.getEncoded());
        log.info(secretString);
        Claims claims = new DefaultClaims();
        claims.setSubject("Joe");
        claims.setExpiration(new Date());
        String jws = Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(key).compact();
        log.info(jws);
        Claims claim = Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(jws).getBody();
        log.info(claim.getSubject());
    }

}
