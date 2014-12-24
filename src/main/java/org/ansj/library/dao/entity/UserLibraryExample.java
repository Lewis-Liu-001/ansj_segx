package org.ansj.library.dao.entity;

import java.util.ArrayList;
import java.util.List;

public class UserLibraryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserLibraryExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdPkIsNull() {
            addCriterion("id_pk is null");
            return (Criteria) this;
        }

        public Criteria andIdPkIsNotNull() {
            addCriterion("id_pk is not null");
            return (Criteria) this;
        }

        public Criteria andIdPkEqualTo(Long value) {
            addCriterion("id_pk =", value, "idPk");
            return (Criteria) this;
        }

        public Criteria andIdPkNotEqualTo(Long value) {
            addCriterion("id_pk <>", value, "idPk");
            return (Criteria) this;
        }

        public Criteria andIdPkGreaterThan(Long value) {
            addCriterion("id_pk >", value, "idPk");
            return (Criteria) this;
        }

        public Criteria andIdPkGreaterThanOrEqualTo(Long value) {
            addCriterion("id_pk >=", value, "idPk");
            return (Criteria) this;
        }

        public Criteria andIdPkLessThan(Long value) {
            addCriterion("id_pk <", value, "idPk");
            return (Criteria) this;
        }

        public Criteria andIdPkLessThanOrEqualTo(Long value) {
            addCriterion("id_pk <=", value, "idPk");
            return (Criteria) this;
        }

        public Criteria andIdPkIn(List<Long> values) {
            addCriterion("id_pk in", values, "idPk");
            return (Criteria) this;
        }

        public Criteria andIdPkNotIn(List<Long> values) {
            addCriterion("id_pk not in", values, "idPk");
            return (Criteria) this;
        }

        public Criteria andIdPkBetween(Long value1, Long value2) {
            addCriterion("id_pk between", value1, value2, "idPk");
            return (Criteria) this;
        }

        public Criteria andIdPkNotBetween(Long value1, Long value2) {
            addCriterion("id_pk not between", value1, value2, "idPk");
            return (Criteria) this;
        }

        public Criteria andKeywordIsNull() {
            addCriterion("keyword is null");
            return (Criteria) this;
        }

        public Criteria andKeywordIsNotNull() {
            addCriterion("keyword is not null");
            return (Criteria) this;
        }

        public Criteria andKeywordEqualTo(String value) {
            addCriterion("keyword =", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordNotEqualTo(String value) {
            addCriterion("keyword <>", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordGreaterThan(String value) {
            addCriterion("keyword >", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordGreaterThanOrEqualTo(String value) {
            addCriterion("keyword >=", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordLessThan(String value) {
            addCriterion("keyword <", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordLessThanOrEqualTo(String value) {
            addCriterion("keyword <=", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordLike(String value) {
            addCriterion("keyword like", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordNotLike(String value) {
            addCriterion("keyword not like", value, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordIn(List<String> values) {
            addCriterion("keyword in", values, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordNotIn(List<String> values) {
            addCriterion("keyword not in", values, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordBetween(String value1, String value2) {
            addCriterion("keyword between", value1, value2, "keyword");
            return (Criteria) this;
        }

        public Criteria andKeywordNotBetween(String value1, String value2) {
            addCriterion("keyword not between", value1, value2, "keyword");
            return (Criteria) this;
        }

        public Criteria andSpeechIsNull() {
            addCriterion("speech is null");
            return (Criteria) this;
        }

        public Criteria andSpeechIsNotNull() {
            addCriterion("speech is not null");
            return (Criteria) this;
        }

        public Criteria andSpeechEqualTo(String value) {
            addCriterion("speech =", value, "speech");
            return (Criteria) this;
        }

        public Criteria andSpeechNotEqualTo(String value) {
            addCriterion("speech <>", value, "speech");
            return (Criteria) this;
        }

        public Criteria andSpeechGreaterThan(String value) {
            addCriterion("speech >", value, "speech");
            return (Criteria) this;
        }

        public Criteria andSpeechGreaterThanOrEqualTo(String value) {
            addCriterion("speech >=", value, "speech");
            return (Criteria) this;
        }

        public Criteria andSpeechLessThan(String value) {
            addCriterion("speech <", value, "speech");
            return (Criteria) this;
        }

        public Criteria andSpeechLessThanOrEqualTo(String value) {
            addCriterion("speech <=", value, "speech");
            return (Criteria) this;
        }

        public Criteria andSpeechLike(String value) {
            addCriterion("speech like", value, "speech");
            return (Criteria) this;
        }

        public Criteria andSpeechNotLike(String value) {
            addCriterion("speech not like", value, "speech");
            return (Criteria) this;
        }

        public Criteria andSpeechIn(List<String> values) {
            addCriterion("speech in", values, "speech");
            return (Criteria) this;
        }

        public Criteria andSpeechNotIn(List<String> values) {
            addCriterion("speech not in", values, "speech");
            return (Criteria) this;
        }

        public Criteria andSpeechBetween(String value1, String value2) {
            addCriterion("speech between", value1, value2, "speech");
            return (Criteria) this;
        }

        public Criteria andSpeechNotBetween(String value1, String value2) {
            addCriterion("speech not between", value1, value2, "speech");
            return (Criteria) this;
        }

        public Criteria andWordFrequencyIsNull() {
            addCriterion("word_frequency is null");
            return (Criteria) this;
        }

        public Criteria andWordFrequencyIsNotNull() {
            addCriterion("word_frequency is not null");
            return (Criteria) this;
        }

        public Criteria andWordFrequencyEqualTo(Integer value) {
            addCriterion("word_frequency =", value, "wordFrequency");
            return (Criteria) this;
        }

        public Criteria andWordFrequencyNotEqualTo(Integer value) {
            addCriterion("word_frequency <>", value, "wordFrequency");
            return (Criteria) this;
        }

        public Criteria andWordFrequencyGreaterThan(Integer value) {
            addCriterion("word_frequency >", value, "wordFrequency");
            return (Criteria) this;
        }

        public Criteria andWordFrequencyGreaterThanOrEqualTo(Integer value) {
            addCriterion("word_frequency >=", value, "wordFrequency");
            return (Criteria) this;
        }

        public Criteria andWordFrequencyLessThan(Integer value) {
            addCriterion("word_frequency <", value, "wordFrequency");
            return (Criteria) this;
        }

        public Criteria andWordFrequencyLessThanOrEqualTo(Integer value) {
            addCriterion("word_frequency <=", value, "wordFrequency");
            return (Criteria) this;
        }

        public Criteria andWordFrequencyIn(List<Integer> values) {
            addCriterion("word_frequency in", values, "wordFrequency");
            return (Criteria) this;
        }

        public Criteria andWordFrequencyNotIn(List<Integer> values) {
            addCriterion("word_frequency not in", values, "wordFrequency");
            return (Criteria) this;
        }

        public Criteria andWordFrequencyBetween(Integer value1, Integer value2) {
            addCriterion("word_frequency between", value1, value2, "wordFrequency");
            return (Criteria) this;
        }

        public Criteria andWordFrequencyNotBetween(Integer value1, Integer value2) {
            addCriterion("word_frequency not between", value1, value2, "wordFrequency");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Integer value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Integer value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Integer value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Integer value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Integer value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Integer> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Integer> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Integer value1, Integer value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Integer value1, Integer value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}