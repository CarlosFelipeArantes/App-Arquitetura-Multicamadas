package socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/* 
 * Main server class. This class includes main(), and is the class that listens
 * for incoming connections and starts ServerThreads to handle those connections
 *
 */
public class Server {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<Thread> list = new ArrayList<Thread>();
		Arquivo arquivo = new Arquivo();
		
		Thread thrd1 = new Thread() {
		    public void run() {
		    	try {
					ServerSocket socket = new ServerSocket(15432);
					System.out.println("Server listening on port 15432");

					// loop (forever) until program is stopped
					while(true) {
						Socket client = socket.accept();
						Thread thrd = new Thread(new ServerThread(client,arquivo));
						list.add(thrd);
						thrd.start();
					}
				}
				catch (IOException ioe){
					ioe.printStackTrace();
				}
		    }
		};
		thrd1.start();
		
		Thread thrd2 = new Thread() {
		    public void run() {
		    	try {
					ServerSocket socket = new ServerSocket(16432);
					System.out.println("Server listening on port 16432");

					// loop (forever) until program is stopped
					while(true) {
						Socket client = socket.accept();
						new Thread()
						{
						    public void run() {
						    	PrintWriter output;
						    	try {
									output = new PrintWriter(client.getOutputStream(), true);
									String outString = arquivo.resposta();
									System.out.println("Enviando arquivo!");
									output.println(outString);
								} catch (IOException e) {
									e.printStackTrace();
								}
						    	this.interrupt();
						    }
						}.start();
					}
				}
				catch (IOException ioe){
					ioe.printStackTrace();
				}
		    }
		};
		thrd2.start();
		
	}
}

