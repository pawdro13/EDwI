package first;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Lab3{

	public static ArrayList <String> WikiPage = new ArrayList<String>();
	
	public static void setPage(){
		WikiPage.add("https://pl.wikipedia.org/wiki/Zoologia");
		WikiPage.add("https://pl.wikipedia.org/wiki/Rodow%C3%B3d");
		WikiPage.add("https://pl.wikipedia.org/wiki/Chrapy_(zoologia)");
		WikiPage.add("https://pl.wikipedia.org/wiki/Mikropyle_(zoologia)");
		WikiPage.add("https://pl.wikipedia.org/wiki/Fauna");
		
		WikiPage.add("https://pl.wikipedia.org/wiki/Koncert_(forma_muzyczna)");
		WikiPage.add("https://pl.wikipedia.org/wiki/Metal_neoklasyczny");
		WikiPage.add("https://pl.wikipedia.org/wiki/1998_w_muzyce");
		WikiPage.add("https://pl.wikipedia.org/wiki/1998_w_muzyce");
		WikiPage.add("https://pl.wikipedia.org/wiki/1998_w_muzyce");
		
		WikiPage.add("https://pl.wikipedia.org/wiki/Implementacja_(informatyka)");
		WikiPage.add("https://pl.wikipedia.org/wiki/Silnik_(informatyka)");
		WikiPage.add("https://pl.wikipedia.org/wiki/ATK_(informatyka)");
		WikiPage.add("https://pl.wikipedia.org/wiki/Informatyka_ekonomiczna");
		WikiPage.add("https://pl.wikipedia.org/wiki/Sk%C3%B3rka_(informatyka)");
			
	}
	
	
	public static void CreateVector() throws IOException{
		setPage();
		 Iterator<String> itr = WikiPage.iterator();
		    while (itr.hasNext()) {
		      String element = itr.next();
		      System.out.println(element);
		      Lab1.SaveHtml(element);
		      Lab1.SaveTxt();
		    }
		
	}

}
