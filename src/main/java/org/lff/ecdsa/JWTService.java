package org.lff.ecdsa;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Logger;


public class JWTService {

    private static final Logger logger = Logger.getLogger(JWTService.class.getName());

    private PublicKey publicKey;
    private PrivateKey privateKey;

    private JWTService() {

    }

    public static JWTService build() {
        return new JWTService();
    }

    public JWTService publicKey(String publicKey) {
        this.loadPublicKey(publicKey);
        return this;
    }

    public JWTService privateKey(String privateKey) {
        this.loadPrivateKey(privateKey);
        return this;
    }

    public void validate(String jwt) {
        check();
        String[] items = jwt.split("\\.");
        if (items.length != 3) {
            throw new RuntimeException("Invalid JWT Token");
        }
        String header = decodeToString(items[0]);
        String body = decodeToString(items[1]);
        String sign = items[2];

        logger.info("Validating " + header + " " + body);


        String k = encode(header) + "." + encode(body);
        boolean b = es384(k, sign);
        if (!b) {
            throw new RuntimeException("Sign is not valid");
        }
    }

    private boolean es384(String key, String sign) {
        try {
            Signature ecdsa = Signature.getInstance("SHA384withECDSAinP1363Format");
            ecdsa.initVerify(publicKey);
            ecdsa.update(key.getBytes());
            boolean result = ecdsa.verify(decode(sign));
            return result;
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    private void check() {
        if (this.privateKey == null) {
            throw new RuntimeException("Private key is not set");
        }

        if (this.publicKey == null) {
            throw new RuntimeException("Public key is not set");
        }
    }


    private String encode(String str) {
        return encode(str.getBytes(StandardCharsets.UTF_8));
    }

    private String encode(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private byte[] decode(String str) {
        return Base64.getUrlDecoder().decode(str);
    }
    private String decodeToString(String str) {
        return new String(decode(str), StandardCharsets.UTF_8);
    }

    private void loadPublicKey(String publicKey) {
        try {
            byte[] encoded = Base64.getDecoder().decode(publicKey);
            KeyFactory kf = KeyFactory.getInstance("EC");
            EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
            this.publicKey = (ECPublicKey)kf.generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadPrivateKey(String privateKey) {
        try {
            byte[] encoded = Base64.getDecoder().decode(privateKey);
            KeyFactory kf = KeyFactory.getInstance("EC");
            EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            this.privateKey = (ECPrivateKey)kf.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
