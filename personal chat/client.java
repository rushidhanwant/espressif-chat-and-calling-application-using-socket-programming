

import java.io.*;
import java.net.*;
import java.util.*;
class threads 
{
	
	Scanner sc=new Scanner(System.in);
	 Socket socket=null;
	 InputStream inStream=null;
	 String st;
	 String name;
	 threads(String s,int x,String s1) throws UnknownHostException, IOException
	 {
		 socket=new Socket("localhost",9999);
		 name=s;
		 	st=s+"@"+x+"@"+s1;
			PrintWriter pr=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			pr.println(st);
			pr.flush();
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
					System.out.println(dis.readLine());
					
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
				if(str.equals("stop"))
				{
				 client1.connectclient();
					return;
				}
			if(str!=null)
			{
					pr.println(name+" :-"+str);
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
	 static String s;
	public static void main(String args[]) throws IOException
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("enter client name");
		 s=sc.nextLine();
		connectclient();		
	}
		static public void connectclient()throws IOException
		{
			Scanner sc=new Scanner(System.in);
			System.out.println("1.group chat\n2.personal chat");
			int x=sc.nextInt();
			sc.nextLine();
			System.out.println("whom to message");
			String s1=sc.nextLine();
			System.out.println("type stop to end conversation");
			threads obj=new threads(s,x,s1);
		}
}