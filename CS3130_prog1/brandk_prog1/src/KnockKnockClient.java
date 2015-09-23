import java.io.*;
import java.net.*;

/**
 * Handles all the communication between the Client and Server.
 * @author Kody
 */
public class KnockKnockClient {
    
    Socket sock = null;
    PrintWriter writeSock;
    BufferedReader readSock;
    
    /**
     * Handles the socket connection and disconnection.
     * @param ip IP to connect to
     * @param port Port to connect to
     * @return status of connection or error message
     */
    public String connectSocket(String ip, int port) {
        if(sock == null || sock.isClosed()) {
            try {
                sock = new Socket(ip, port);
                writeSock = new PrintWriter(sock.getOutputStream(), true);
                readSock = new BufferedReader(new InputStreamReader(
                                                sock.getInputStream()));
            } catch (Exception e) {
                return "Error : " + e.getLocalizedMessage() + "\n";
            }
            return "Connected to Server \n";
        } else {
            try {
                sock.close();
            } catch (Exception e) {
                return e.getLocalizedMessage();
            }
            return "Disconnected \n";
        }
    }
    
    /**
     * Checks is the socket is connected or not
     * @return True if connected else false
     */
    public boolean isConnected() {
        return sock.isConnected();
    }
    
    /**
     * Sends a string over socket and waits for a response.
     * @param message Message to be sent
     * @return Response from the server
     */
    public String sendAndRead(String message) {
        String response;
        try {
            writeSock.println(message);
            response = readSock.readLine() + "\n";
        } catch ( Exception e) {
            return "Error : " + e.getLocalizedMessage() + "\n";
        }
        if(response.contains("Good Bye!")) {
            try {
                sock.close();
            } catch (Exception e) {
                return "Error : " + e.getLocalizedMessage() + "\n";
            }
        }
        return response;
        
        
    }
}