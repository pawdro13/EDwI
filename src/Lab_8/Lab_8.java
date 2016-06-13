package Lab_8;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


import org.jsoup.Jsoup;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Lab_8 {
	
	ArrayList<String> kompin = new ArrayList<String>();
	public static ArrayList<String> PageStart = new ArrayList<String>();
	public static ArrayList<String> PageTemp = new ArrayList<String>();
	public static String PageStartAll = "";
	static int counter=0;
	static ArrayList<String> Contain = new ArrayList<String>();
	static String URL = "https://en.wikipedia.org/wiki/Cheerleading";
	
	public static HashSet<String> words = new HashSet<String>();
	

	

	public static String RemoveTag(Reader reader) throws IOException {

		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(reader);

		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		String textOnly = Jsoup.parse(sb.toString()).text();
		return textOnly;

	}
	public static void SaveHtml(String adres) throws IOException {
		
		URL url = new URL(adres);
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), "UTF-8"));

		FileWriter fWriter = new FileWriter("C:\\Users\\HP\\Desktop\\SemWeb\\lab8.html");

		BufferedWriter writer = new BufferedWriter(fWriter);

		String inputLine;

		while ((inputLine = in.readLine()) != null) {
			writer.write(inputLine);
			writer.newLine();

		}
		writer.close();
		in.close();

	}

	public static void SaveTxt(String adres) throws IOException {
		
		URL url = new URL(adres);
	
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), "UTF-8"));

	
		List<String> patternList = Arrays.asList("\\,", "\\.", "\\!", "\\?", "\\(", "\\)", "\\-", "\\<", "\\>", "\\t+",
				"\\:","\\n");

	
				String all = RemoveTag(in).toLowerCase();

		for (String pattern : patternList) {
			all = all.replaceAll(pattern, " ");
		}
			
		
		PageStartAll=all;
	}

	static String[] PositivWord; 
		static String[] NegativWord;
		static double[] PositivVal;
		static double[] NegativVal;
		static String calwyn="";

 	static public void badacz(int dl, int ilosc) throws UnirestException, IOException{
 		
 		PositivWord = new String[ilosc];
 		NegativWord = new String[ilosc];
 		PositivVal = new double[ilosc];
 		NegativVal = new double[ilosc];
 		int conP=0, conN=0;
 		String cal="";
 		
 		SaveTxt(URL);
 		
 		HttpResponse<String> calosc = Unirest.post("https://community-sentiment.p.mashape.com/text/")
 				.header("X-Mashape-Key", "OTSqaZ7HIXmshqV6RBnfi8KGCICmp1vZ9FRjsn2Kj2wHV9CeMJ")
 				.header("Content-Type", "application/x-www-form-urlencoded")
 				.header("Accept", "application/json")
 				.field("txt", PageStartAll)
 				.asString();
 		
 		cal=calosc.getBody().toString();
 
 		
 		if(cal.contains("Negative")){
 			calwyn="NEGATIVE";
	
 		}
 		else if (cal.contains("Positive")){
 			calwyn="POSITIVE";
 			}
 		else if(cal.contains("Neutral")){
 			calwyn="NETRUAL";
 		
 	 	}
 		
 	 String[] stronka = PageStartAll.split(" ");
 	 
 	for(String str:stronka){
 		words.add(str);
 	}
 		

 	for(String str:words){
 		
 		
 		if(str.length()==dl){
	HttpResponse<String> response = Unirest.post("https://community-sentiment.p.mashape.com/text/")
			.header("X-Mashape-Key", "OTSqaZ7HIXmshqV6RBnfi8KGCICmp1vZ9FRjsn2Kj2wHV9CeMJ")
			.header("Content-Type", "application/x-www-form-urlencoded")
			.header("Accept", "application/json")
			.field("txt", str)
			.asString();
	

	String wyrocznia2 ="";
	String wyrocznia ="";
	
	wyrocznia= response.getBody().toString();
	wyrocznia2= response.getBody().toString();

	wyrocznia=wyrocznia.replaceAll("\n", "");
	wyrocznia=wyrocznia.replaceAll(".*\"confidence\": \"", "");
	wyrocznia=wyrocznia.replaceAll("\",.*", "");
	
	double val = Double.parseDouble(wyrocznia);
	
	
	
	
	if(wyrocznia2.contains("Negative")){
		
		if (conN<ilosc){
			NegativWord[conN]=str;
			NegativVal[conN]=val;
			
			conN++;
		}
		else if(PositivVal[ilosc-1]<val){
			sort(NegativWord, NegativVal);
			NegativVal[0]=val;
			NegativWord[0]=str;
			conN++;
	
		}
	
		
	}
	else if (wyrocznia2.contains("Positive")){
		if (conP<ilosc){
			PositivWord[conP]=str;
			PositivVal[conP]=val;
		
			conP++;
			
			
		}
		else if(PositivVal[ilosc-1]<val){
			sort(PositivWord, PositivVal);
			PositivVal[0]=val;
			PositivWord[0]=str;
		
			conP++;
			int con=0;
			
		}
		
	}
	else if(wyrocznia2.contains("Neutral")){
	
	
 	}
 		}
 	}
 	sort(NegativWord, NegativVal);
 	sort(PositivWord, PositivVal); 
 	
 	}
 	
 	static void sort(String[] words, double[] values){
 		
 		int wczesniejsza = values.length - 1; // indeks pierwszej porownywanej liczby; wartosc poczatkowa
        int pozniejsza = wczesniejsza - 1; // indeks drugiej porownywanej liczby; wartosc poczatkowa

        double tmp;
        String tmp2;
        int tmp3;
        while (pozniejsza >= 0) // indeks drugiej porownywanej liczby musi byc nie mniejszy niz zero
        {
            if (values[pozniejsza] > values[wczesniejsza]) // jesli druga liczba jest wieksza niz pierwsza
            {
                tmp = values[pozniejsza];
               values[pozniejsza] = values[wczesniejsza]; // zamien wartosci
                values[wczesniejsza] = tmp;

                tmp2 = words[pozniejsza];
                words[pozniejsza] = words[wczesniejsza]; // zamien wartosci
                words[wczesniejsza] = tmp2;

                wczesniejsza = values.length - 1;  // zresetuj licznik
                pozniejsza = wczesniejsza - 1;   // zresetuj licznik
            } else // jesli druga liczba jest mniejsza lub rowna pierwszej, zmniejsz licznik o 1
            {
                wczesniejsza--;
                pozniejsza--;
            }
        }
      
 		
 		
 	}
	
	public static void main(String[] args) throws IOException, UnirestException {

		badacz(5,10);

	}

}
