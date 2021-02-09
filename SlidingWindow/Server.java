package SlidingWindow;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class Server {

    DatagramSocket socket;
    int windowSize = 5;
    List<Integer> packets;

    public Server() {
        try {
            socket = new DatagramSocket(5000);
            byte[] buffer = new byte[256];
            DatagramPacket input = new DatagramPacket(buffer, buffer.length);
            getInput(input);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String args[]) {
        Server server = new Server();
    }

    private void getInput(DatagramPacket input) {
        try {
            packets = new ArrayList<Integer>();
            while (true) {
                socket.receive(input);
                byte[] data = input.getData();
                String input_str = new String(data, 0, input.getLength());
                int key = Integer.parseInt(input_str);
                Thread.sleep(500);

                System.out.println("Data Recieverd:  " + key);
                if (!packets.contains(key)) {
                    packets.add(key);
                }

                if (packets.size() == windowSize) {
                    int j = getRandomNumberInRange(0, packets.size());
                    sendResponce(j, input);
                }
            }
        } catch (IOException e) {
            System.out.print(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendResponce(int key, DatagramPacket input) {
        String k = String.valueOf(key);
        byte[] b = k.getBytes();
        DatagramPacket data = new DatagramPacket(b, b.length, input.getAddress(), input.getPort());
        try {
            socket.send(data);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        return (int) (Math.random() * ((max - min) + 1)) + min;
    }
}
