import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainApp extends Thread {

	boolean running = true;
	List fileData;
	
	public List readFile ( String filePath ) throws IOException {
		
		FileReader frIn = new FileReader(filePath);
		BufferedReader br = new BufferedReader(frIn);
		fileData = new List();
		
		String line = "";
		String[] brokenLine;
		String urlFromFile = "";
		double srFromFile = 0.0;;
		double sfrFromFile = 0.0;
		
		while ( line != null ) {
			
			line =  br.readLine();
			
			if ( line != null ) {
				
				brokenLine = line.split(" ");
				if ( brokenLine.length == 3 ) {
					// File has correct parameters, we read it. If not, we skip line.
					urlFromFile = brokenLine[0];
					srFromFile = Double.parseDouble(brokenLine[1]);
					sfrFromFile = Double.parseDouble(brokenLine[2]);
					fileData.addNode(urlFromFile, srFromFile, sfrFromFile);
				}
				
	
			}
		}
		
		br.close();
		return fileData;
	}
	
	public void run () {
		
		int getResult;
			
		while ( running ) {
		
			Node aux = fileData.header;
			while ( aux != null ) {
				getResult = getRequest( aux.url, aux.sfr );
				aux.totalRequests++;
				
				if ( getResult == 2 ) {
					
					// SR
					aux.sfrResult++;
					aux.srResult++;
					
				} else if ( getResult == 1 ) {
					
					aux.srResult++;
					
				} 
				
				aux = aux.right;
			}
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
			
			System.out.println("Finished");
	
	}
	
	// Returns 1 on SR, 2 on SFR and 0 on FR ( Failed Request ).
	public int getRequest ( String urlString, double sfr ) {
		
		URL url;
		HttpURLConnection connection = null;
		
		try {
			
			long startTime = System.nanoTime();
			url = new URL(urlString);
			connection = ( HttpURLConnection ) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			int code = connection.getResponseCode();
			
			long endTime = System.nanoTime();
			long duration = ( endTime - startTime ) / 1000000; // Running time in ms
			if ( duration <= sfr ) {
				return 2;
			} else {
				if ( code >= 200 & code <= 499 ) {
					return 1;
				} else {
					return 0;
				}
				
			}
			
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		return -1;
		
	}
	
}
