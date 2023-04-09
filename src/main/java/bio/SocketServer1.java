package bio;

import lombok.extern.slf4j.Slf4j;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * 多线程版BIO
 */
@Slf4j
public class SocketServer1 {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8080);
        log.info("Server started on port 8080");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            log.info("Client connected");
            new Thread(() -> {
                try {
                    processClient(clientSocket);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }).start();
        }
    }

    private static void processClient(Socket clientSocket) throws Exception {
        log.info("process Client connected");
        byte[] bytes = new byte[1024];
        int bytesRead = clientSocket.getInputStream().read(bytes);
        while (bytesRead != -1) {
            String message = new String(bytes, 0, bytesRead);
            log.info("Received message: " + message);
            bytesRead = clientSocket.getInputStream().read(bytes);
        }

        log.info("Client disconnected");
        clientSocket.close();
    }
}
