
import java.net.*;

/**
 *
 * @author Kody
 */
public class Server {

    private static int PORT = 55661;

    public void run() {
        try {
            ServerSocket servSock = new ServerSocket(PORT);
            while (true) {
                Socket sock = servSock.accept();
                System.out.println(sock.getRemoteSocketAddress().toString() + " # " + sock.getPort());
                ServerThread servThread = new ServerThread(sock);
                servThread.start();
            }
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getClass().toString() + e);
        }

    }
}
