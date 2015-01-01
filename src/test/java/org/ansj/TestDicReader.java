package org.ansj;

import java.io.BufferedReader;
import java.io.IOException;

import org.ansj.dic.DicReader;

public class TestDicReader {

	public static void main(String[] args) throws IOException {
		String path = "library/default.dic";
		
		BufferedReader br = DicReader.getReader(path);
		String line = null;
		if(null != br) {
			while(null != (line = br.readLine())) {
				System.out.println(line);
			}
		}
	}
}
