package com.coupons.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionPool {

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("Loading JDBC Driver Failed!!!");
			e.printStackTrace();
			System.err.println("Loading JDBC Driver Failed!!!");
		}
	}

	private Stack<Connection> connections = new Stack<>();

	private static ConnectionPool instance = null;

	private ConnectionPool() {
		for (int i = 1; i <= 10; i++) {
			System.out.println("Creating connection #" + i);
			try {
				Connection conn = DriverManager.getConnection(DatabaseManager.getUrl(), DatabaseManager.getUser(),
						DatabaseManager.getPassword());
				connections.push(conn);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static ConnectionPool getInstance() {
		if (instance == null) {
			synchronized (ConnectionPool.class) {
				if (instance == null) {
					instance = new ConnectionPool();
				}
			}
		}
		return instance;
	}

	private Connection getConnection() throws InterruptedException {

		synchronized (connections) {

			if (connections.isEmpty()) {
				connections.wait();
			}

			return connections.pop();
		}
	}

	private void returnConnection(Connection conn) {

		synchronized (connections) {
			connections.push(conn);
			connections.notify();
		}
	}

	@SuppressWarnings("unused")
	public static <T> T instanceInvoke(ConnectionMethod<T> method) {
		Connection conn = null;
		T object = null;
		try {
			conn = getInstance().getConnection();
			object = method.run(conn);
			getInstance().returnConnection(conn);
		} catch (InterruptedException e) {
			if (conn != null) {
				getInstance().returnConnection(conn);
			}
			System.out.println("Could not connect to database , message - " + e.getMessage());
		}
		return object;
	}

	public void closeAllConnection() throws InterruptedException {
		synchronized (connections) {

			while (connections.size() < 10) {
				connections.wait();
			}

			for (Connection conn : connections) {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}
	}
}
