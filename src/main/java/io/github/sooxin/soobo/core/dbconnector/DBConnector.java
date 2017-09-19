package io.github.sooxin.soobo.core.dbconnector;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.sooxin.soobo.cli.model.CLIConfig;

public class DBConnector {
	private static String driver = "org.sqlite.JDBC";
	private static String url;
	private static String username;
	private static String password;
	private static Connection conn;

	static {
		CLIConfig cliConfig=CLIConfig.getParsedCLIConfig();
		DBConnector.url="jdbc:sqlite:"+cliConfig.getProjects().get(cliConfig.getCurrentProjectName())+File.separator+"soobo.db";
		System.out.println("> 正在连接数据库 "+url);
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			if(conn==null) {
				DBConnector.conn=DriverManager.getConnection(url);
			}
		} catch (SQLException e) {
			System.out.println("# 数据库连接出现未知错误！");
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		//return DBConnector.conn;
		Connection conn=null;
		try {
			conn=DriverManager.getConnection(url);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

}
