package org.ansj.library.dao.entity;

public class UserWord {
    private Long idPk;

    private String keyword;

    private String speech;

    private Integer wordFrequency;

    private Integer state;

    public Long getIdPk() {
        return idPk;
    }

    public void setIdPk(Long idPk) {
        this.idPk = idPk;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }

    public Integer getWordFrequency() {
        return wordFrequency;
    }

    public void setWordFrequency(Integer wordFrequency) {
        this.wordFrequency = wordFrequency;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}