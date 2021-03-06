package main;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Set;

import redis.clients.jedis.Jedis;
import util.Common;

public class MyJedis {
	private Jedis jedis;

	public MyJedis() {
		jedis = new Jedis("localhost");
		jedis.connect();
	}

	public void set(String key, String value) {
		this.jedis.set(key, value);
	}

	public void showAll() {
		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(Constant.FILE_RESULT), "utf-8"));

			Set<String> keys = jedis.keys("*");
			String value;
			for (String key : keys) {
				// Common.print(key);
				value = jedis.get(key);
				writer.write(key+"\n");
				writer.write("--" + value+"\n");
				// Common.print(value);
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteAll(){
		jedis.flushAll();
	}
}
