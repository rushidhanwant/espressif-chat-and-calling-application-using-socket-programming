
import java.io.*;
import java.net.*;
import java.util.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

class Runner {

	Scanner sc = new Scanner(System.in);
	Socket socket = null;
	InputStream inStream = null;
	String st;
	String name;
	DataInputStream dataIn = null;
	SourceDataLine inSpeaker = null;

	Runner(String s, String s1) throws UnknownHostException, IOException, LineUnavailableException {
		socket = new Socket("localhost", 9999);
		name = s;
		st = s + "@" + s1;
		PrintWriter pr = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		pr.println(st);
		pr.flush();
		write();
		read();
	}

	void read() {
		new Thread() {
			public void run() {
				InputStream input;

				try {
					input = socket.getInputStream();

					SourceDataLine inSpeaker = null;
					DataInputStream dataIn = new DataInputStream(input);
					AudioFormat af = new AudioFormat(4000.0f, 8, 1, true, false);
					DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);
					inSpeaker = (SourceDataLine) AudioSystem.getLine(info);
					inSpeaker.open(af);
					int bytesRead = 0;
					byte[] inSound = new byte[1000];
					inSpeaker.start();
					while (bytesRead != -1) {
						try {
							bytesRead = dataIn.read(inSound, 0, inSound.length);
						} catch (Exception e) {
						}
						if (bytesRead >= 0) {
							System.out.println("from_client_read:-" + bytesRead);
							inSpeaker.write(inSound, 0, bytesRead);
						}
					}

				} catch (Exception e) {
					System.out.println("error3");
					e.printStackTrace();
					System.exit(0);
				}
			}

		}.start();
	}

	void write() {
		new Thread() {
			public void run() {
				OutputStream output;
				try {
					output = socket.getOutputStream();
					DataOutputStream dos = new DataOutputStream(output);
					AudioFormat af = new AudioFormat(4000.0f, 8, 1, true, false);
					DataLine.Info info = new DataLine.Info(TargetDataLine.class, af);
					TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info);
					microphone.open(af);
					int bytesRead = 0;
					byte[] soundData = new byte[1000];
					microphone.start();

					while (bytesRead != -1) {
						bytesRead = microphone.read(soundData, 0, soundData.length);
						if (bytesRead >= 0) {
							dos.write(soundData, 0, bytesRead);
						}
					}

				} catch (Exception e) {
					System.out.println("error4");
					e.printStackTrace();
					System.exit(0);
				}
			}
		}.start();
	}

}

class Client {
	static String s;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the client name :: ");
		s = sc.nextLine();
		connectClient();
		sc.close();
	}

	static public void connectClient() {
		Scanner sc = new Scanner(System.in);
		try {
			System.out.println("Whom to message :: ");
			String s1 = sc.nextLine();
			new Runner(s, s1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}
	}
}