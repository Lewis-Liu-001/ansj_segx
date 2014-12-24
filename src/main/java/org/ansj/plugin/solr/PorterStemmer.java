package org.ansj.plugin.solr;

import static org.apache.lucene.util.RamUsageEstimator.NUM_BYTES_CHAR;
import org.apache.lucene.util.ArrayUtil;

public class PorterStemmer {
	private char[] b;
	private int i, /* offset into b */
	j, k, k0;
	private boolean dirty = false;
	private static final int INITIAL_SIZE = 50;

	public PorterStemmer() {
		b = new char[INITIAL_SIZE];
		i = 0;
	}
	
	public void reset() {
		i = 0;
		dirty = false;
	}
	
	public void add(char ch) {
		if (b.length <= i) {
			b = ArrayUtil.grow(b, i + 1);
		}
		b[i++] = ch;
	}
	
	@Override
	public String toString() {
		return new String(b, 0, i);
	}
	
	public int getResultLength() {
		return i;
	}
	
	public char[] getResultBuffer() {
		return b;
	}

	
	private final boolean cons(int i) {
		switch (b[i]) {
		case 'a':
		case 'e':
		case 'i':
		case 'o':
		case 'u':
			return false;
		case 'y':
			return (i == k0) ? true : !cons(i - 1);
		default:
			return true;
		}
	}

	
	private final int m() {
		int n = 0;
		int i = k0;
		while (true) {
			if (i > j)
				return n;
			if (!cons(i))
				break;
			i++;
		}
		i++;
		while (true) {
			while (true) {
				if (i > j)
					return n;
				if (cons(i))
					break;
				i++;
			}
			i++;
			n++;
			while (true) {
				if (i > j)
					return n;
				if (!cons(i))
					break;
				i++;
			}
			i++;
		}
	}

	
	private final boolean vowelinstem() {
		int i;
		for (i = k0; i <= j; i++)
			if (!cons(i))
				return true;
		return false;
	}

	
	private final boolean doublec(int j) {
		if (j < k0 + 1)
			return false;
		if (b[j] != b[j - 1])
			return false;
		return cons(j);
	}

	
	private final boolean cvc(int i) {
		if (i < k0 + 2 || !cons(i) || cons(i - 1) || !cons(i - 2))
			return false;
		else {
			int ch = b[i];
			if (ch == 'w' || ch == 'x' || ch == 'y')
				return false;
		}
		return true;
	}

	private final boolean ends(String s) {
		int l = s.length();
		int o = k - l + 1;
		if (o < k0)
			return false;
		for (int i = 0; i < l; i++)
			if (b[o + i] != s.charAt(i))
				return false;
		j = k - l;
		return true;
	}

	void setto(String s) {
		int l = s.length();
		int o = j + 1;
		for (int i = 0; i < l; i++)
			b[o + i] = s.charAt(i);
		k = j + l;
		dirty = true;
	}

	
	void r(String s) {
		if (m() > 0)
			setto(s);
	}

	
	private final void step1() {
		if (b[k] == 's') {
			if (ends("sses"))
				k -= 2;
			else if (ends("ies"))
				setto("i");
			else if (b[k - 1] != 's')
				k--;
		}
		if (ends("eed")) {
			if (m() > 0)
				k--;
		} else if ((ends("ed") || ends("ing")) && vowelinstem()) {
			k = j;
			if (ends("at"))
				setto("ate");
			else if (ends("bl"))
				setto("ble");
			else if (ends("iz"))
				setto("ize");
			else if (doublec(k)) {
				int ch = b[k--];
				if (ch == 'l' || ch == 's' || ch == 'z')
					k++;
			} else if (m() == 1 && cvc(k))
				setto("e");
		}
	}

	
	private final void step2() {
		if (ends("y") && vowelinstem()) {
			b[k] = 'i';
			dirty = true;
		}
	}

	
	private final void step3() {
		if (k == k0)
			return; /* For Bug 1 */
		switch (b[k - 1]) {
		case 'a':
			if (ends("ational")) {
				r("ate");
				break;
			}
			if (ends("tional")) {
				r("tion");
				break;
			}
			break;
		case 'c':
			if (ends("enci")) {
				r("ence");
				break;
			}
			if (ends("anci")) {
				r("ance");
				break;
			}
			break;
		case 'e':
			if (ends("izer")) {
				r("ize");
				break;
			}
			break;
		case 'l':
			if (ends("bli")) {
				r("ble");
				break;
			}
			if (ends("alli")) {
				r("al");
				break;
			}
			if (ends("entli")) {
				r("ent");
				break;
			}
			if (ends("eli")) {
				r("e");
				break;
			}
			if (ends("ousli")) {
				r("ous");
				break;
			}
			break;
		case 'o':
			if (ends("ization")) {
				r("ize");
				break;
			}
			if (ends("ation")) {
				r("ate");
				break;
			}
			if (ends("ator")) {
				r("ate");
				break;
			}
			break;
		case 's':
			if (ends("alism")) {
				r("al");
				break;
			}
			if (ends("iveness")) {
				r("ive");
				break;
			}
			if (ends("fulness")) {
				r("ful");
				break;
			}
			if (ends("ousness")) {
				r("ous");
				break;
			}
			break;
		case 't':
			if (ends("aliti")) {
				r("al");
				break;
			}
			if (ends("iviti")) {
				r("ive");
				break;
			}
			if (ends("biliti")) {
				r("ble");
				break;
			}
			break;
		case 'g':
			if (ends("logi")) {
				r("log");
				break;
			}
		}
	}

	
	private final void step4() {
		switch (b[k]) {
		case 'e':
			if (ends("icate")) {
				r("ic");
				break;
			}
			if (ends("ative")) {
				r("");
				break;
			}
			if (ends("alize")) {
				r("al");
				break;
			}
			break;
		case 'i':
			if (ends("iciti")) {
				r("ic");
				break;
			}
			break;
		case 'l':
			if (ends("ical")) {
				r("ic");
				break;
			}
			if (ends("ful")) {
				r("");
				break;
			}
			break;
		case 's':
			if (ends("ness")) {
				r("");
				break;
			}
			break;
		}
	}

	

	private final void step5() {
		if (k == k0)
			return; /* for Bug 1 */
		switch (b[k - 1]) {
		case 'a':
			if (ends("al"))
				break;
			return;
		case 'c':
			if (ends("ance"))
				break;
			if (ends("ence"))
				break;
			return;
		case 'e':
			if (ends("er"))
				break;
			return;
		case 'i':
			if (ends("ic"))
				break;
			return;
		case 'l':
			if (ends("able"))
				break;
			if (ends("ible"))
				break;
			return;
		case 'n':
			if (ends("ant"))
				break;
			if (ends("ement"))
				break;
			if (ends("ment"))
				break;
			/* element etc. not stripped before the m */
			if (ends("ent"))
				break;
			return;
		case 'o':
			if (ends("ion") && j >= 0 && (b[j] == 's' || b[j] == 't'))
				break;
			/* j >= 0 fixes Bug 2 */
			if (ends("ou"))
				break;
			return;
			/* takes care of -ous */
		case 's':
			if (ends("ism"))
				break;
			return;
		case 't':
			if (ends("ate"))
				break;
			if (ends("iti"))
				break;
			return;
		case 'u':
			if (ends("ous"))
				break;
			return;
		case 'v':
			if (ends("ive"))
				break;
			return;
		case 'z':
			if (ends("ize"))
				break;
			return;
		default:
			return;
		}
		if (m() > 1)
			k = j;
	}
	
	private final void step6() {
		j = k;
		if (b[k] == 'e') {
			int a = m();
			if (a > 1 || a == 1 && !cvc(k - 1))
				k--;
		}
		if (b[k] == 'l' && doublec(k) && m() > 1)
			k--;
	}

	
	public String stem(String s) {
		if (stem(s.toCharArray(), s.length()))
			return toString();
		else
			return s;
	}

	public boolean stem(char[] word) {
		return stem(word, word.length);
	}

	public boolean stem(char[] wordBuffer, int offset, int wordLen) {
		reset();
		if (b.length < wordLen) {
			b = new char[ArrayUtil.oversize(wordLen, NUM_BYTES_CHAR)];
		}
		System.arraycopy(wordBuffer, offset, b, 0, wordLen);
		i = wordLen;
		return stem(0);
	}

	
	public boolean stem(char[] word, int wordLen) {
		return stem(word, 0, wordLen);
	}

	
	public boolean stem() {
		return stem(0);
	}

	public boolean stem(int i0) {
		k = i - 1;
		k0 = i0;
		if (k > k0 + 1) {
			step1();
			step2();
			step3();
			step4();
			step5();
			step6();
		}
		
		if (i != k + 1)
			dirty = true;
		i = k + 1;
		return dirty;
	}

	
}
