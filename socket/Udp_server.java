package socket;

import java.net.*;
import java.net.http.WebSocket;
import java.io.*;
import java.util.*;

public class Udp_server {

    static Map<String, String> map = new HashMap<String, String>();

    private static String getIp(String website) {
        String ip = map.get(website);
        return ip;
    }

    public static void main(String args[]) {

        String[] websites = { "www.google.com", "www.youtube.com", "www.github.com" };
        String[] dns = { "149.112.112.112", "208.67.220.220", "185.228.169.9" };

        for (int i = 0; i < websites.length; i++) {
            map.put(websites[i], dns[i]);
        }

        DatagramSocket socket;

        try {
            socket = new DatagramSocket(7777);
            byte[] buffer = new byte[65500];
            DatagramPacket input = new DatagramPacket(buffer, buffer.length);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Server started");

            while (true) {
                socket.receive(input);
                byte[] data = input.getData();
                String input_str = new String(data, 0, input.getLength());
                System.out.println(input.getAddress().getHostName() + ": " + input_str);

                if (input_str.length() > 4) {
                    if (input_str.substring(0, 4).equals("!dns")) {
                        String website = input_str.substring(5);
                        String ip = getIp(website);
                        DatagramPacket r = new DatagramPacket(ip.getBytes(), ip.getBytes().length, input.getAddress(),
                                input.getPort());
                        socket.send(r);
                    }
                }

                String in = reader.readLine();
                DatagramPacket res = new DatagramPacket(in.getBytes(), in.getBytes().length, input.getAddress(),
                        input.getPort());
                // input_str.getBytes().length,
                // input.getAddress(), input.getPort());
                socket.send(res);
            }
        } catch (IOException e) {
            System.out.println(e);
        }

    }
}
