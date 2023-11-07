package leetcode2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Codec {

    private Map<String, String> encodecUrlToOrignalUrlMap = new HashMap<>();

    // Encodes a URL to a shortened URL.
    public String encode(String longUrl) {
        String encoded = "";
        try {
            encoded = String.valueOf(MessageDigest.getInstance("MD5").digest((String.valueOf(System.currentTimeMillis()) + longUrl).getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        encodecUrlToOrignalUrlMap.put(encoded, longUrl);
        return encoded;
    }

    // Decodes a shortened URL to its original URL.
    public String decode(String shortUrl) {
        return encodecUrlToOrignalUrlMap.get(shortUrl);
    }
}
