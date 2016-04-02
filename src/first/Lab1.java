package first;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;

public class Lab1 {

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

	public static void SaveHtml(String adres) throws IOException {
		Counter++;
		URL url = new URL(adres);
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), "UTF-8"));

		FileWriter fWriter = new FileWriter("C:\\Users\\HP\\Desktop\\SemWeb\\lab1\\Wiki"+Counter+".html");

		BufferedWriter writer = new BufferedWriter(fWriter);

		String inputLine;

		while ((inputLine = in.readLine()) != null) {
			writer.write(inputLine);
			writer.newLine();

		}
		writer.close();
		in.close();

	}

	public static void SaveTxt() throws IOException {

		FileReader reader = new FileReader("C:\\Users\\HP\\Desktop\\SemWeb\\lab1\\Wiki"+Counter+".html");

		List<String> patternList = Arrays.asList("\\,", "\\.", "\\!", "\\?", "\\(", "\\)", "\\-", "\\<", "\\>", "\\t+",
				"\\:");

		File file = new File("C:\\Users\\HP\\Desktop\\SemWeb\\lab1\\Wiki"+Counter+".txt");

		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		String all = RemoveTag(reader).toLowerCase();

		for (String pattern : patternList) {
			all = all.replaceAll(pattern, " ");
		}
		bw.write(all);
		bw.close();
	}

}
