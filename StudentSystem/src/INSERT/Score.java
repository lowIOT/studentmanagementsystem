package org.jdbc.INSERT;

import org.jdbc.DB.JdbcTest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Score {
    public static void main(String[] args) throws FileNotFoundException, SQLException {
        String RFilePath = "StudentSystem/src/csv/score.csv";

        FileReader reader = new FileReader(RFilePath);
        BufferedReader bReader = new BufferedReader(reader);

        JdbcTest dbConnection = new JdbcTest();
        Connection conn = dbConnection.getConnection();

        try {
            //テーブルの作成
            String sql = "CREATE TABLE scores (sid INT, course VARCHAR(255), score INT)";
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.executeUpdate(sql);

            String tableName = "scores";
            String line;
            line = bReader.readLine();

            while ((line = bReader.readLine()) != null) {

                String[] values = line.split(",");

                String sql1 = "INSERT INTO " + tableName + " (sid, course, score) VALUES (?, ?, ?)";
                PreparedStatement statement1 = conn.prepareStatement(sql1);

                statement1.setString(1, values[0]);
                statement1.setString(2, values[1]);
                statement1.setString(3, values[2]);

                statement1.executeUpdate();

            }
            dbConnection.closeConnection(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


