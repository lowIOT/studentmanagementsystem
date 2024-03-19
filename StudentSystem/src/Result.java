package org.jdbc;


import org.jdbc.DB.DbClass;
import org.jdbc.DB.SearchResult;

import java.io.*;
import java.sql.*;
import java.util.List;

public class Result {

    public static void main(String[] args) throws IOException, SQLException {

        String WFilePath = "StudentSystem/out/production/StudentSystem/org/jdbc/result.txt";

        try {

            FileWriter fileWriter = new FileWriter(WFilePath);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            DbClass db = new DbClass();

//            String Class = res.getString("class");
//            String course = res.getString("course");
//            String avg_score = res.getString("avg_score");
//            String subject_teacher = res.getString("subject_teacher");
//            writer.write(Class + "\t" + course + "\t" + avg_score + "\t" + subject_teacher + "\r\n");

            writer.write("各クラス各科目ごとの上位３名");
            writer.newLine();
            writer.write("順位: 生徒番号: クラス: 生徒名: 科目: 得点:");
            writer.newLine();

            List<SearchResult> allTop3 = db.allTop3();
            for (int i = 0; i < allTop3.size(); i++) {
                writer.write(allTop3.get(i).getRankInClass() + ":　" + allTop3.get(i).getSid() + ":　" + allTop3.get(i).getClassa() + ":　" + allTop3.get(i).getName() + ":　" + allTop3.get(i).getSubject() + ":　" + allTop3.get(i).getScore());
                writer.newLine();
            }
            System.out.println("書き込みに成功");

            writer.write("各クラス総合得点上位３名");
            writer.newLine();
            writer.write("順位: 生徒番号: クラス: 生徒名: 総合得点:");
            writer.newLine();

            List<SearchResult> TotalTop3 = db.TotalTop3();
            for (int i = 0; i < TotalTop3.size(); i++) {
                writer.write(TotalTop3.get(i).getRankInClass() + ":　" + TotalTop3.get(i).getSid() + ":　" + TotalTop3.get(i).getClassa() + ":　" + TotalTop3.get(i).getName() + ":　" + TotalTop3.get(i).getTotalScore());
                writer.newLine();
            }
            System.out.println("書き込みに成功");

            writer.write("全校各科目得点上位３名");
            writer.newLine();
            writer.write("順位: 科目: 生徒番号: クラス: 生徒名: 得点:");
            writer.newLine();

            List<SearchResult> EvryAll3 = db.EvryAll3();
            for (int i = 0; i < EvryAll3.size(); i++) {
                writer.write(EvryAll3.get(i).getRankInSchool() + ":　" + EvryAll3.get(i).getSubject() + ":　" + EvryAll3.get(i).getSid() + ":　" + EvryAll3.get(i).getClassa() + ":　" + EvryAll3.get(i).getName() + ":　" + EvryAll3.get(i).getScore());
                writer.newLine();
            }
            System.out.println("書き込みに成功");

            writer.write("総合成績上位10名");
            writer.newLine();
            writer.write("順位: 生徒番号: クラス: 生徒名: 総合得点:");
            writer.newLine();

            List<SearchResult> top10 = db.evrytop10();
            int j = 1;
            for (int i = 0; i < top10.size(); i++) {
                writer.write(j + ":　" + top10.get(i).getSid() + ":　" + top10.get(i).getClassa() + ":　" + top10.get(i).getName() + ":　" + top10.get(i).getTotalScore());
                writer.newLine();
                j++;
            }
            System.out.println("書き込みに成功");

            writer.newLine();
            writer.write("各科目平均点上位教師");
            writer.newLine();
            writer.write("科目: クラス: 平均点数: 担当教師名:");
            writer.newLine();

            List<SearchResult> NiceTeacher = db.NiceTeacher();
            for (int i = 0; i < NiceTeacher.size(); i++) {
                writer.write(NiceTeacher.get(i).getSubject() + ":　" + NiceTeacher.get(i).getClassa() + ":　" + NiceTeacher.get(i).getAvgScore() + ":　" + NiceTeacher.get(i).getTeacher());
                writer.newLine();
            }
            System.out.println("書き込みに成功");

            writer.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}