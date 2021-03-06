import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class FridayFindCountry {
	public static String continent_id = "";

	public static ArrayList<String> continentid = new ArrayList<String>();
	public static ArrayList<String> continent_name = new ArrayList<String>();
	public static ArrayList<String> continent_url = new ArrayList<String>();

	public static String only_page = "";

	public static int country_id = 1;

	public static ArrayList<String> country_name = new ArrayList<String>();
	public static ArrayList<String> country_url = new ArrayList<String>();

	public static int num_country = 0;

	public static String read_page = "";

	static String InputFile = "/Users/frank/Desktop/continentinput2.txt";
	static String OutputFilename = "/Users/frank/Desktop/countryoutput2.txt";
	private static PrintWriter OutputFile;

	public static void main(String[] args) throws IOException {

		FridayFindCountry main = new FridayFindCountry();

		java.io.InputStream stream = null;
		try {
			stream = new FileInputStream(InputFile);
			BufferedReader in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

			main.readFilename(in); // read file
			OutputFile = new PrintWriter(new FileWriter(OutputFilename));
			OutputFile.printf("id\tcountry_id\tname\turl\t%n");

			stream.close();
		} catch (FileNotFoundException e) {
			System.out.println("No more files");
			OutputFile.close();

		}

		for (int i = 0; i < continentid.size(); i++) {

			continent_id = continentid.get(i);
			only_page = continent_url.get(i);

			//System.out.println(continentid.get(i) + "\t" + continent_name.get(i));

			// System.out.println(current_page);
			Document read = Jsoup.connect(only_page).userAgent("fml").get();
			main.readHTML(read);

			main.printWords();

			main.reset();

		}

		OutputFile.close();
		System.out.println("End of File");

	}

	// ---------------------------------------------------------------

	private void readFilename(BufferedReader input) throws IOException {

		String line = input.readLine();
		char[] parse = { '	' };
		String delims = new String(parse);

		while (line != null) {
			String[] lineWords = line.split(delims);
			//System.out.println(lineWords[0]);
			continentid.add(lineWords[0]);
			continent_name.add(lineWords[1]);
			continent_url.add(lineWords[2]);

			line = input.readLine();

		}

	}

	private void reset() {
		continent_id = "";
		num_country = 0;
		country_name.clear();
		country_url.clear();
		only_page = "";

	}

	private void readHTML(Document read) throws IOException {
		
		
		Elements country_list = read.select("div.tabberTab").select("ul").select("li");
		
		Elements testing = read.select("div.tabberTab");
		System.out.println(testing.get(1).text());
		
		for (int i = 0; i < country_list.size(); i++) {
	
			country_name.add(country_list.get(i).select("a").attr("alt"));
			//country_url.add("http://www.worldtravelguide.net/" + country_list.get(i).select("a").attr("alt"));
			country_name.removeAll(Arrays.asList(null, ""));
			//country_url.removeAll(Arrays.asList(null, ""));
			//System.out.println(country_list.get(i).select("a").text());
			//System.out.println(country_list.get(i).select("a").attr("href"));
		}

		num_country = country_name.size();

	}

	private void printWords() {

		for (int i = 0; i < country_name.size(); i++) {
			OutputFile.printf("%s\t%s\t%s%n", country_id, continent_id, country_name.get(i));
			country_id++;
		}

	}

}
