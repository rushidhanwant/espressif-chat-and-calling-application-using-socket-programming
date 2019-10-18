

import java.io.*;
import java.net.*;
import java.util.Scanner;

//class server1 
//{
//	public static void main(String args[]) throws IOException
//	{
//		
//		ServerSocket Ssocket=new ServerSocket(9999);
//		
//		Socket socket=Ssocket.accept();
//		
//		while(true)
//		{
//			InputStream input =socket.getInputStream();//bytes
//			InputStreamReader in=new InputStreamReader(input);//converts byte to character
//			BufferedReader dis=new BufferedReader(in);
//			String s = dis.readLine();
//			System.out.println(s);
//			System.out.println(";bnsfiob");
//			final Scanner sc=new Scanner(System.in);
//			String str=sc.nextLine();
//			
//			
//			OutputStream output =socket.getOutputStream();//bytes
//			OutputStreamWriter out=new OutputStreamWriter(output);//converts character stream to byte stream
//			PrintWriter pr=new PrintWriter(out);//object to text ;
//			pr.println(str+"\n");
//				pr.flush();
//				
//			if(dis.readLine().equals("stop"))
//			{
//				socket.close();
//				Ssocket.close();
//			}
//
//		}
//		}
//}
class thread
{
	ServerSocket Ssocket=null;
	Socket socket=null;
	Scanner sc=new Scanner(System.in);
	thread() throws IOException
	{
		Ssocket=new ServerSocket(9999);
		System.out.println("Waiting for client");
		socket=Ssocket.accept();
		System.out.println("connected");
		
		read();
		write();
		
	}
	
	void read()
	{
		Thread t=new Thread()
		{	
			public void run()
			{
				while(socket.isConnected())
				{
				InputStream input;
				try {
					input = socket.getInputStream();
				InputStreamReader in=new InputStreamReader(input);//converts byte to character
				BufferedReader dis=new BufferedReader(in);
					System.out.println("client:- "+dis.readLine());
					
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
				while(socket.isConnected())
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
class server1
{
	public static void main(String args[]) throws IOException 
	{
		thread obj=new thread();
		
	}
}