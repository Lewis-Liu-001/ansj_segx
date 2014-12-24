package org.ansj.library.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ansj.lang.tire.domain.Value;
import org.ansj.library.dao.IAmbiguityWordDao;
import org.ansj.library.dao.entity.AmbiguitySentence;
import org.ansj.library.dao.entity.AmbiguitySentenceExample;
import org.ansj.library.dao.entity.AmbiguitySentenceWord;
import org.ansj.library.dao.entity.AmbiguitySentenceWordExample;
import org.ansj.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmbiguityWordDao implements IAmbiguityWordDao {
	private static final Logger log = LoggerFactory
			.getLogger(AmbiguityWordDao.class);
	private SqlSession session;

	@Override
	public List<Value> query() {
		List<Value> resultList = null;
		try {
			this.session = SqlSessionUtil.getSession(true);
			AmbiguitySentenceExample condition = new AmbiguitySentenceExample();
			AmbiguitySentenceExample.Criteria criteria = condition
					.createCriteria();
			criteria.andStateEqualTo(VALID_STATE);

			List<AmbiguitySentence> list = this.session.selectList(
					SENTENCE_NAMESPACE + ".selectByExample", condition);
			if (null == list || list.size() <= 0) {
				return resultList;
			}
			resultList = new ArrayList<Value>(list.size());
			Value value = null;
			AmbiguitySentenceWordExample wordCondition = null;
			for (AmbiguitySentence as : list) {
				wordCondition = new AmbiguitySentenceWordExample();
				AmbiguitySentenceWordExample.Criteria wordCriteria = wordCondition.createCriteria();
				wordCriteria.andStateEqualTo(VALID_STATE);
				wordCriteria.andSentenceIdEqualTo(as.getIdPk());
				List<AmbiguitySentenceWord> wordList = this.session.selectList(WORD_NAMESPACE + ".selectByExample", wordCondition);
				if(null == wordList || wordList.size() <= 0) {
					continue;
				}
				String[] temp = new String[wordList.size() * 2];
				for(int i = 0, j = 0; i < wordList.size(); i++, j++) {
					AmbiguitySentenceWord asw = wordList.get(i);
					if(j < temp.length) {
						temp[j] = asw.getWord();
						temp[++j] = asw.getSpeech();
					}
				}
				value = new Value(as.getSentence(), temp);
				resultList.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			SqlSessionUtil.closeSession(this.session);
		}

		return resultList;
	}

	@Override
	public List<Value> query(int start, int offset) {
		List<Value> resultList = null;
		try {
			this.session = SqlSessionUtil.getSession(true);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("state", VALID_STATE);
			params.put("start", start);
			params.put("offset", offset);
			List<AmbiguitySentence> list = this.session.selectList(
					SENTENCE_NAMESPACE + ".selectByPage", params);
			if (null == list || list.size() <= 0) {
				return resultList;
			}
			resultList = new ArrayList<Value>(list.size());
			Value value = null;
			AmbiguitySentenceWordExample wordCondition = null;
			for (AmbiguitySentence as : list) {
				wordCondition = new AmbiguitySentenceWordExample();
				AmbiguitySentenceWordExample.Criteria wordCriteria = wordCondition.createCriteria();
				wordCriteria.andStateEqualTo(VALID_STATE);
				wordCriteria.andSentenceIdEqualTo(as.getIdPk());
				List<AmbiguitySentenceWord> wordList = this.session.selectList(WORD_NAMESPACE + ".selectByExample", wordCondition);
				if(null == wordList || wordList.size() <= 0) {
					continue;
				}
				String[] temp = new String[wordList.size() * 2];
				for(int i = 0, j = 0; i < wordList.size(); i++, j++) {
					AmbiguitySentenceWord asw = wordList.get(i);
					if(j < temp.length) {
						temp[j] = asw.getWord();
						temp[++j] = asw.getSpeech();
					}
				}
				value = new Value(as.getSentence(), temp);
				resultList.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			SqlSessionUtil.closeSession(this.session);
		}

		return resultList;
	}

	@Override
	public int count() {
		int result = 0;
		try {
			this.session = SqlSessionUtil.getSession(true);
			AmbiguitySentenceExample condition = new AmbiguitySentenceExample();
			AmbiguitySentenceExample.Criteria criteria = condition
					.createCriteria();
			criteria.andStateEqualTo(VALID_STATE);
			result = this.session.selectOne(SENTENCE_NAMESPACE
					+ ".countByExample", condition);
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			SqlSessionUtil.closeSession(this.session);
		}
		return result;
	}

}
