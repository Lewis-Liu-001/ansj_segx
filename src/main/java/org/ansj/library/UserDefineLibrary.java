package org.ansj.library;

import static org.ansj.util.MyStaticValue.LIBRARYLOG;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.ansj.dic.DicReader;
import org.ansj.lang.tire.domain.Forest;
import org.ansj.lang.tire.domain.Value;
import org.ansj.lang.tire.domain.WoodInterface;
import org.ansj.lang.tire.library.Library;
import org.ansj.library.dao.IAmbiguityWordDao;
import org.ansj.library.dao.IDictConfigDao;
import org.ansj.library.dao.IUserLibraryDao;
import org.ansj.library.dao.IUserWordDao;
import org.ansj.library.dao.entity.DictConfig;
import org.ansj.library.dao.entity.UserLibrary;
import org.ansj.library.dao.entity.UserWord;
import org.ansj.library.dao.impl.AmbiguityWordDao;
import org.ansj.library.dao.impl.DictConfigDao;
import org.ansj.library.dao.impl.UserLibraryDao;
import org.ansj.library.dao.impl.UserWordDao;
import org.ansj.util.IOUtil;
import org.ansj.util.MyStaticValue;
import org.ansj.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 用户自定义词典操作类
 * 
 * @author ansj
 */
public class UserDefineLibrary {
	private static final Logger log = LoggerFactory.getLogger(UserDefineLibrary.class);
	public static final String DEFAULT_NATURE = "userDefine";
	public static final Integer DEFAULT_FREQ = 1000;
	public static final String DEFAULT_FREQ_STR = "1000";
	public static final IDictConfigDao configDao = new DictConfigDao();
	public static final IAmbiguityWordDao ambiguityWordDao = new AmbiguityWordDao();
	public static final IUserWordDao userWordDao = new UserWordDao();
	public static final IUserLibraryDao userLibraryDao = new UserLibraryDao();
	public static Forest FOREST = null;
	public static Forest ambiguityForest = null;

	static {
		if("file".equals(MyStaticValue.userLibrarySource)) {
			//initUserLibrary();
			initUserLibraryFromFile();
			//initAmbiguityLibrary();
			initAmbiguityLibraryFromFile();
		} else if("mysql".equals(MyStaticValue.userLibrarySource)) {
			initUserWordFromMySQL();
			initUserLibraryFromMySQL();
			
			initAmbiguityLibraryFormMySQL();
		}
	}

	/**
	 * 关键词增加
	 * 
	 * @param keyWord
	 *            所要增加的关键词
	 * @param nature
	 *            关键词的词性
	 * @param freq
	 *            关键词的词频
	 */
	public static void insertWord(String keyword, String nature, int freq) {
		String[] paramers = new String[2];
		paramers[0] = nature;
		paramers[1] = String.valueOf(freq);
		Value value = new Value(keyword, paramers);
		Library.insertWord(FOREST, value);
	}

	/**
	 * 加载纠正词典
	 */
	private static void initAmbiguityLibrary() {
		// TODO Auto-generated method stub
		String ambiguityLibrary = MyStaticValue.ambiguityLibrary;
		if (StringUtil.isBlank(ambiguityLibrary)) {
			LIBRARYLOG.warning("init ambiguity  warning :" + ambiguityLibrary + " because : file not found or failed to read !");
			return;
		}
		ambiguityLibrary = MyStaticValue.ambiguityLibrary;
		File file = new File(ambiguityLibrary);
		if (file.isFile() && file.canRead()) {
			try {
				ambiguityForest = Library.makeForest(ambiguityLibrary);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LIBRARYLOG.warning("init ambiguity  error :" + new File(ambiguityLibrary).getAbsolutePath() + " because : not find that file or can not to read !");
				e.printStackTrace();
			}
			LIBRARYLOG.info("init ambiguityLibrary ok!");
		} else {
			LIBRARYLOG.warning("init ambiguity  warning :" + new File(ambiguityLibrary).getAbsolutePath() + " because : file not found or failed to read !");
		}
	}
	
	/**
	 * 加载纠正词典
	 */
	private static void initAmbiguityLibraryFromFile() {
		// TODO Auto-generated method stub
		String ambiguityLibrary = MyStaticValue.ambiguityLibrary;
		if (StringUtil.isBlank(ambiguityLibrary)) {
			LIBRARYLOG.warning("init ambiguity  warning :" + ambiguityLibrary + " because : file not found or failed to read !");
			return;
		}
		
		BufferedReader br = DicReader.getReader(ambiguityLibrary);
		if (null != br) {
			try {
				ambiguityForest = Library.makeForest(br);
			} catch (Exception e) {
				LIBRARYLOG.warning("init ambiguity  error :" + new File(ambiguityLibrary).getAbsolutePath() + " because : not find that file or can not to read !");
				e.printStackTrace();
			} finally {
				try {
					br.close();
				} catch (IOException e) {
					LIBRARYLOG.warning(e.getLocalizedMessage());
					e.printStackTrace();
				}
			}
			LIBRARYLOG.info("init ambiguityLibrary ok!");
		} else {
			LIBRARYLOG.warning("init ambiguity  warning :" + new File(ambiguityLibrary).getAbsolutePath() + " because : file not found or failed to read !");
		}
	}
	/**
	 * 加载纠正词典，from MySQL
	 */
	private static void initAmbiguityLibraryFormMySQL() {
		try {
			long start = System.currentTimeMillis();
			ambiguityForest = new Forest();
			List<Value> values = ambiguityWordDao.query();
			if(values == null || values.size() <= 0) {
				log.warn("岐义词典为空！");
				return;
			}
			for(Value value : values) {
				Library.insertWord(ambiguityForest, value);
			}
			long end = System.currentTimeMillis();
			if(log.isInfoEnabled()) {
				log.info("从MySQL加载岐义纠正词典共花费：" + (end - start) + "ms");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	/**
	 * 加载用户自定义词典和补充词典
	 */
	private static void initUserLibrary() {
		// TODO Auto-generated method stub
		try {
			FOREST = new Forest();
			// 加载用户自定义词典
			String userLibrary = MyStaticValue.userLibrary;
			long start = System.currentTimeMillis();
			loadLibrary(FOREST, userLibrary);
			long end = System.currentTimeMillis();
			if(log.isInfoEnabled()) {
				log.info("加载" + userLibrary + "文件共花费" + (end - start) + " ms");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

	}
	
	/**
	 * 加载用户自定义词典和补充词典
	 */
	private static void initUserLibraryFromFile() {
		// TODO Auto-generated method stub
		try {
			FOREST = new Forest();
			// 加载用户自定义词典
			String userLibrary = MyStaticValue.userLibrary;
			if (StringUtil.isBlank(userLibrary)) {
				LIBRARYLOG.warning("init user library  warning :" + userLibrary + " because : file name is empty or null!");
				return;
			}
			long start = System.currentTimeMillis();
			BufferedReader br = DicReader.getReader(userLibrary);
			loadFile(FOREST, br);
			long end = System.currentTimeMillis();
			if(log.isInfoEnabled()) {
				log.info("加载" + userLibrary + "文件共花费" + (end - start) + " ms");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

	}
	
	/**
	 * 加载用户自定义词典， from segment表
	 */
	private static void initUserWordFromMySQL() {
		try {
			long start = System.currentTimeMillis();
			FOREST = new Forest();
			List<UserWord> userWords = userWordDao.query();
			if(null == userWords || userWords.size() <= 0) {
				log.warn("用户自定义词典(segment)为空！");
				return;
			}
			Value tempValue = null;
			String keyword = null;
			for(UserWord uw : userWords) {
				keyword = uw.getKeyword();
				if(StringUtil.isBlank(keyword)) {
					log.warn("用户自定义词典(segment)异常!id=" + uw.getIdPk());
					continue;
				}
				keyword = keyword.toLowerCase();
				// 如何核心辞典存在那么就放弃
				if (MyStaticValue.isSkipUserDefine && DATDictionary.getId(keyword) > 0) {
					continue;
				}
				tempValue = new Value(keyword, uw.getSpeech(), uw.getWordFrequency() + "");
				Library.insertWord(FOREST, tempValue);
			}
			long end = System.currentTimeMillis();
			if(log.isInfoEnabled()) {
				log.info("从MySQL加载用户自定义词典(segment)共花费" + (end - start) + "ms");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
	/**
	 * 加载用户自定义词典， from user_library表
	 */
	private static void initUserLibraryFromMySQL() {
		try {
			long start = System.currentTimeMillis();
			FOREST = new Forest();
			List<UserLibrary> userLibrary = userLibraryDao.query();
			if(null == userLibrary || userLibrary.size() <= 0) {
				log.warn("用户自定义词典(user_library)为空！");
				return;
			}
			Value tempValue = null;
			String keyword = null;
			for(UserLibrary ul : userLibrary) {
				keyword = ul.getKeyword();
				if(StringUtil.isBlank(keyword)) {
					log.warn("用户自定义词典(user_library)有异常!id=" + ul.getIdPk());
					continue;
				}
				keyword = keyword.toLowerCase();
				// 如何核心辞典存在那么就放弃
				if (MyStaticValue.isSkipUserDefine && DATDictionary.getId(keyword) > 0) {
					continue;
				}
				tempValue = new Value(keyword, ul.getSpeech(), ul.getWordFrequency() + "");
				Library.insertWord(FOREST, tempValue);
			}
			long end = System.currentTimeMillis();
			if(log.isInfoEnabled()) {
				log.info("从MySQL加载用户自定义词典(user_library)共花费" + (end - start) + "ms");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	// 单个文件加载词典
	public static void loadFile(Forest forest, File file) {
		// TODO Auto-generated method stub
		if (!file.canRead()) {
			LIBRARYLOG.warning("file in path " + file.getAbsolutePath() + " can not to read!");
			return;
		}
		String temp = null;
		BufferedReader br = null;
		String[] strs = null;
		Value value = null;
		try {
			br = IOUtil.getReader(new FileInputStream(file), "UTF-8");
			while ((temp = br.readLine()) != null) {
				if (StringUtil.isBlank(temp)) {
					continue;
				} else {
					strs = temp.split("\t");

					strs[0] = strs[0].toLowerCase();

					// 如何核心辞典存在那么就放弃
					if (MyStaticValue.isSkipUserDefine && DATDictionary.getId(strs[0]) > 0) {
						continue;
					}

					if (strs.length != 3) {
						value = new Value(strs[0], DEFAULT_NATURE, DEFAULT_FREQ_STR);
					} else {
						value = new Value(strs[0], strs[1], strs[2]);
					}
					Library.insertWord(forest, value);
				}
			}
			LIBRARYLOG.info("init user userLibrary ok path is : " + file.getAbsolutePath());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			IOUtil.close(br);
			br = null;
		}
	}
	
	// 单个文件加载词典
		public static void loadFile(Forest forest, BufferedReader br) {
			if (null == br) {
				LIBRARYLOG.warning("init user library  error , because : not find that file or can not to read !");
				return;
			}
			String temp = null;
			String[] strs = null;
			Value value = null;
			try {
				while ((temp = br.readLine()) != null) {
					if (StringUtil.isBlank(temp)) {
						continue;
					} else {
						strs = temp.split("\t");

						strs[0] = strs[0].toLowerCase();

						// 如何核心辞典存在那么就放弃
						if (MyStaticValue.isSkipUserDefine && DATDictionary.getId(strs[0]) > 0) {
							continue;
						}

						if (strs.length != 3) {
							value = new Value(strs[0], DEFAULT_NATURE, DEFAULT_FREQ_STR);
						} else {
							value = new Value(strs[0], strs[1], strs[2]);
						}
						Library.insertWord(forest, value);
					}
				}
				LIBRARYLOG.info("init user userLibrary OK! ");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				IOUtil.close(br);
				br = null;
			}
		}

	/**
	 * 加载词典,传入一本词典的路径.或者目录.词典后缀必须为.dic
	 */
	public static void loadLibrary(Forest forest, String path) {
		// 加载用户自定义词典
		File file = null;
		if (path != null) {
			file = new File(path);
			if (!file.canRead() || file.isHidden()) {
				LIBRARYLOG.warning("init userLibrary  warning :" + new File(path).getAbsolutePath() + " because : file not found or failed to read !");
				return;
			}
			if (file.isFile()) {
				loadFile(forest, file);
			} else if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					if (files[i].getName().trim().endsWith(".dic")) {
						loadFile(forest, files[i]);
					}
				}
			} else {
				LIBRARYLOG.warning("init user library  error :" + new File(path).getAbsolutePath() + " because : not find that file !");
			}
		}
	}

	/**
	 * 删除关键词
	 */
	public static void removeWord(String word) {
		Library.removeWord(FOREST, word);
	}

	public static String[] getParams(String word) {
		WoodInterface temp = FOREST;
		for (int i = 0; i < word.length(); i++) {
			temp = temp.get(word.charAt(i));
			if (temp == null) {
				return null;
			}
		}
		if (temp.getStatus() > 1) {
			return temp.getParams();
		} else {
			return null;
		}
	}

	public static String[] getParams(Forest forest, String word) {
		WoodInterface temp = forest;
		for (int i = 0; i < word.length(); i++) {
			temp = temp.get(word.charAt(i));
			if (temp == null) {
				return null;
			}
		}
		if (temp.getStatus() > 1) {
			return temp.getParams();
		} else {
			return null;
		}
	}

	public static boolean contains(String word) {
		return getParams(word) != null;
	}

	/**
	 * 将用户自定义词典清空
	 */
	public static void clear() {
		FOREST.clear();
	}
	
	public synchronized static void reloadUserLibrary() {
		String userLibraryTable = MyStaticValue.userLibraryTable;
		if(StringUtil.isBlank(userLibraryTable)) {
			log.warn("userLibraryTable 值异常！");
			return;
		}
		DictConfig config = configDao.isUpdate(userLibraryTable);
		if(null != config) {
			try {
				long start = System.currentTimeMillis();
				if(null == FOREST) {
					FOREST = new Forest();
				}
				List<UserLibrary> userLibrary = userLibraryDao.query();
				if(null == userLibrary || userLibrary.size() <= 0) {
					log.warn("用户自定义词典(user_library)为空！");
					return;
				}
				Value tempValue = null;
				String keyword = null;
				for(UserLibrary ul : userLibrary) {
					keyword = ul.getKeyword();
					if(StringUtil.isBlank(keyword)) {
						log.warn("用户自定义词典(user_library)有异常!id=" + ul.getIdPk());
						continue;
					}
					keyword = keyword.toLowerCase();
					
					if (contains(keyword)) {
						continue;
					}
					tempValue = new Value(keyword, ul.getSpeech(), ul.getWordFrequency() + "");
					Library.insertWord(FOREST, tempValue);
				}
				long end = System.currentTimeMillis();
				if(log.isInfoEnabled()) {
					log.info("从MySQL更新用户自定义词典(user_library)共花费" + (end - start) + "ms");
				}
				updateDictConfig(config);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		} else {
			if(log.isInfoEnabled()) {
				log.info("用户自定义词典没有更新！");
			}
		}
	}
	
	public synchronized static void reloadAmbiguityWord() {
		String ambiguitySentenceTable = MyStaticValue.ambiguitySentenceTable;
		if(StringUtil.isBlank(ambiguitySentenceTable)) {
			log.warn("ambiguitySentenceTable 值异常！");
			return;
		}
		DictConfig config = configDao.isUpdate(ambiguitySentenceTable);
		if(null != config) {
			try {
				long start = System.currentTimeMillis();
				ambiguityForest = new Forest();
				List<Value> values = ambiguityWordDao.query();
				if(values == null || values.size() <= 0) {
					log.warn("岐义词典为空！");
					return;
				}
				for(Value value : values) {
					Library.insertWord(ambiguityForest, value);
				}
				long end = System.currentTimeMillis();
				if(log.isInfoEnabled()) {
					log.info("从MySQL加载岐义纠正词典共花费：" + (end - start) + "ms");
				}
				updateDictConfig(config);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		} else {
			if(log.isInfoEnabled()) {
				log.info("岐义词典没有更新！");
			}
		}
	}
	
	private static void updateDictConfig(DictConfig config) {
		if(null != config) {
			String operator = config.getOperator();
			if(StringUtil.isBlank(operator)) {
				config.setOperator(configDao.getMachineName());
				config.setIsUpdate(config.getIsUpdate() + 1);
			} else {
				operator = operator + "," + configDao.getMachineName();
				String[] operators = operator.split(",");
				config.setOperator(operator);
				if(config.getNodeNumber() == operators.length) {
					config.setIsUpdate(0);
				} else {
					config.setIsUpdate(config.getIsUpdate() + 1);
				}
			}
			config.setUpdateTime(new Date());
			configDao.update(config);
		}
	}

}
