import java.net.*;
import java.io.*;

public class DateClient implements Runnable {
    public void run() {
        try {
            // this could be changed to an IP name
            // or address other than the localhost
            Socket sock = new Socket("127.0.0.1", 6013);
            InputStream in = sock.getInputStream();
            BufferedReader bin = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = bin.readLine()) != null)
                System.out.println(line);
            sock.close();
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }
}
