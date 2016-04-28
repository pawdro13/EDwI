package Lab_1_3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class Lab3 {

	public static String AllPage = "";

	public static ArrayList<String> WikiPage = new ArrayList<String>();
	public static double tabwinvalue[] = new double[106];
	public static String tabmarks[] = new String[106];
	public static double tmp[];
	public static double temp[][] = new double[15][];
	public static String zwyciezcy = "";
	public static String przegrani = "";
	public static String nazwy = "";

	public static Map<String, Integer> MainMap = new HashMap();

	////////////////Wczytujemy strony//////////////////
	
	public static void setPage() {
		WikiPage.add("https://pl.wikipedia.org/wiki/Zoologia");
		WikiPage.add("https://pl.wikipedia.org/wiki/Rodow%C3%B3d");
		WikiPage.add("https://pl.wikipedia.org/wiki/Chrapy_(zoologia)");
		WikiPage.add("https://pl.wikipedia.org/wiki/Mikropyle_(zoologia)");
		WikiPage.add("https://pl.wikipedia.org/wiki/Fauna");

		WikiPage.add("https://pl.wikipedia.org/wiki/Koncert_(forma_muzyczna)");
		WikiPage.add("https://pl.wikipedia.org/wiki/Metal_neoklasyczny");
		WikiPage.add("https://pl.wikipedia.org/wiki/1999_w_muzyce");
		WikiPage.add("https://pl.wikipedia.org/wiki/1998_w_muzyce");
		WikiPage.add("https://pl.wikipedia.org/wiki/1996_w_muzyce");

		WikiPage.add("https://pl.wikipedia.org/wiki/Implementacja_(informatyka)");
		WikiPage.add("https://pl.wikipedia.org/wiki/Silnik_(informatyka)");
		WikiPage.add("https://pl.wikipedia.org/wiki/ATK_(informatyka)");
		WikiPage.add("https://pl.wikipedia.org/wiki/Informatyka_ekonomiczna");
		WikiPage.add("https://pl.wikipedia.org/wiki/Sk%C3%B3rka_(informatyka)");

		nazwy = "1. Zoologia \n2. Rodow%C3%B3d \n3. Chrapy_(zoologia) \n4. Mikropyle_(zoologia) \n"
				+ "5. Fauna \n6. Koncert_(forma_muzyczna \n7. Metal_neoklasyczny \n8. 1999_w_muzyce \n"
				+ "9. 1998_w_muzyce \n10. 1996_w_muzyce \n11. Implementacja_(informatyka \n12. Silnik_(informatyka)) \n"
				+ "13. ATK_(informatyka) \n14. Informatyka_ekonomiczna \n15. Sk%C3%B3rka_(informatyka)  \n";
	}

////////////////zliczamy slowa potrzebne do vektorow//////////////////
	
	public static void CreateVector() throws IOException {
		setPage();
		Iterator<String> itr = WikiPage.iterator();
		while (itr.hasNext()) {
			String element = itr.next();
			Lab1.SaveHtml(element);
			Lab1.SaveTxt();

			String inputLine = "";
			String all = " ";
			List<String> lineList = new ArrayList<String>();

			FileReader fileReader = new FileReader(
					"C:\\Users\\HP\\Desktop\\SemWeb\\lab1\\Wiki" + Lab1.Counter + ".txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((inputLine = bufferedReader.readLine()) != null) {
				lineList.add(inputLine);
			}

			fileReader.close();

			for (String line : lineList) {
				all = all + line;
			}
			AllPage = AllPage + all;
			String all2 = "";
			String[] strings = all.split(" ");

			Arrays.sort(strings);

			List<String> list = new ArrayList<String>();

			String REGEX = ".{1}";
			String REGEX2 = "\\s*";

			for (String line : strings) {
				if (!line.matches(REGEX)) {
					if (!line.matches(REGEX2)) {
						all2 = all2 + line;
						list.add(line);
					}
				}
			}

			Map<String, Integer> countMap = new HashMap();
			Set<String> unique = new HashSet<String>(list);

			for (String key : unique) {
				int accurNO = Collections.frequency(list, key);
				countMap.put(key, accurNO);

			}

			FileWriter fileWriter = new FileWriter(
					"C:\\Users\\HP\\Desktop\\SemWeb\\lab1\\Wiki_map" + Lab1.Counter + ".txt");
			PrintWriter out = new PrintWriter(fileWriter);
			Iterator it = countMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				out.println(pair.getKey() + " = " + pair.getValue());
				it.remove();
			}

			out.flush();
			out.close();
			fileWriter.close();
		}
		shortmap(AllPage);

	}

	
////////////////Tworzymy glowny wektor//////////////////
	
	
	public static void shortmap(String all) throws IOException {

		String all2 = "";
		String[] strings = all.split(" ");

		Arrays.sort(strings);

		List<String> list = new ArrayList<String>();

		String REGEX = ".{1}";
		String REGEX2 = "\\s*";

		for (String line : strings) {
			if (!line.matches(REGEX)) {
				if (!line.matches(REGEX2)) {
					all2 = all2 + line;
					list.add(line);
				}
			}
		}

		Set<String> unique = new HashSet<String>(list);

		for (String key : unique) {
			int accurNO = Collections.frequency(list, key);
			MainMap.put(key, accurNO);

		}

		createVector(MainMap);

		FileWriter fileWriter = new FileWriter("C:\\Users\\HP\\Desktop\\SemWeb\\lab1\\WikiALLMap+.txt");
		PrintWriter out = new PrintWriter(fileWriter);

		Iterator it = MainMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			out.println(pair.getKey() + " = " + pair.getValue());
			it.remove();
		}

		out.flush();
		out.close();
		fileWriter.close();
	}

	
////////////////Jeszcze raz przechodzimy po wektoch (hash mapah jeszcze) i tworzymy juz ostateczne wektory//////////////////
////////////////Wyliczamy cosinusy i wybietamy skrajne 10//////////////////
	
	public static void createVector(Map MainMap2) throws IOException {
		int another = 1;
		int licznik = 0;
		double temp[][] = new double[15][MainMap.size()];
		Iterator<String> itr = WikiPage.iterator();
		FileWriter fileWriter = new FileWriter("C:\\Users\\HP\\Desktop\\SemWeb\\lab1\\Vector.txt");
		PrintWriter out = new PrintWriter(fileWriter);
		while (itr.hasNext()) {

			String element = itr.next();
			String inputLine = "";
			String all = " ";
			List<String> lineList = new ArrayList<String>();

			FileReader fileReader = new FileReader("C:\\Users\\HP\\Desktop\\SemWeb\\lab1\\Wiki" + another + ".txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((inputLine = bufferedReader.readLine()) != null) {
				lineList.add(inputLine);
			}

			fileReader.close();

			for (String line : lineList) {
				all = all + line;
			}
			AllPage = AllPage + all;
			String all2 = "";
			String[] strings = all.split(" ");

			Arrays.sort(strings);

			List<String> list = new ArrayList<String>();

			String REGEX = ".{1}";
			String REGEX2 = "\\s*";

			for (String line : strings) {
				if (!line.matches(REGEX)) {
					if (!line.matches(REGEX2)) {
						all2 = all2 + line;
						list.add(line);
					}
				}
			}

			Map<String, Integer> countMap = new HashMap();
			Set<String> unique = new HashSet<String>(list);

			for (String key : unique) {
				int accurNO = Collections.frequency(list, key);
				countMap.put(key, accurNO);

			}

			boolean Zero = true;
			Iterator it = MainMap2.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				Iterator itt = countMap.entrySet().iterator();
				while (itt.hasNext()) {
					Map.Entry pairr = (Map.Entry) itt.next();
					if (pair.getKey().equals(pairr.getKey())) {
						int tempS = (int) pair.getValue();
						int tempM = (int) pairr.getValue();
						double tempp = (double) tempM / (double) tempS;
						temp[another - 1][licznik] = tempp;
						licznik++;
						Zero = false;
					}

				}
				if (Zero == true) {
					int tempS = (int) pair.getValue();
					int tempM = 0;
					double tempp = tempM / tempS;
					temp[another - 1][licznik] = tempp;
					licznik++;

				}
				Zero = true;
				}

			another++;
			licznik = 0;

		}

		out.flush();
		out.close();
		fileWriter.close();

		double result = 0, a = 0, b = 0, ab = 0, temp2 = 0, temp3 = 0, malywynik = 0;
		int con = 0;
		String mark = "";

		for (int i = 0; i < 15; i++) {
			for (int j = i + 1; j < 15; j++) {
			
				for (int d = 0; d < temp[i].length; d++) {
					temp2 = temp[i][d];
					temp3 = temp[j][d];
					malywynik = malywynik + temp2 * temp3;
					a = a + Math.pow(temp2, 2);
					b = b + Math.pow(temp3, 2);
					ab = Math.sqrt(a) * Math.sqrt(b);
					}

				result = malywynik / ab;
				tabwinvalue[con] = result;
				mark = (i + 1) + "-" + (j + 1) + "  cos: " + tabwinvalue[con];
				tabmarks[con] = mark;
				con++;
				temp2 = 0;
				temp3 = 0;
				a = 0;
				b = 0;
				ab = 0;
				malywynik = 0;

				mark = "";

			}
		}
		int odwroc = 1;
		sortowaniezwy(tabwinvalue, tabmarks);
		for (int t = tabwinvalue.length; t > 1; t--) {

			if (t > tabwinvalue.length - 10) {
				zwyciezcy = zwyciezcy + "\n " + tabmarks[t - 1];
			} else if (t < 12) {
				przegrani = przegrani + "\n " + tabmarks[odwroc];
				odwroc++;
			}

		}
		System.out.println(zwyciezcy);
	}

	
	////////////////Sortujemy wartosci cos w tablicy i ich znaczniki/////////////////
	public static void sortowaniezwy(double[] tablicaWygranych, String[] TablicaZnacznikow) {

		int wczesniejsza = tablicaWygranych.length - 1; 

		int pozniejsza = wczesniejsza - 1; 

		double tmp;
		String tmp2;
		String tmp3;
		while (pozniejsza >= 0) 
		{
			if (tablicaWygranych[pozniejsza] > tablicaWygranych[wczesniejsza]) {
				tmp = tablicaWygranych[pozniejsza];
				tablicaWygranych[pozniejsza] = tablicaWygranych[wczesniejsza]; 
				tablicaWygranych[wczesniejsza] = tmp;

				tmp2 = TablicaZnacznikow[pozniejsza];
				TablicaZnacznikow[pozniejsza] = TablicaZnacznikow[wczesniejsza]; 
				TablicaZnacznikow[wczesniejsza] = tmp2;

				wczesniejsza = tablicaWygranych.length - 1; 
				pozniejsza = wczesniejsza - 1; 
			} else 
			{
				wczesniejsza--;
				pozniejsza--;
			}
		}
	}

}
