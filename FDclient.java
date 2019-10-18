

import java.io.*;
import java.net.*;
import java.util.*;
class threads 
{
	
	Scanner sc=new Scanner(System.in);
	 Socket socket=null;
	 InputStream inStream=null;
	 threads() throws UnknownHostException, IOException
	 {
		 socket=new Socket("localhost",9999);
			
			read();
			write();
	 }
	
	void read()
	{
		Thread t=new Thread()
		{	
			public void run()
			{
				while(true)
				{
				InputStream input;
				try {
					input = socket.getInputStream();
				InputStreamReader in=new InputStreamReader(input);//converts byte to character
				BufferedReader dis=new BufferedReader(in);
					System.out.println("server:- "+dis.readLine());
					
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("error");
						System.exit(0);
					}
			
					
				}
			}
			
			
		};
		t.start();
	}
	void write()
	{
		Thread t=new Thread() {
			public void run()
			{
				while(true)
				{
				OutputStream output;
				try {
					
					output = socket.getOutputStream();
				OutputStreamWriter out=new OutputStreamWriter(output);//converts character stream to byte stream
				PrintWriter pr=new PrintWriter(out);//object to text ;
				String str=sc.nextLine();
				
			if(str!=null)
			{
					pr.println(str);
					pr.flush();	
				}
			
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("error");
					System.exit(0);
				}//bytes
				}		
		}
		};
	t.start();
}
	
	
}
 class client1 
{
	public static void main(String args[]) throws IOException
	{
		
		threads obj=new threads();
			
	}
}