package com.amazing_gift.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class SqlSession {

	private DataSource dataSource;

	public SqlSession(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public int update(String sql, PreparedStatementHandler preparedStatementHandler) {

		Connection connection = null;

		try {

			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatementHandler.handle(preparedStatement);

			return preparedStatement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
			return 0;

		} finally {

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}

		}
	}

	public int updateBatch(String sql, PreparedStatementHandler preparedStatementHandler) {

		Connection connection = null;

		try {

			connection = dataSource.getConnection();
			connection.setAutoCommit(false);

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatementHandler.handle(preparedStatement);

			int[] resultCounts = preparedStatement.executeBatch();
			int totalCount = 0;
			for (int resultCount : resultCounts) {
				totalCount += resultCount;
			}

			connection.commit();

			return totalCount;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;

		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	public int query(String sql, PreparedStatementHandler preparedStatementHandler, ResultSetHandler resultSetHandler) {

		Connection connection = null;

		try {

			connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatementHandler.handle(preparedStatement);

			ResultSet resultSet = preparedStatement.executeQuery();

			return resultSetHandler.handle(resultSet);

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;

		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}
	}

}
