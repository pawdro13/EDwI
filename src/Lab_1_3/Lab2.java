
package Lab_1_3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lab2 {

	public static long startTime = 0;
	public static long endTime = 0;
	public static long duration = 0;

	static void ConvertToMap(int k, int TS) throws IOException {

		String inputLine = "";
		String all = " ";
		List<String> lineList = new ArrayList<String>();

		FileReader fileReader = new FileReader("C:\\Users\\HP\\Desktop\\SemWeb\\lab1\\Onet.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		while ((inputLine = bufferedReader.readLine()) != null) {
			lineList.add(inputLine);
		}

		fileReader.close();

		startTime = System.currentTimeMillis();

		for (String line : lineList) {
			all = all + line;
		}

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

		FileWriter fileWriter = new FileWriter("C:\\Users\\HP\\Desktop\\SemWeb\\lab1\\sort.txt");
		PrintWriter out = new PrintWriter(fileWriter);
		HashMap<String, Integer> filtermap = new HashMap<String, Integer>();

		countMap.entrySet().stream().sorted(HashMap.Entry.<String, Integer> comparingByValue().reversed()).limit(k)
				.filter(e -> e.getValue() > TS).forEachOrdered(e -> filtermap.put(e.getKey(), e.getValue()));

		Iterator it = filtermap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			out.println(pair.getKey() + " = " + pair.getValue());
			it.remove();
		}

		out.flush();
		out.close();
		fileWriter.close();
		endTime = System.currentTimeMillis();
		duration = (endTime - startTime);

	}

}
