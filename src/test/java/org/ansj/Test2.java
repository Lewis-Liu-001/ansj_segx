package org.ansj;


import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.ansj.domain.Term;
import org.ansj.splitword.analysis.ToAnalysis;

public class Test2 {
	public static void main(String[] args) throws IOException {
		String str = "我觉得Ansj中文分词是一个不错的系统!我是王婆! 中华人民共和国万岁，人民万岁，毛泽东万岁。刘楠纺工作很认真！";
		List<Term> list = ToAnalysis.parse(str);
		
		System.out.println(list);
		
		System.in.read();
		
		list = ToAnalysis.parse(str);
		
		System.out.println(list);
		
		System.in.read();
		
		list = ToAnalysis.parse(str);
		
		System.out.println(list);
		
		Reader br = null;
		
		new ToAnalysis(br);
	}
}
