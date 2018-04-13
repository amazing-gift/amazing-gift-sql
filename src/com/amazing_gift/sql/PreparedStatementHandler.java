package com.amazing_gift.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementHandler {

	public void handle(PreparedStatement preparedStatement) throws SQLException;

}
