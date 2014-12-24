package org.ansj.plugin.solr;

import java.io.IOException;
import java.io.Reader;
import java.util.Set;

import org.ansj.domain.Term;
import org.ansj.domain.TermNatures;
import org.ansj.splitword.Analysis;
import org.ansj.util.AnsjReader;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

public final class AnsjTokenizer extends Tokenizer {
	// 当前词
	private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
	// 偏移量
	private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
	// 距离
	private final PositionIncrementAttribute positionAttr = addAttribute(PositionIncrementAttribute.class);
	private final PorterStemmer stemmer = new PorterStemmer();
	protected Analysis analysis = null;
	private Set<String> filter;
	private boolean pstemming;

	public AnsjTokenizer(Analysis analysis, Reader input, Set<String> filter,
			boolean pstemming) {
		super(input);
		this.analysis = analysis;
		this.filter = filter;
		this.pstemming = pstemming;
	}

	@Override
	public boolean incrementToken() throws IOException {
		// TODO Auto-generated method stub
		clearAttributes();
		int position = 0;
		Term term = null;
		String name = null;
		int length = 0;
		boolean flag = true;
		do {
			term = analysis.next();
			if (term == null) {
				break;
			}
			name = term.getName();
			length = name.length();
			if (pstemming && term.termNatures() == TermNatures.EN) {
				name = stemmer.stem(name);
				term.setName(name);
			}

			if (filter != null && filter.contains(name)) {
				continue;
			} else {
				position++;
				flag = false;
			}
		} while (flag);

		if (term != null) {
			positionAttr.setPositionIncrement(position);
			termAtt.setEmpty().append(term.getName());
			offsetAtt.setOffset(term.getOffe(), term.getOffe() + length);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void reset() throws IOException {
		super.reset();
		analysis.resetContent(new AnsjReader(this.input));
	}

}
