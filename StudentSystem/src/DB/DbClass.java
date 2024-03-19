package org.jdbc.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbClass {
//    String sql5 = "SELECT s.student_class, sc.course, AVG(sc.score) AS avg_score\n" +
//            "FROM scores AS sc\n" +
//            "JOIN students AS s ON sc.sid = s.student_id\n" +
//            "GROUP BY s.student_class, sc.course;";

    String sql = "";
    ResultSet res = null;

    public DbClass() throws SQLException {
    }

    //各クラス各科目ごとの上位３名
    public static List<SearchResult> allTop3() throws SQLException {
        String sql = "SELECT * FROM " +
                "(SELECT s.student_name,s.student_id,s.student_class,sc.course,sc.score," +
                "ROW_NUMBER() OVER (PARTITION BY sc.course,s.student_class ORDER BY sc.score DESC) AS rk " +
                "FROM scores AS sc JOIN students AS s ON sc.sid = s.student_id ) AS rking WHERE rk <= 3";
        ResultSet res = JdbcTest.executeQuery(sql);

        List<SearchResult> allTop3list = new ArrayList<>();
        while (res.next()) {
            SearchResult sr = new SearchResult();
            sr.setName(res.getString("student_name"));
            sr.setSid(res.getInt("student_id"));
            sr.setClassa(res.getString("student_class"));
            sr.setSubject(res.getString("course"));
            sr.setRankInClass(res.getInt("rk"));
            sr.setScore(res.getInt("score"));
            allTop3list.add(sr);

        }
        return allTop3list;
    }


    //各クラス総合得点上位３名
    public static List<SearchResult> TotalTop3() throws SQLException {
        String sql = "SELECT * FROM" +
                "(SELECT sc.sid,s.student_class,s.student_name, SUM(sc.score) AS total_score," +
                "ROW_NUMBER() OVER (PARTITION BY s.student_class ORDER BY SUM(sc.score) DESC) AS rk " +
                "FROM scores AS sc JOIN students AS s ON sc.sid = s.student_id GROUP BY sc.sid,s.student_class) AS rking " +
                "WHERE rk <= 3;";
        ResultSet res = JdbcTest.executeQuery(sql);

        List<SearchResult> TotalTop3list = new ArrayList<>();
        while (res.next()) {
            SearchResult sr = new SearchResult();
            sr.setName(res.getString("student_name"));
            sr.setSid(res.getInt("sid"));
            sr.setClassa(res.getString("student_class"));
            sr.setTotalScore(res.getInt("total_score"));
            sr.setRankInClass(res.getInt("rk"));
            TotalTop3list.add(sr);
        }
        return TotalTop3list;
    }

    //全校各科目得点上位３名
    public static List<SearchResult> EvryAll3() throws SQLException {
        String sql = "SELECT * FROM" +
                "(SELECT s.student_name,sc.sid,sc.course,sc.score,student_class," +
                "ROW_NUMBER() OVER (PARTITION BY sc.course ORDER BY sc.score DESC) AS rk " +
                "FROM scores AS sc JOIN students AS s ON sc.sid = s.student_id) AS rking " +
                "WHERE rk <= 3;";
        ResultSet res = JdbcTest.executeQuery(sql);

        List<SearchResult> EvryAll3list = new ArrayList<>();
        while (res.next()) {
            SearchResult sr = new SearchResult();
            sr.setName(res.getString("student_name"));
            sr.setSid(res.getInt("sid"));
            sr.setSubject(res.getString("course"));
            sr.setScore(res.getInt("score"));
            sr.setClassa(res.getString("student_class"));
            sr.setRankInSchool(res.getInt("rk"));
            EvryAll3list.add(sr);
        }
        return EvryAll3list;

    }

    //全校総合上位10名
    public static List<SearchResult> evrytop10() throws SQLException {
        String sql = "SELECT sc.sid,s.student_class,s.student_name,SUM(sc.score) AS total_score " +
                "FROM scores AS sc " +
                "JOIN students AS s ON sc.sid = s.student_id " +
                "GROUP BY sc.sid ORDER BY total_score DESC " +
                "LIMIT 10;";
        ResultSet res = JdbcTest.executeQuery(sql);

        List<SearchResult> evrytop10List = new ArrayList<>();
        while (res.next()) {
            SearchResult sr = new SearchResult();
            sr.setClassa(res.getString("student_class"));
            sr.setSid(res.getInt("sid"));
            sr.setName(res.getString("student_name"));
            sr.setTotalScore(res.getInt("total_score"));
            evrytop10List.add(sr);

        }
        return evrytop10List;

    }
    //    平均点上位教師
    public static List<SearchResult> NiceTeacher() throws SQLException {

        String sql = "WITH max_avg_scores AS (\n" +
                "SELECT course, MAX(average_score) AS max_average_score\n" +
                "FROM (\n" +
                "SELECT course, student_class, AVG(score) AS average_score\n" +
                "FROM students\n" +
                "INNER JOIN\n" +
                "scores ON students.student_id = scores.sid\n" +
                "GROUP BY student_class, course ) AS sub\n" +
                "GROUP BY course )\n" +
                "SELECT SUBJECT, student_class, max_average_score, teacher_name\n" +
                "FROM (\n" +
                "SELECT '英語' AS SUBJECT, s.student_class, max_avg.max_average_score AS max_average_score, t.teacher_name,\n" +
                "ROW_NUMBER() OVER(PARTITION BY '英語' ORDER BY max_avg.max_average_score DESC) AS rn\n" +
                "FROM (\n" +
                "SELECT student_class, course, AVG(score) AS average_score\n" +
                "FROM students\n" +
                "INNER JOIN scores ON students.student_id = scores.sid\n" +
                "WHERE course = '英語'\n" +
                "GROUP BY student_class, course ) AS s\n" +
                "INNER JOIN teachers AS t ON s.course = t.teacher_subject\n" +
                "INNER JOIN max_avg_scores AS max_avg ON s.course = max_avg.course\n" +
                "WHERE s.average_score = max_avg.max_average_score\n" +
                "UNION ALL\n" +
                "SELECT '日本語' AS SUBJECT, s.student_class, max_avg.max_average_score AS max_average_score, t.teacher_name,\n" +
                "ROW_NUMBER() OVER(PARTITION BY '日本語' ORDER BY max_avg.max_average_score DESC) AS rn\n" +
                "FROM (\n" +
                "SELECT student_class, course, AVG(score) AS average_score\n" +
                "FROM students\n" +
                "INNER JOIN scores ON students.student_id = scores.sid\n" +
                "WHERE course = '日本語'\n" +
                "GROUP BY student_class, course ) AS s\n" +
                "INNER JOIN teachers AS t ON s.course = t.teacher_subject\n" +
                "INNER JOIN max_avg_scores AS max_avg ON s.course = max_avg.course\n" +
                "WHERE s.average_score = max_avg.max_average_score\n" +
                "UNION ALL\n" +
                "SELECT '数学' AS SUBJECT, s.student_class, max_avg.max_average_score AS max_average_score, t.teacher_name,\n" +
                "ROW_NUMBER() OVER(PARTITION BY '数学' ORDER BY max_avg.max_average_score DESC) AS rn\n" +
                "FROM (\n" +
                "SELECT student_class, course, AVG(score) AS average_score\n" +
                "FROM students\n" +
                "INNER JOIN scores ON students.student_id = scores.sid\n" +
                "WHERE course = '数学'\n" +
                "GROUP BY student_class, course ) AS s\n" +
                "INNER JOIN teachers AS t ON s.course = t.teacher_subject\n" +
                "INNER JOIN max_avg_scores AS max_avg ON s.course = max_avg.course\n" +
                "WHERE s.average_score = max_avg.max_average_score ) AS ranked_results\n" +
                "WHERE rn = 1;";
        ResultSet res = JdbcTest.executeQuery(sql);

        List<SearchResult> NiceTeacher = new ArrayList<>();
        while (res.next()) {
            SearchResult sr = new SearchResult();
            sr.setClassa(res.getString("student_class"));
            sr.setSubject(res.getString("SUBJECT"));
            sr.setAvgScore(res.getInt("max_average_score"));
            sr.setTeacher(res.getString("teacher_name"));
            NiceTeacher.add(sr);
        }
        return NiceTeacher;
    }
}
