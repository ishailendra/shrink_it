package dev.techsphere.shrinkit.utils;

public class Base62Encoder {

    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String encode(long number) {
        StringBuilder sb = new StringBuilder();
        while (number > 0) {
            int remainder = (int) (number % 62);
            sb.append(BASE62.charAt(remainder));
            number /= 62;
        }
        return sb.reverse().toString();
    }

    public static long decode(String base62) {
        long result = 0;
        for (int i = 0; i < base62.length(); i++) {
            result = result * 62 + BASE62.indexOf(base62.charAt(i));
        }
        return result;
    }
}

