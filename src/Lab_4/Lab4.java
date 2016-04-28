package Lab_4;

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
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Lab_1_3.Lab1;

public class Lab4 {

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

		String s = null, t;

		try {
			while ((t = urlIn.readLine()) != null) {
				s += t;
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

			if (links.get(i).contains("http")) {
				Filter.add(links.get(i));
			} else if (links.get(i).contains("/")) {
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

	static void replayScan(int times) {

		String temp;
		try {
			getLinks(URL);
		} catch (FileNotFoundException e) {
			System.out.println("Wyjatek");
		}

		for (int i = 0; i < times; i++) {
			System.out.println("PEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEETLA " + (i + 1));
			Copy.addAll(Main);
			for (String ref : Copy) {
				temp = ref;
				try {
					getLinks(temp);
				} catch (FileNotFoundException e) {
					System.out.println("Wyjatek");
				}

			}
			System.out.println("Petla:AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA " + (i + 1));
		}

	}

	public static String getadres() throws UnknownHostException, MalformedURLException {
		InetAddress address = InetAddress.getByName(new URL(URL).getHost());
		String adres = address.getHostAddress();
		return adres;
	}

}
