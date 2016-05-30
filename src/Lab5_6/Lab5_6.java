package Lab5_6;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
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

public class Lab5_6 {
	static String fultext = "";
	static String test = "";
	static ArrayList<String> Titles = new ArrayList<String>();
	static ArrayList<String> Contain = new ArrayList<String>();
	static Set<String> wynyki = new HashSet<String>();
	static List<File> esen = new ArrayList<File>();
	static int counter = 0;
	static int counterchar = 0;
	static String[] args;
	static int straz = -1;
	public static IndexWriter w;
	static StandardAnalyzer analyzer;
	static Directory index;

	// konstruktor w ktorym inicjalizujemy niezbêdne zmienne
	Lab5_6() throws IOException {
		analyzer = new StandardAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		index = new RAMDirectory();
		w = new IndexWriter(index, config);

	}

	// przelaruje po plikach, wyszukeju zipy i pobiera z nich zawartosc, wykorzystujac ponizej zaimplementowane funkcjie
	static void indeksowanie() throws IOException {

		walk("G://1//0//0//");
		walk2();
		if (counter >= 0) {
			for (String tit : Titles) {

				addDoc(w, tit, Integer.toString(counter), Contain.get(counter));
				counter++;

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

	// wyszukje zipow w folderach i dodaje je do listy
	public static void walk(String path) throws IOException {

		File root = new File(path);
		File[] list = root.listFiles();
		Titles.clear();

		if (list == null)
			return;

		for (File f : list) {
			String temp = f.toString();
			if (f.isDirectory()) {
				walk(f.getAbsolutePath());

			} else if (temp.contains(".ZIP") && !temp.contains("_8") && !temp.contains("_H")) {
				esen.add(f.getAbsoluteFile());
			}
		}
	}

	// pobiera z zipow zawartosci i przydziela je do odpowiednich kontenerow,
	// wykorzystujac counWord oraz dokonuje filtracji niechcianych tresci

	public static void walk2() throws IOException {
		for (int i = 0; i < esen.size(); i++) {
		}
		for (int i = 0; i < esen.size(); i++) {

			ZipFile zipFile = new ZipFile(esen.get(i).toString());

			Enumeration<? extends ZipEntry> entries = zipFile.entries();

			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				InputStream stream = zipFile.getInputStream(entry);
				int content;
				int licznik = 0;
				while ((content = stream.read()) != -1 && counterchar < 5000) {

					char temp = (char) content;
					if (temp == '\n')
						licznik++;
					if (licznik == 7)
						fultext = "";

					fultext = fultext + (char) content;
					counterchar++;

				}
				Titles.add(countWord("Title", fultext));
				Contain.add(fultext);
				fultext = "";
				counterchar = 0;

			}
		}

	}

}
