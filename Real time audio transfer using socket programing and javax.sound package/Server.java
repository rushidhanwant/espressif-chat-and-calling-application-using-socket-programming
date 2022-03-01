import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

class Read {
	String str = null;
	Socket socket;
	Socket destinationSocket;
	String name;
	String destination;

	Read(Socket socket, String name, String destination) throws IOException {
		this.socket = socket;
		this.name = name;
		this.destination = destination;
		this.read();
	}

	public void read() {
		if (sockets.name.contains(destination)) {
			int x = sockets.name.indexOf(destination);
			destinationSocket = sockets.socket.get(x);
			System.out.println("In_server_write");

			new Thread() {
				public void run() {
					InputStream input;
					try {
						input = socket.getInputStream();
						DataInputStream dataIn = new DataInputStream(input);
						int bytesR = 0;
						byte[] bytes = new byte[1000];
						while (bytesR != -1) {
							try {
								bytesR = dataIn.read(bytes, 0, bytes.length);
								new Write(bytes, bytesR, destinationSocket);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}

					} catch (Exception e) {
						System.out.println("error1");
						e.printStackTrace();
						System.exit(0);
					}
				}
			}.start();
		} else {
			str = "client not available";
			System.out.println(str);
			new Write(str, socket);
		}
	}

	static class Write {
		String str = null;
		Socket socket;
		byte[] bytes;
		int bytesR;

		Write(String str1, Socket socket1) {
			str = str1;
			socket = socket1;
		}

		Write(byte[] bytes1, int bytesR1, Socket destisocket) {
			bytes = bytes1;
			bytesR = bytesR1;
			socket = destisocket;
			write(bytes, bytesR);
		}

		void write(byte[] bytes1, int bytesR1) {
			new Thread() {
				public void run() {
					OutputStream output;
					try {
						output = socket.getOutputStream();
						DataOutputStream dataOut = new DataOutputStream(output);
						System.out.println("from_server_write:-" + bytesR1);
						dataOut.write(bytes1, 0, bytesR1);
					} catch (Exception e) {
						e.printStackTrace();
						System.exit(0);
					}
				}
			}.start();
		}
	}
}

class sockets extends Thread {
	static Vector<Socket> socket = new Vector<>();
	static Vector<String> name = new Vector<>();
	static Vector<String> destination = new Vector<>();
	ServerSocket serverSocket;

	sockets(ServerSocket socket) {
		serverSocket = socket;
	}

	public void run() {
		int i = 0;
		while (true) {
			Socket socket;
			try {
				sockets.socket.add(serverSocket.accept());
				socket = sockets.socket.get(i);
				String s = (new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine());
				String[] a = s.split("@");
				name.add(a[0]);
				destination.add(a[1]);
				System.out.println(name.get(i) + " is connected");
				new Read(sockets.socket.get(i), name.get(i), destination.get(i));
			} catch (IOException e) {
				e.printStackTrace();
			}
			i++;
		}
	}
}

class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket Ssocket = new ServerSocket(9999);
		new sockets(Ssocket).start();
	}
}