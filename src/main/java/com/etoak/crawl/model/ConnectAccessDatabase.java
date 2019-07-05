package com.etoak.crawl.model;

import java.sql.*;
import java.util.*;


public class ConnectAccessDatabase {
    public static void main(String[] args) {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            //需要引的数据源
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            //databaseName就是刚刚添加的数据源名称，
            // 是在ODBC数据源中添加的数据库名称会自动读取到数据库中的数据
            String url = "jdbc:odbc:model";
            //乱码解决
            Properties prop = new Properties();
            prop.put("charSet", "gb2312");
            //没有用户名和密码的时候直接为空
            Connection con = DriverManager.getConnection(url, prop);
            Statement sta = con.createStatement();
            //demoTable为access数据库中的一个表名
            ResultSet rst = sta.executeQuery("select * from student where true");


//			rst = sta.executeQuery("select * from e_user");
            ResultSetMetaData md = rst.getMetaData(); //获得结果集结构信息,元数据
            int columnCount = md.getColumnCount();   //获得列数
            while (rst.next()) {
                Map<String, Object> rowData = new HashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), rst.getObject(i));
                }
                list.add(rowData);

            }


//			ResultSet rst = sta.executeQuery("select * from student where id = 2");
//			System.out.println(rst.getClass());
            if (rst.next()) {
//				System.out.println(rst.next());
//				System.out.println(rst.findColumn("ID"));
                System.out.println(rst);

                System.out.println(rst.getString("name"));
                System.out.println(rst.getString("sex"));
                //解决乱码问题
//				System.out.println(new String(rst.getBytes("name"), "gbk"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } /*catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/

        for (Map<String, Object> map : list) {

            Set<String> strings = map.keySet();
            System.out.println("key" + map.keySet());
            for (String s : strings) {
                System.out.println("value" + s);
            }
        }


    }
}
