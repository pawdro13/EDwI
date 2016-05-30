package Lab_7;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.jsoup.Jsoup;

public class Lab_7 {
	
	
	public static ArrayList<String> PageStart = new ArrayList<String>();
	public static ArrayList<String> PageTemp = new ArrayList<String>();
	public static String PageStartAll = "";
	static int counter=0;
	static ArrayList<String> Contain = new ArrayList<String>();
	
	
	//czyta zawartosc pliku txt
	static public String readTXT(String URL) throws IOException {

		try (BufferedReader br = new BufferedReader(new FileReader(URL))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String everything = sb.toString();
			return everything;
		}
	}
	
	////////////////////////////////////////Indeksowanie stron
	public static URL url;
	static Set<String> Main = new HashSet<String>();
	static ArrayList<String> Copy = new ArrayList<String>();
	static Set<String> Other = new HashSet<String>();
	static ArrayList<String> Filter = new ArrayList<String>();
	static String adres;
	static String URL = "http://forum.gazeta.pl/forum/0,0.html";

	public static void getLinks(String urll) throws FileNotFoundException {

		try {
			url = new URL(urll);
		} catch (MalformedURLException e1) {
			System.out.println("Wyjatek");
		}

		try {
			getadres();
		} catch (UnknownHostException e1) {
			System.out.println("Wyjatek");
		} catch (MalformedURLException e1) {
			System.out.println("Wyjatek");
		}

		BufferedReader urlIn = null;
		ArrayList<String> links = new ArrayList<String>();

		try {
			urlIn = new BufferedReader(new InputStreamReader(url.openStream()));
		} catch (IOException e1) {
			System.out.println("Wyjatek");
		}

		String s = "", t;

		try {
			while ((t = urlIn.readLine()) != null) {			
				s+= t;
				//System.out.println(t);
			}
		} catch (IOException e1) {
			System.out.println("Wyjatek");
		}
		{

		}

		String baseHREF = null;

		while (s.indexOf("<a href=") != -1) {
			links.add(s.substring(s.indexOf("<a href=") + 9,
					s.indexOf("<a href=") + 9
							+ s.substring(s.indexOf("<a href=") + 9).indexOf(
									(s.substring(s.indexOf("<a href=") + 8, s.indexOf("<a href=") + 9).equals("'"))
											? "'" : "\"")));
			s = s.substring(s.indexOf("<a href=") + 9 + s.substring(s.indexOf("<a href=") + 9).indexOf("\""));
		}

		String patern = "[.]*http[.]*";

		for (int i = 0; i < links.size(); i++) {

			if (links.get(i).contains("http") && links.get(i).contains("www")) {
				Filter.add(links.get(i));
			} else if (links.get(i).contains("/") && links.get(i).contains("www")) {
				links.set(i, URL + links.get(i));
				Filter.add(links.get(i));
			}
		}
		String ip = "";
		for (int i = 0; i < Filter.size(); i++) {
			// System.out.println(Filter.get(i));
			InetAddress addresss;
			try {
				addresss = InetAddress.getByName(new URL(Filter.get(i)).getHost());
				ip = addresss.getHostAddress();
			} catch (UnknownHostException | MalformedURLException e) {
				System.out.println("Wyjatek");
			}

			try {
				if (ip.equals(getadres())) {
					Main.add(Filter.get(i));
				} else {
					// System.out.println("Jestem tu");
					counter++;
					Other.add(Filter.get(i));
					
				}
			} catch (UnknownHostException e) {
				System.out.println("Wyjatek");
			} catch (MalformedURLException e) {
				System.out.println("Wyjatek");
			}
		}
		PrintWriter pw = new PrintWriter(new FileOutputStream("C:\\Users\\HP\\Desktop\\SemWeb\\lab4\\Main.txt"));
		for (String ref : Main)
			pw.println(ref);
		pw.close();

		PrintWriter pww = new PrintWriter(new FileOutputStream("C:\\Users\\HP\\Desktop\\SemWeb\\lab4\\Other.txt"));
		for (String ref : Other)
			pww.println(ref);
		pww.close();

	}

	static void replayScan(String page) {

		String temp;
		try {
			getLinks(page);
		} catch (FileNotFoundException e) {
			System.out.println("Wyjatek");
		}

		for (int i = 0; i < 1; i++) {
			//System.out.println("PEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEETLA " + (i + 1));
			Copy.addAll(Main);
			for (String ref : Copy) {
				temp = ref;
				try {
					getLinks(temp);
				} catch (FileNotFoundException e) {
					System.out.println("Wyjatek");
				}

			}
			//System.out.println("Petla:AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA " + (i + 1));
		}
		

	}

	public static String getadres() throws UnknownHostException, MalformedURLException {
		InetAddress address = InetAddress.getByName(new URL(URL).getHost());
		String adres = address.getHostAddress();
		return adres;
	}
	
	
	//////////////Pobieranie stronek//////////////////////////////////////
	
	public static Integer Counter = 0;
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

	public static void SaveTxt(String adres) throws IOException {
		//Counter++;
		URL url = new URL(adres);
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), "UTF-8"));

		//FileWriter fWriter = new FileWriter("C:\\Users\\HP\\Desktop\\SemWeb\\lab1\\Wiki"+Counter+".html");

		//BufferedWriter writer = new BufferedWriter(fWriter);

		//String inputLine;

		//while ((inputLine = in.readLine()) != null) {
		//	writer.write(inputLine);
		//	writer.newLine();

		//}
		//writer.close();
		//in.close();

		//FileReader reader = new FileReader("C:\\Users\\HP\\Desktop\\SemWeb\\lab1\\Wiki"+Counter+".html");

		List<String> patternList = Arrays.asList("\\,", "\\.", "\\!", "\\?", "\\(", "\\)", "\\-", "\\<", "\\>", "\\t+",
				"\\:");

	//	File file = new File("C:\\Users\\HP\\Desktop\\SemWeb\\lab1\\Wiki"+Counter+".txt");

		//if (!file.exists()) {
		//	file.createNewFile();
		//}

		//FileWriter fw = new FileWriter(file.getAbsoluteFile());
		//BufferedWriter bw = new BufferedWriter(fw);
				String all = RemoveTag(in).toLowerCase();

		for (String pattern : patternList) {
			all = all.replaceAll(pattern, " ");
		}
			//	bw.write(all);
			//	bw.close();
		
		Contain.add(all);
	}
	
	////////////////////Lucyna-baza danych////////////////////////////
	
	static String fultext = "";
	static String test = "";
	static ArrayList<String> Titles = new ArrayList<String>();

	static Set<String> wynyki = new HashSet<String>();
	static List<File> esen = new ArrayList<File>();

	static int counterchar = 0;
	static String[] args;
	static int straz = -1;
	public static IndexWriter w;
	static StandardAnalyzer analyzer;
	static Directory index;

	// konstruktor w ktorym inicjalizujemy niezbêdne zmienne
	Lab_7() throws IOException {
		analyzer = new StandardAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		index = new RAMDirectory();
		w = new IndexWriter(index, config);

	}

	// przelaruje po plikach, wyszukeju zipy i pobiera z nich zawartosc, wykorzystujac ponizej zaimplementowane funkcjie
	static void indeksowanie() throws IOException {
int counterr=0;
	
		if (counterr >= 0) {
			for (String tit : PageStart) {

				addDoc(w, tit, Integer.toString(counterr), Contain.get(counterr));
				System.out.println(tit);
				counterr++;

			}
		}

	}

	// obsluguje wyszukiwanie w zalesnosci od przekazanych parametrow oraz
	// wyswietlanie wynikow
	static String engain(String title, int wsk) throws IOException, ParseException {

		w.close();

		// 2. query

		String result = "";
		String querystr = title;

		Query q;
		if (wsk == 0) {

			q = new QueryParser("title", analyzer).parse(querystr);
		} else {
			q = new QueryParser("contein", analyzer).parse(querystr);

		}

		// 3. search
		int hitsPerPage = 1000;
		IndexReader reader = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopDocs docs = searcher.search(q, hitsPerPage);
		ScoreDoc[] hits = docs.scoreDocs;

		result = "";

		// 4. display results
		result = "Found " + hits.length + " hits." + "\n ISBN" + "\tTITLE" + "\n";
		Document d = searcher.doc(0);

		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;

			d = searcher.doc(docId);

			result = result + d.get("isbn") + "\t" + d.get("title") + "\n";
		}

		reader.close();
		return result;
	}

	// dodaje dane do naszej "dokumentowej bazy danych"
	private static void addDoc(IndexWriter w, String title, String isbn, String contein) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("title", title, Field.Store.YES));
		doc.add(new TextField("contein", contein, Field.Store.YES));

		doc.add(new StringField("isbn", isbn, Field.Store.YES));
		w.addDocument(doc);
	}

	// wyszukuje tytul w zawartosci pliku
	public static String countWord(String word, String file) throws FileNotFoundException {
		String nextToken = "";
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			nextToken = scanner.nextLine();
			if (nextToken.contains(word)) {
				return nextToken.replace("Title: ", "");
			}
		}
		return nextToken;
	}

	
	/////////////////glowna metoda/////////////////////////////////////////////////////////////////////////
	static void engine() throws IOException{
		
		
		//Wczytujemy adresy stron poczatkowych do arrayListy PageStart
		PageStartAll = readTXT("C://Users//HP//Desktop//SemWeb//Lab7//PageList.txt");
		String lines[] = PageStartAll.split("\n");
		for (int i = 0; i < lines.length; i++) {
			PageStart.add(lines[i]);
			PageTemp.add(lines[i]);
		}
	
	
	do{
		for(String page:PageTemp){
			replayScan(page);			
		}
	PageTemp.clear();	
	PageStartAll = readTXT("C:\\Users\\HP\\Desktop\\SemWeb\\lab4\\Other.txt");
	String lines2[] = PageStartAll.split("\n");
	
		for (int i = 0; i < lines2.length; i++) {
			//System.out.println("PAGE:" + lines2[i]);
			PageStart.add(lines2[i]);
			PageTemp.add(lines2[i]);
			if(PageStart.size()<18)
				break;
		}
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA	:"+PageStart.size());
		
	}while(PageStart.size()<18);
	
	for(String page:PageStart){
		SaveTxt(page);			
	}
	
	indeksowanie();

	}
}