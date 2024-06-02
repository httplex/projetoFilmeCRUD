package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TesteJDBC {
		public static void main(String[] args) throws SQLException {
			String url = "jdbc:sqlite:C:/Users/desuf/Desktop/testeBanco/bancoIMDB.db";
			String username = "root";
			String password = "root";
			
			
			Connection conexao = DriverManager.getConnection(url, username ,password);
			
			conexao.close();
	}

}
