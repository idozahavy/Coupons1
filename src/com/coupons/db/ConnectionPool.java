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
			System.out.println("Error - " + e.getMessage());
		}
	}

	private Stack<Connection> connections = new Stack<>();

	private static ConnectionPool instance = null;// = new ConnectionPool();

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

	public <T> T invoke(ConnectionMethod<T> method) {
		Connection conn;
		T object = null;
		try {
			conn = getConnection();
			object = method.run(conn);
			returnConnection(conn);
		} catch (InterruptedException e) {
			System.out.println("Could not connect to database , message - " + e.getMessage());
		}
		return object;
	}

	public static <T> T instanceInvoke(ConnectionMethod<T> method) {
		return getInstance().invoke(method);
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
