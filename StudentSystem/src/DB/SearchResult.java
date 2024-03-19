package org.jdbc.DB;

public class SearchResult {

    int sid;
    String name;
    String classa;

    String subject;

    int score;

    int totalScore;

    int rankInClass;

    int rankInSchool;

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    String teacher;

    public int getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(int avgScore) {
        this.avgScore = avgScore;
    }

    int avgScore;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassa() {
        return classa;
    }

    public void setClassa(String classa) {
        this.classa = classa;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getRankInClass() {
        return rankInClass;
    }

    public void setRankInClass(int rankInClass) {
        this.rankInClass = rankInClass;
    }

    public int getRankInSchool() {
        return rankInSchool;
    }

    public void setRankInSchool(int rankInSchool) {
        this.rankInSchool = rankInSchool;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
