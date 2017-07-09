package com.jz.crm.base.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jz.crm.base.config.JZMushroom;

/**
 * 数据库链接
 * @author Administrator
 *
 */
public class DbUntils {
	static{
		try {
			Class.forName(JZMushroom.getInstance().get("bb-jdbc-driver"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	public static Connection getConnection(){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(JZMushroom.getInstance().get("bb-jdbc-proxool-url")
					,JZMushroom.getInstance().get("bb-jdbc-user"),JZMushroom.getInstance().get("bb-jdbc-password"));
		//conn = DriverManager.getConnection("jdbc:sqlserver://192.168.1.8:1433; DatabaseName=MIS_30_xyj","sa","mis2012!@");
		//	conn = DriverManager.getConnection("jdbc:sqlserver://192.168.1.107:1433; DatabaseName=MIS_30_xyj","sa","2293340732");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void free(ResultSet rs,Statement st,PreparedStatement pst,Connection conn){
		try{
			if(rs!=null){
				rs.close();
				rs = null;
			}
			if(st!=null){
				st.close();
				st = null;
			}
			if(pst!=null){
				pst.close();
				pst = null;
			}
			if(conn!=null){
				conn.close();
				conn = null;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
