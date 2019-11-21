package server_client4;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.Vector;
class Read
{
	Scanner sc =new Scanner(System.in);
	   String str=null;
	    int x; 
	   Socket socket;
	  String name;
	  String desti;
	   Read(Socket socket1,String name1,String desti1)
	   {
		   socket=socket1;
		   name=name1;
		   desti=desti1;
		   read();
	   }
	public void read()
		{
		
			Thread t=new Thread()
			{	
				public void run()
				{
					boolean  exit=false;
					while(!exit)                              
					{
					InputStream input;
					try {
						input = socket.getInputStream();
					InputStreamReader in=new InputStreamReader(input);//converts byte to character
					BufferedReader dis=new BufferedReader(in);
					str=dis.readLine();
					
					if(str.equals(name+" :-stop"))
					{
						socket.close();
						return;
					}
					new connection(str,socket,name,desti);
					
						} catch (Exception e) {
							System.out.println("error");
							System.exit(0);
						}
					} 
				}
			};         
			t.start();
}
}
class connection
{
	String str=null;
	   Socket socket;
		  String name;
		  String desti;
	 connection(String s,Socket socket1,String name1,String desti1)
	{
		   socket=socket1;
		   name=name1;
		   desti=desti1;
		   str=s;
		   connect();
	}
	 public void  connect()
	 {
		 sockets obj=new sockets();
		 if(obj.name.contains(desti))
		 {
			int x= obj.name.indexOf(desti);
			Socket destisocket=(Socket)obj.socket.get(x);
			new Write(str,destisocket);
		 }
		 else
		 {
			 str="client not available";
			 new Write(str,socket);
		 }
	 }
}
   class Write
   { 
	   Scanner sc =new Scanner(System.in);
	   String str=null;
	   static int x; 
	   Socket socket;
	  
	  Write(int n) throws IOException
	  {

	  }
	   Write(String str1 ,Socket socket1)
	   {
		   str=str1;
		   socket=socket1;  
		   write();
	   }
	  
	   void write( )
		{
			Thread t=new Thread() {
				public void run()
				{	
					OutputStream output;
					InputStream input;
					try {
						output = socket.getOutputStream();
					OutputStreamWriter out=new OutputStreamWriter(output);//converts character stream to byte stream
					PrintWriter pr=new PrintWriter(out);//object to text ;	
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
			};
		t.start();
	}	   
   }
   class sockets extends Thread
   {
	  static Vector <Socket>socket=new Vector<Socket>();
	  static Vector <String>name=new Vector<String>();
	  static Vector <String>type=new Vector<String>();
	  static Vector <String>desti=new Vector<String>();
	  static Vector <Vector>group=new Vector<Vector>();
		ServerSocket Ssocket;
		sockets()
		{
			
		}
		sockets(ServerSocket socket)
		{
			Ssocket=socket;
		}
		public void run()
		{
		for(int i=0;true;i++)
		{
			Socket socket1;
			try {
				socket.add(Ssocket.accept());
			socket1=(Socket)socket.get(i);
			String s=(new BufferedReader(new InputStreamReader(socket1.getInputStream())).readLine());
			String a[]=s.split("@");
			name.add(a[0]);
			type.add(a[1]);
			desti.add(a[2]);
			System.out.println((String)name.get(i)+" is connected");
			if(type.get(i).equals("2"))
			{
			new Read((Socket)socket.get(i),(String)name.get(i),(String)desti.get(i));
			}
			else if(type.get(i).equals("1"))
			{
				
			}
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		}
   }
class server
{
	
	public static void main(String args[]) throws IOException 
	{
		Scanner sc=new Scanner(System.in);
		ServerSocket Ssocket;   
		Ssocket=new ServerSocket(9999);
		new sockets(Ssocket).start();
	}	
}