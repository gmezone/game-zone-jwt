package com.gamezone.jwt;

import org.jose4j.base64url.Base64;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.JoseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Map;

public class TestMain {

    private static final String KEY_STRING = "GtdPvDfyfegbARYG";
    private static final String EXP = "exp";
    static AesKey key = new AesKey(KEY_STRING.getBytes(StandardCharsets.ISO_8859_1));
    static private Signer signer;
    static SignatureVerifier verifier;
    static private String verifierKey;
    static private JsonParser objectMapper = JsonParserFactory.create();


    static public void setKeyPair() {
        ClassPathResource cpr = new ClassPathResource("jwt.jks");
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(cpr, "GameZoneQ!W@".toCharArray());
        System.out.println(keyStoreKeyFactory.getClass());
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("jwt");
        PrivateKey privateKey = keyPair.getPrivate();
        Assert.state(privateKey instanceof RSAPrivateKey, "KeyPair must be an RSA");
        signer = new RsaSigner((RSAPrivateKey) privateKey);
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        verifier = new RsaVerifier(publicKey);
        verifierKey = "-----BEGIN PUBLIC KEY-----\n" + new String(Base64.encode(publicKey.getEncoded()))
                + "\n-----END PUBLIC KEY-----";
        System.out.println("verifierKey:" + verifierKey);
    }

    public static void main(String[] args) throws JoseException {
        setKeyPair();
        String content = "{\"kodMevakeshMeida\":\"1\" ,\"clientId\":\"039503164\",\"user_name\":\"aaa\",\"exp\":\"1447863943511\" }";
        System.out.println("encode content: " + content);
        JsonWebEncryption jwe = new JsonWebEncryption();
        jwe.setPayload(content);
        jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
        jwe.setEncryptionMethodHeaderParameter((ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256));
        jwe.setKey(key);
        jwe.enableDefaultCompression();
        jwe.getCompactSerialization();
        String serializedJwe = jwe.getCompactSerialization();
        System.out.println("serializedJwe: " + serializedJwe);
        System.out.println(signer);

        String token = JwtHelper.encode(serializedJwe,signer).getEncoded();
        // token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.ZXlKaGJHY2lPaUpCTVRJNFMxY2lMQ0psYm1NaU9pSkJNVEk0UTBKRExVaFRNalUySWl3aWVtbHdJam9pUkVWR0luMC55MTdkcFpZUGpjVjR2c0p2MmhmMlZnQXRXR2hsTGYtRDJ5N29zRDhMOXZXQ0dGSnN4NzZzSlEuajRLTF9JMUdQTDVsNk9BMUpKejNTZy5lYi1xbkE4bDhGS1Vsd0xlOFZ2ZU1TTVFlZ0JIdkZXWnZ5UVo1dDI1bTlzeUxWLVYtNVkzNXAtS3VfbVFac3FDLmt1MmhYS2I4eGZNTVF5TDBGaVF0cnc.SkxecVUKLcjEep6lYavsFbpl5y75a6sNt-9japfOOXrTT1VeSn5cOZ7vsaLo-jrJq6CEBqjhLby_GKVjS2GR-4g1-I-6HJ0Y27zJjPXHrJRiQKxBYrJrsPq5y7uraFkE1BclW62PSrC1xWFlmx_sV6GT0E9qZn1vn8iw8mF1Im9FHNhbKg6M_kXM-WP2kgRFUdbbCvC7D4esF4RpXM7_QZm3jeF4d3KsqzlaNHEaIgiLwQK490W7R_PlHibC9yEkaFn1TQVqymWyWI8z8h5qvrJ8zrCSJQsEhVyIwLjUf6OWDCaJ8_eE6aism4FbDLZUg/VWxku7Zxi9m8f0XZTJrwQ";
        System.out.println("token=" + token);
        Jwt jwt = JwtHelper.decodeAndVerify(token,verifier);
        System.out.println("jwt: " + jwt);
        String content2 = jwt.getClaims();
        System.out.println(decode(content2));


    }

    static protected Map<String, Object> decode(String content) {

        try {
            if (!content.startsWith("{")) {
                System.out.println("content:" + content);
                JsonWebEncryption jwe = new JsonWebEncryption();
                jwe.setAlgorithmConstraints(new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.WHITELIST,
                        KeyManagementAlgorithmIdentifiers.A128KW));
                jwe.setContentEncryptionAlgorithmConstraints(
                        new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.WHITELIST,
                                ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256));
                jwe.setKey(key);
                System.out.println("key: " + key);
                jwe.setCompactSerialization(content);
                content = jwe.getPayload();
            }
            Map<String, Object> map = objectMapper.parseMap(content);
            if (map.containsKey(EXP)) {
                System.out.println("exp:" + map.get(EXP));
                try {
                    System.out.println(map.get(EXP));
                    Date expDate = new Date(Long.parseLong((String) map.get(EXP)));
                    System.out.println(expDate);

                } catch (java.lang.NumberFormatException e) {

                }
            }
            return map;

        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidTokenException("Cannot convert access token to JSON");
        }
    }
}
