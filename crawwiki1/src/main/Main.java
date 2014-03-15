package main;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import redis.clients.jedis.Jedis;
import util.Common;

public class Main {
	public static MyJedis jedis = new MyJedis(); 
	public static void main(String[] args){
		String seed_link;
		int depth; 
		seed_link = ""; //"http://vi.wikipedia.org/wiki/M%E1%BA%A1ng_ng%E1%BB%AF_ngh%C4%A9a";
		depth = 2;
		int chon;
	//	MyJedis jedis = 
		MyCraller craller = new MyCraller();
		while(true){
			Common.print("Chuong trinh crawl wiki---------\n" +
					"Chon cac tuy chon sau \n" +
					"1. Crawl \n" +
					"2. Export toan bo du lieu ra file\n" +
					"3. Xoa toan bo du lieu\n" +
					"4. Thoa khoi chuong trinh   ");
			chon = Common.getInputInt();
			switch(chon){
			case 1:
				Common.print("Nhap seed_link:");
				seed_link = Common.getInputStr();
				Common.print("Nhap depth:");
				depth = Common.getInputInt();
				craller.getData(seed_link, depth);
				break;
			case 2:
				jedis.showAll();
				break;
			case 3:
				jedis.deleteAll();
				break;
			case 4: break;		
			}
			if (chon==4) break;
		}
//		new MyCraller().getData(seed_link, depth);
	}

}
