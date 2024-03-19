package org.jdbc;

import org.jdbc.DB.JdbcTest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class StudentInfo {
    public static void main(String[] args) throws IOException, SQLException {

        JdbcTest dbConnection = new JdbcTest();
        Connection conn = dbConnection.getConnection();

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("生徒番号を入力してください: ");
            String  StudentID = scanner.nextLine();

            scanner.close();

            String sql = "SELECT  rk, rk2, sc.sid ,ranking.student_class,ranking.student_name,SUM(sc.score) AS total_score,\n" +
                    " SUM(CASE WHEN sc.course = '英語' THEN sc.score ELSE 0 END) AS english, \n" +
                    " SUM(CASE WHEN sc.course = '数学' THEN sc.score ELSE 0 END) AS math, \n" +
                    " SUM(CASE WHEN sc.course = '日本語' THEN sc.score ELSE 0 END) AS japanese\n" +
                    "FROM\n" +
                    "\t(\n" +
                    "\t\tSELECT sc.sid,s.student_class,s.student_name,SUM(sc.score) AS total_score, \n" +
                    "ROW_NUMBER() OVER (ORDER BY SUM(sc.score) DESC) AS rk,\n" +
                    "ROW_NUMBER() OVER (PARTITION BY s.student_class ORDER BY SUM(sc.score) DESC) AS rk2\n" +
                    "FROM scores AS sc \n" +
                    "JOIN students AS s ON sc.sid = s.student_id \n" +
                    "GROUP BY sc.sid ORDER BY total_score DESC\n" +
                    "\t) AS ranking\n" +
                    "JOIN scores sc ON ranking.sid = sc.sid\n" +
                    "WHERE ranking.sid = " + StudentID ;

            PreparedStatement stmt1 = conn.prepareStatement(sql);

            ResultSet res = stmt1.executeQuery(sql);

            while (res.next()) {
                String studentClass_ch = res.getString("student_class");
                if(studentClass_ch != null) {
                    int studentId = res.getInt("sid");
                    String studentClass = res.getString("student_class");
                    String studentName = res.getString("student_name");
                    int math = res.getInt("math");
                    int japanese = res.getInt("japanese");
                    int english = res.getInt("english");
                    int TotalScore = res.getInt("total_score");
                    int rk = res.getInt("rk");
                    int rk2 = res.getInt("rk2");
                    System.out.println
                            ("生徒番号" + "\t" + "クラス" + "\t" + "生徒氏名" + "\t" + "英語" + "\t" + "数学" + "\t" + "日本語" + "\t" + "総合成績" + "\t" + "全体順位" + "\t" + "クラス順位" + "\r\n");
                    System.out.println
                            (studentId + "\t" + studentClass + "\t" + studentName + "\t" + english + "\t" + math + "\t" + japanese + "\t" + TotalScore + "\t" + rk + "\t" + rk2 + "\r\n");
                }
                else{
                    System.out.println("入力値に問題があります。");
                }
            }

            res.close();
            dbConnection.closeConnection(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
