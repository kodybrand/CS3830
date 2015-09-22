/**
 *
 * @author Kody
 */
public class Main {

    public static void main(String [] args) {
        try {
            Server server = new Server();
        server.run();
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getClass().toString() + e);
        }
        
    }
}
