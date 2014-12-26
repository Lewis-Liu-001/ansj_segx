package org.ansj.plugin.solr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.ansj.splitword.analysis.IndexAnalysis;
import org.ansj.splitword.analysis.ToAnalysis;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnsjTokenizerFactory extends TokenizerFactory {
	private static final Logger log = LoggerFactory
			.getLogger(AnsjTokenizerFactory.class);
	boolean isStemming;
	boolean isQuery;
	private String stopWordsDir;
	private String[] stopWords = new String[] { "'", ",", ".", "`", "?", "(",
			")", "{", "}", "[", "]", "<", ">", "*", "#", "&", "^", "!", ":",
			";", "《", "》", "，", "。", "、", "：", "；", "！", "·", "？", "“", "”",
			"）", "（", "【", "】", "●" };
	public Set<String> filter;

	public AnsjTokenizerFactory(Map<String, String> args) {
		super(args);
		assureMatchVersion();
		isQuery = getBoolean(args, "isQuery", true);
		isStemming = getBoolean(args, "isStemming", false);
		stopWordsDir = get(args, "stopWords");
		addStopwords(stopWordsDir);
	}

	// add stopwords list to filter
	private void addStopwords(String dir) {
		if (null == filter) {
			filter = new HashSet<String>();
		}
		Collections.addAll(filter, stopWords);
		if (dir == null) {
			log.warn("no stopwords file");
			return;
		}
		// read stoplist
		if (log.isInfoEnabled()) {
			log.info("stopwords: " + dir);
		}
		File file = new File(dir);
		InputStreamReader reader;
		try {
			reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			BufferedReader br = new BufferedReader(reader);
			String word = br.readLine();
			while (word != null) {
				filter.add(word);
				word = br.readLine();
			}
			br.close();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error("No stopword file found");
		} catch (IOException e) {
			e.printStackTrace();
			log.error("stopword file io exception");
		} 
	}

	@Override
	public Tokenizer create(AttributeFactory factory, Reader input) {
		if (isQuery == true) {
			return new AnsjTokenizer(new ToAnalysis(new BufferedReader(input)),
					input, filter, isStemming);
		} else {
			return new AnsjTokenizer(new IndexAnalysis(
					new BufferedReader(input)), input, filter, isStemming);
		}
	}
}
