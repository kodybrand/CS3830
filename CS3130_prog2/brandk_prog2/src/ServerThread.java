
import java.io.*;
import java.net.*;

/**
 *
 * @author Kody
 */
public class ServerThread extends Thread
{

    private PrintWriter writeSock;
    private BufferedReader readSock;
    private Socket sock;

    public ServerThread(Socket sock)
    {
        this.sock = sock;
        try
        {
            writeSock = new PrintWriter(sock.getOutputStream(), true);
            readSock = new BufferedReader(new InputStreamReader(
                    sock.getInputStream()));
        } catch (Exception e)
        {
            System.out.println("ERROR : " + e.getClass().toString() + e);
        }
    }

    @Override
    public void run()
    {
        try
        {
            boolean quitTime = false;
            while (!quitTime)
            {
                String inLine = readSock.readLine();
                String outLine = inLine;
                writeSock.println(outLine);
                if (inLine.equalsIgnoreCase("quit"))
                {
                    quitTime = true;
                }
            }
            writeSock.println("Good Bye!");
            sock.close();
        } catch (Exception e)
        {
            System.out.println("ERROR : " + e.getClass().toString() + e);
        }

    }
}
