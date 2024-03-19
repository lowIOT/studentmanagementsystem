package org.jdbc.INSERT;

import org.jdbc.DB.JdbcTest;

import java.sql.*;
import java.io.*;

public class Subjects {
    public static void main(String[] args) throws FileNotFoundException, SQLException {
        String RFilePath = "StudentSystem/src/csv/course-schedul.csv";

        FileReader reader = new FileReader(RFilePath);
        BufferedReader bReader = new BufferedReader(reader);

        JdbcTest dbConnection = new JdbcTest();
        Connection conn = dbConnection.getConnection();

        try {
            //テーブルの作成
            String sql = "CREATE TABLE subjects (id int, class varchar(50), subject varchar(50), subject_teacher_id varchar(50))";
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.executeUpdate(sql);

            String tableName = "subjects";
            String line;
            line = bReader.readLine();


            while ((line = bReader.readLine()) != null) {

                String[] values = line.split(",");

                String sql1 = "INSERT INTO " + tableName + " (id, class, subject, subject_teacher_id) VALUES (?, ?, ?, ?)";
                PreparedStatement statement1 = conn.prepareStatement(sql1);

                statement1.setString(1, values[0]);
                statement1.setString(2, values[1]);
                statement1.setString(3, values[2]);
                statement1.setString(4, values[3]);

                statement1.executeUpdate();

            }
            dbConnection.closeConnection(conn);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
