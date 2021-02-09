import java.net.*;
import java.io.*;

public class Udp_client {
    public static void main(String args[]) {
        DatagramSocket socket;
        int port = 7777;
        String str;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        byte[] buffer = new byte[65500];

        try {
            socket = new DatagramSocket();
            InetAddress host_add = InetAddress.getByName("localhost");

            while (true) {
                str = reader.readLine();
                byte[] b_arr = str.getBytes();

                DatagramPacket msg = new DatagramPacket(b_arr, b_arr.length, host_add, port);
                socket.send(msg);

                DatagramPacket res_b = new DatagramPacket(buffer, buffer.length);
                socket.receive(res_b);

                byte[] data = res_b.getData();
                String res_str = new String(data, 0, res_b.getLength());
                System.out.println(res_b.getAddress() + ": " + res_str);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
