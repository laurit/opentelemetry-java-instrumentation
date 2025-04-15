/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.instrumentation.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.function.Consumer;

class TestStatement implements Statement {
  final Connection connection;
  Consumer<String> sqlConsumer = unused -> {};

  TestStatement() {
    this.connection = null;
  }

  TestStatement(Connection connection) {
    this.connection = connection;
  }

  TestStatement(Connection connection, Consumer<String> sqlConsumer) {
    this.connection = connection;
    this.sqlConsumer = sqlConsumer;
  }

  @Override
  public void addBatch(String sql) throws SQLException {
    sqlConsumer.accept(sql);
  }

  @Override
  public void cancel() throws SQLException {}

  @Override
  public void clearBatch() throws SQLException {}

  @Override
  public void clearWarnings() throws SQLException {}

  @Override
  public void close() throws SQLException {}

  @Override
  public void closeOnCompletion() throws SQLException {}

  @Override
  public boolean execute(String sql) throws SQLException {
    sqlConsumer.accept(sql);
    return true;
  }

  @Override
  public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
    sqlConsumer.accept(sql);
    return true;
  }

  @Override
  public boolean execute(String sql, int[] columnIndexes) throws SQLException {
    sqlConsumer.accept(sql);
    return true;
  }

  @Override
  public boolean execute(String sql, String[] columnNames) throws SQLException {
    sqlConsumer.accept(sql);
    return true;
  }

  @Override
  public int[] executeBatch() throws SQLException {
    return new int[0];
  }

  @Override
  public ResultSet executeQuery(String sql) throws SQLException {
    sqlConsumer.accept(sql);
    return null;
  }

  @Override
  public int executeUpdate(String sql) throws SQLException {
    sqlConsumer.accept(sql);
    return 0;
  }

  @Override
  public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
    sqlConsumer.accept(sql);
    return 0;
  }

  @Override
  public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
    sqlConsumer.accept(sql);
    return 0;
  }

  @Override
  public int executeUpdate(String sql, String[] columnNames) throws SQLException {
    sqlConsumer.accept(sql);
    return 0;
  }

  @Override
  public Connection getConnection() throws SQLException {
    return connection;
  }

  @Override
  public int getFetchDirection() throws SQLException {
    return 0;
  }

  @Override
  public int getFetchSize() throws SQLException {
    return 0;
  }

  @Override
  public ResultSet getGeneratedKeys() throws SQLException {
    return null;
  }

  @Override
  public int getMaxFieldSize() throws SQLException {
    return 0;
  }

  @Override
  public int getMaxRows() throws SQLException {
    return 0;
  }

  @Override
  public boolean getMoreResults() throws SQLException {
    return false;
  }

  @Override
  public boolean getMoreResults(int current) throws SQLException {
    return false;
  }

  @Override
  public int getQueryTimeout() throws SQLException {
    return 0;
  }

  @Override
  public ResultSet getResultSet() throws SQLException {
    return null;
  }

  @Override
  public int getResultSetConcurrency() throws SQLException {
    return 0;
  }

  @Override
  public int getResultSetHoldability() throws SQLException {
    return 0;
  }

  @Override
  public int getResultSetType() throws SQLException {
    return 0;
  }

  @Override
  public int getUpdateCount() throws SQLException {
    return 0;
  }

  @Override
  public SQLWarning getWarnings() throws SQLException {
    return null;
  }

  @Override
  public boolean isCloseOnCompletion() throws SQLException {
    return false;
  }

  @Override
  public boolean isClosed() throws SQLException {
    return false;
  }

  @Override
  public boolean isPoolable() throws SQLException {
    return false;
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return false;
  }

  @Override
  public void setCursorName(String name) throws SQLException {}

  @Override
  public void setEscapeProcessing(boolean enable) throws SQLException {}

  @Override
  public void setFetchDirection(int direction) throws SQLException {}

  @Override
  public void setFetchSize(int rows) throws SQLException {}

  @Override
  public void setMaxFieldSize(int max) throws SQLException {}

  @Override
  public void setMaxRows(int max) throws SQLException {}

  @Override
  public void setPoolable(boolean poolable) throws SQLException {}

  @Override
  public void setQueryTimeout(int seconds) throws SQLException {}

  @Override
  public long executeLargeUpdate(String sql) throws SQLException {
    sqlConsumer.accept(sql);
    return 0;
  }

  @Override
  public long executeLargeUpdate(String sql, int[] columnIndexes) throws SQLException {
    sqlConsumer.accept(sql);
    return 0;
  }

  @Override
  public long executeLargeUpdate(String sql, String[] columnNames) throws SQLException {
    sqlConsumer.accept(sql);
    return 0;
  }

  @Override
  public long executeLargeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
    sqlConsumer.accept(sql);
    return 0;
  }

  @Override
  public long[] executeLargeBatch() throws SQLException {
    return new long[0];
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    return null;
  }
}
