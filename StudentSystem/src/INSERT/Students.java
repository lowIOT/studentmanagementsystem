package org.jdbc.INSERT;

import org.jdbc.DB.JdbcTest;

import java.sql.*;
import java.io.*;

public class Students {
    public static void main(String[] args) throws FileNotFoundException, SQLException {
        String RFilePath = "StudentSystem/src/csv/student.csv";

        FileReader reader = new FileReader(RFilePath);
        BufferedReader bReader = new BufferedReader(reader);

        JdbcTest dbConnection = new JdbcTest();
        Connection conn = dbConnection.getConnection();

        try {
            //テーブルの作成
            //String sql = "CREATE TABLE students (student_id int, student_name varchar(50), student_age int, student_sex varchar(50), student_class varchar(50))";
            //PreparedStatement statement = conn.prepareStatement(sql);

            //statement.executeUpdate(sql);
            String tableName = "students";
            String line;
            line = bReader.readLine();


            while ((line = bReader.readLine()) != null) {

                String[] values = line.split(",");

                String sql1 = "INSERT INTO " + tableName + " (student_id, student_name, student_age, student_sex, student_class) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement1 = conn.prepareStatement(sql1);

                statement1.setString(1, values[0]);
                statement1.setString(2, values[1]);
                statement1.setString(3, values[2]);
                statement1.setString(4, values[3]);
                statement1.setString(5, values[4]);

                statement1.executeUpdate();

            }
            dbConnection.closeConnection(conn);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
