package SlidingWindow;

import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
    DatagramSocket socket;
    int port = 5000;
    byte[] buffer;
    InetAddress host_add;
    int packetCount = 10;
    int windowSize = 5;
    Map<Integer, String> packets;

    public Client() {
        packets = new HashMap<Integer, String>();
        buffer = new byte[1024];

        for (int i = 0; i < 10; i++) {
            packets.put(i, String.valueOf(i) + ' ' + String.valueOf(i % 5));
        }

        try {
            socket = new DatagramSocket();
            host_add = InetAddress.getByName("localhost");
            // for (String str : packets) {
            // byte[] arr = str.getBytes();
            // DatagramPacket msg = new DatagramPacket(arr, arr.length, host_add, port);
            // socket.send(msg);
            // }
            int sent = 0;
            int i = 0;
            while (true) {

                for (i = 0; i < windowSize; i++) {
                    sendData(sent);
                    sent++;
                    if (sent == windowSize) {
                        break;
                    }
                }
                int nxtAck = checkResponce();

                System.out.println("Last Ack: " + nxtAck);

                if (nxtAck == windowSize) {
                    break;
                } else {
                    sent = nxtAck;
                }
            }

        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public static void main(String args[]) {
        Client client = new Client();
    }

    private Boolean sendData(int i) {
        String data = String.valueOf(i);
        byte[] str = data.getBytes();
        DatagramPacket pac = new DatagramPacket(str, str.length, host_add, port);
        try {
            socket.send(pac);
            return true;
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
    }

    private int checkResponce() {
        byte[] buffer = new byte[1024];
        DatagramPacket input = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(input);
            byte[] data = input.getData();
            String s = new String(data, 0, input.getLength());
            int ack = Integer.parseInt(s);
            return ack;
        } catch (IOException e) {
            System.out.println(e);
            return -1;
        }
    }
}
