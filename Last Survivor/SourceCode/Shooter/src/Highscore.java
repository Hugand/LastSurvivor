//-------------------------------------------------
// Highscore: Where I load and save the highscore
//-------------------------------------------------

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;


public class Highscore {
	StringBuilder sb;
	BufferedReader br;
	String line;
	String content;
	public Highscore(){
		sb = new StringBuilder();
		br = null;
	}
	

	
	public int loadHigh(){ //Load the highscore
		
		try {
            
			br = new BufferedReader(new FileReader("highscore.txt")); //Read the highscore.txt file
			
			
            while ((line = br.readLine()) != null) {
                sb.append(line);
                System.out.println(line);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
		
		content = sb.toString();
		
		System.out.println(content);
		
		return Integer.parseInt(content);
		
		
	}
	
	public void saveHigh(int highscore){ //Save the highscore to the highscore.txt file
		
		try{
			PrintWriter writer = new PrintWriter("highscore.txt");
			writer.print(highscore);
			writer.close();
		}catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	
}
