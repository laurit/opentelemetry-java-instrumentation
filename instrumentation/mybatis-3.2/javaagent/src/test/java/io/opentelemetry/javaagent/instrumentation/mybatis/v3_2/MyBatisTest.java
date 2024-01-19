/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.javaagent.instrumentation.mybatis.v3_2;

import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.instrumentation.testing.junit.AgentInstrumentationExtension;
import io.opentelemetry.instrumentation.testing.junit.InstrumentationExtension;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class MyBatisTest {

  @RegisterExtension
  protected static final InstrumentationExtension testing = AgentInstrumentationExtension.create();

  private static final String SPAN_NAME =
      "io.opentelemetry.javaagent.instrumentation.mybatis.v3_2.RecordMapper.updateRecord";

  @Test
  public void mybatis() {
    JdbcDataSource dataSource = new JdbcDataSource();
    dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
    Configuration configuration = new Configuration();
    configuration.setEnvironment(new Environment("test", new JdbcTransactionFactory(), dataSource));
    configuration.addMapper(RecordMapper.class);
    SqlSession sqlSession = new SqlSessionFactoryBuilder().build(configuration).openSession();
    RecordMapper recordMapper = sqlSession.getMapper(RecordMapper.class);
    recordMapper.updateRecord();
    span(SPAN_NAME);
  }

  static void span(String spanName) {
    testing.waitAndAssertTracesWithoutScopeVersionVerification(
        trace -> {
          trace
              .hasSize(1)
              .hasSpansSatisfyingExactly(
                  span -> {
                    span.hasKind(SpanKind.INTERNAL).hasName(spanName);
                  });
        });
  }
}
