
import java.io.*;
import java.net.*;

public class server {

    public static void main(String[] args) {
        
        int byteRead = 0;
        try {
            ServerSocket serverSocket = new ServerSocket(9999);            
                InputStream in = clientSocket.getInputStream();

                OutputStream os = new FileOutputStream("C:\\Users\\HP\\Desktop\\dhanwant.txt");
                byte[] byteArray = new byte[1024];

                while ((byteRead = in .read(byteArray, 0, byteArray.length)) != -1) {
                    os.write(byteArray, 0, byteRead);  
                }
                os.close();
                serverSocket.close();
        } catch (Exception e) {
            System.out.println( e.getMessage());
            e.printStackTrace();
        }
    }

}