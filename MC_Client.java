

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MC_Client {
    private static final String server_host = "localhost";
    private static final int server_port = 22554;

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public void start() {
        try {
            socket = new Socket(server_host, server_port);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            System.out.println("> Connected to the server.");

            Thread receiveThread = new Thread(this::receiveMessages);
            receiveThread.start();

            sendUsername();
            sendMessageLoop();
        } catch (IOException e) {
            System.out.println("> Error: Failed to connect to the server.");
        } finally {
            closeConnection();
        }
    }

    private void sendUsername() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> Please enter your username: ");
        String username = scanner.nextLine();
        outputStream.write(username.getBytes());
    }

    private void sendMessageLoop() throws IOException {

        Scanner scanner = new Scanner(System.in);
        String message;

        while (true) {
            message = scanner.nextLine();
            outputStream.write(message.getBytes());
            if (message.equalsIgnoreCase("exit")) {
                break;
            }
        }
    }

    private void receiveMessages() {
        byte[] buffer = new byte[1024];
        int bytesRead;

        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                String message = new String(buffer, 0, bytesRead).trim();
                System.out.println("> "+message);
            }
        } catch (IOException e) {
            System.out.println("> Disconnected from the server.");
        } finally {
            closeConnection();
        }
    }

    private void closeConnection() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MC_Client client = new MC_Client();
        client.start();
  }
}
