package socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
	protected Socket client = null;
	private PrintWriter output;
	private BufferedReader input;
	private Arquivo arquivo;
	
	public ServerThread(Socket client, Arquivo arquivo) {
		this.client = client;
		this.arquivo = arquivo;
	}
	
	public void run() {
		System.out.print("Recebendo dados!");
		try {
			output = new PrintWriter(client.getOutputStream(), true);
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String entrada = input.readLine();
			entrada=entrada.replace("{", "");
			entrada=entrada.replace("}", "");
			entrada=entrada.replace(":", "");
			entrada=entrada.replace("\"", "");
			entrada=entrada.concat(",");
			arquivo.incluirFrase(entrada);
			String outString = "";
			output.println(outString);
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			try {
				client.close();
				this.interrupt();

			}
			catch (IOException e) {
				e.printStackTrace();	
			}			
		}
	}
}

