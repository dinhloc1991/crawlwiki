package main;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import redis.clients.jedis.Jedis;
import util.Common;
import util.FileWriter;

public class MyCraller {
	public void getData(String seed_link, int depth) {
		// Lay toan bo duong link
		ArrayList<String> link_r_tong = new ArrayList<String>();
		ArrayList<String> link_r_cu = new ArrayList<String>();
		ArrayList<String> link_r_moi = new ArrayList<String>();
		ArrayList<String> link_tmp;
		link_r_cu.add(seed_link);

		for (int i = 0; i < depth; i++) {
			link_r_moi = new ArrayList();
			Common.print("--------Do sau " + i);
			for (String link : link_r_cu) {
				if (!link.startsWith("http")) {
					if (link.startsWith("/wiki")){
						link = "http://wikipedia.org/" + link;
					}
				}
				Common.print(link);
				link_tmp = getDataOneLink(link);
				link_r_moi.addAll(link_tmp);
			}

			link_r_cu = new ArrayList(link_r_moi);
		}

	}

	public ArrayList<String> getDataOneLink(String link) {
		JSONObject obj = new JSONObject();
		try {
			Document doc = Jsoup.connect(link).get();
			// FileWriter.write("result.txt", doc.html());
			Element short_content = doc.select("div#mw-content-text").get(0)
					.select("p").get(0);
			FileWriter.write("result.txt", short_content.html());
			String short_content_str = short_content.text();
			obj.put("short_content", short_content_str);
			ArrayList<String> keyWord_r = new ArrayList();
			ArrayList<String> link_r = new ArrayList();

			Elements keyWord_tag = short_content.select("a");
			for (Element element : keyWord_tag) {
				keyWord_r.add(element.text());
				String link_next_level = element.attr("href");
				if ((!link_next_level.contains("wikipedia")
						&& !link_next_level.contains("wiki"))
						|| link_next_level.contains("#cite_note")) {
					continue;
				}
				Common.print("++++++" + link_next_level);
				link_r.add(link_next_level);
			}
			obj.put("keyword", keyWord_r);
			StringWriter out = new StringWriter();
			obj.writeJSONString(out);
			String jsonText = out.toString();
			System.out.print(jsonText);
			useJedis(link, jsonText);

			// Elements link_tag = short_content.select("a[href]");
			//
			// for (Element element : link_tag) {
			// link_r.add(element.attr("href"));
			// }
			// Common.print(link_r);
			return link_r;
		} catch (Exception e) {

		}
		return new ArrayList();
	}

	public void useJedis(String key, String value) {
		Main.jedis.set(key, value);
	}
}
