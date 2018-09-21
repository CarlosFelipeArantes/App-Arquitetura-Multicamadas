package socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Arquivo  implements Runnable {
	
	private File arquivo;
	private ArrayList<String> dados = new ArrayList<>();

	public Arquivo()  {
		super();
		arquivo = new File("arquivo.txt");
		this.carregar();
		new Thread (this).start();
	}
	
	public ArrayList<String> getDados() {
		return dados;
	}

	public void carregar()
	{
		
		String [] linhas;
		
		try
		{
			FileReader file = new FileReader(arquivo);
			BufferedReader br = new BufferedReader(file);
			String line = br.readLine();

			while (line!=null)
			{
				//linhas = line.split("-");
				
				dados.add(line);
				
				line = br.readLine();
				
			}			
			
			file.close();
		}
		catch (IOException e)
		{
			System.out.println(e.getLocalizedMessage());	
		}
		
	}


	public synchronized boolean incluirFrase(String frase)
	{
		if (this.dados.contains(frase)==false)
		{
			this.dados.add(frase);
			return true;
		}
		else 
		{
			return false;
		}

	}
 
	@Override
	public void run()
	{
		while(true) 
		 {
			try 
			{	
				TimeUnit.SECONDS.sleep(3);
				try
				{
					BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo,false));
					this.dados.forEach(	(value)-> {
													try
													{
													writer.write(value+"\n");
													} 
													catch (IOException e)
													{
													e.printStackTrace();
													}
					});
					
					writer.close();
				} 
				catch (Exception e)
				{
					e.getMessage();
				}
				
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}		
			
		 }
	}	
	
	public String resposta()
	{
		String resposta="";
		String linha="";
		String [] linhas;
		System.out.println(dados.size());
		for(int i = 0 ; i<this.dados.size() ; i++) {
			linhas = this.dados.get(i).split("-");
			linha = linha + "" + linhas[0];
			linha = linha + "-" + linhas[1];	
			linha = linha + "-" + linhas[2];	
			linha = linha + "-" + linhas[3];	
			linha = linha + "-" + linhas[4];	
			linha = linha + "-" + linhas[5];
			resposta = resposta + linha;
			linha="";
		}
		resposta=resposta.replace(",", "|");
		System.out.println(resposta);
		return resposta;
	}
}
