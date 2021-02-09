package socket;

import java.util.*;

public class Dns {
    public static void main(String args[]) {
        Map<String, String> map = new HashMap<String, String>();
        String[] websites = { "www.google.com", "www.youtube.com", "www.github.com" };
        String[] dns = { "149.112.112.112", "208.67.220.220", "185.228.169.9" };

        for (int i = 0; i < websites.length; i++) {
            map.put(websites[i], dns[i]);
        }
    }
}
