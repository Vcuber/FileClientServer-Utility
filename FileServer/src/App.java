import java.io.*;
import java.net.*;

public class App {

    public static void main(String[] args) throws Exception {

        int bytesRead;

        ServerSocket serverSocket = null;
        serverSocket = new ServerSocket(0);

        System.out.println("Creating port on: "+serverSocket.getLocalPort());
        System.out.println("IP Address: "+InetAddress.getLocalHost());

        while(true) {
            try {
            Socket clientSocket = null;
            clientSocket = serverSocket.accept();
            String homepath = System.getProperty("user.home");
            FileInputStream is = (FileInputStream) clientSocket.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            String filename = dis.readUTF();
            //long filesize = dis.readLong();
            OutputStream os = new FileOutputStream(homepath+"/Downloads/"+filename);
            byte[] buffer = new byte[1024];
            while((bytesRead = is.read(buffer /*, 0, (int) Math.min(buffer.length, filesize)*/)) > 0) {
                os.write(buffer, 0, bytesRead);
                //filesize -= bytesRead;
            }
            os.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            serverSocket.close();
        }
        }
    }
}

