package com.target.game.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import com.target.game.interfaces.Config;

public class ConfigImpl implements Config {

	private static final String GET_BY_NAME = "SELECT id, name, value FROM ConfigImpl WHERE name = ?";

	private Connection connect() {
		String url = "jdbc:sqlite:src/main/resources/bowling.db";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	@Override
	public void createTable() {
		String sql = "CREATE TABLE IF NOT EXISTS ConfigImpl (id integer PRIMARY KEY, name varchar(30) UNIQUE NOT NULL, value varchar(100) NOT NULL);";

		try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void insertConfig(String name, String value) {
		String sql = "INSERT INTO ConfigImpl(name,value) VALUES(?,?)";

		try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, name);
			pstmt.setString(2, value);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void updateConfig(String name, String value) {
		String sql = "UPDATE ConfigImpl SET value=? WHERE name=?";

		try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, value);
			pstmt.setString(2, name);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void getAllConfigs() {
		String sql = "SELECT id, name, value FROM ConfigImpl";

		try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				System.out.println(rs.getInt("id") + "\t" + rs.getString("name") + "\t" + rs.getString("value"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public List<String> getPlayerNames(String playerNames) {
		List<String> playerNameList = null;

		try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(GET_BY_NAME)) {
			pstmt.setString(1, playerNames);
			ResultSet rs = pstmt.executeQuery();
			if (rs != null) {
				String value = rs.getString("value");
				playerNameList = Arrays.asList(value.split(",", -1));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return playerNameList;
	}

	@Override
	public int getTotalLanes(String name) {
		return getInt(name);
	}

	@Override
	public int getTotalPlayers(String totalPlayers) {
		return getInt(totalPlayers);
	}

	private int getInt(String name) {
		int value = 0;
		try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(GET_BY_NAME)) {
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			if (rs != null) {
				value = Integer.parseInt(rs.getString("value"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return value;
	}

}
