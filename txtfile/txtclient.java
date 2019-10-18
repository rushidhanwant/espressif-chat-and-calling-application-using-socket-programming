package server_client3;

import java.io.*;
import java.net.*;

public class client {

public static void main(String[] args)
{
    Socket socket;
    try
    {
        socket = new Socket("localhost", 9999);            
       
        File myfile = new File("C:\\Users\\HP\\Desktop\\ganesh.txt");       //local file path.
        byte[] byteArray = new byte[1024];

        FileInputStream fis = new FileInputStream(myfile);
        BufferedInputStream bis = new BufferedInputStream(fis);
        OutputStream os = socket.getOutputStream();
        int trxBytes =0;
        while(( bis.read(byteArray, 0, byteArray.length)) !=-1)
        {           
        	os.write(byteArray, 0, byteArray.length);
        	
        }
        os.flush();
        bis.close();
        socket.close();
        
    }
    catch(Exception e)
    {
        System.out.println(e.getMessage());
    }       
}
}
