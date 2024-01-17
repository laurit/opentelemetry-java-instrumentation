package io.opentelemetry.javaagent.instrumentation.mybatis;

import static org.mockito.Mockito.when;

import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.instrumentation.testing.junit.AgentInstrumentationExtension;
import io.opentelemetry.instrumentation.testing.junit.InstrumentationExtension;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.junit.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.Mockito;

public class MybatisTest {

  @RegisterExtension
  protected static final InstrumentationExtension testing = AgentInstrumentationExtension.create();

  private static final String SPAN_NAME = "io.opentelemetry.javaagent.instrumentation.mybatis.RecordMapper.updateRecord";

  
  
  @Test
  public void mybatis() throws Exception {
    DefaultSqlSession sqlSession = Mockito.mock(DefaultSqlSession.class);
    Configuration configuration = Mockito.mock(Configuration.class);
    DefaultObjectFactory defaultObjectFactory = Mockito.mock(DefaultObjectFactory.class);
    when(sqlSession.update(SPAN_NAME, null)).thenReturn(1);
    Class<?> mappedStatementClass = Class.forName(MappedStatement.class.getName());
    Constructor<?> constructor = mappedStatementClass.getDeclaredConstructor();
    constructor.setAccessible(true);
    MappedStatement mappedStatement = (MappedStatement) constructor.newInstance();
    Field id = mappedStatementClass.getDeclaredField("id");
    id.setAccessible(true);
    id.set(mappedStatement, SPAN_NAME);
    Field sqlCommandType = mappedStatementClass.getDeclaredField("sqlCommandType");
    sqlCommandType.setAccessible(true);
    sqlCommandType.set(mappedStatement, SqlCommandType.UPDATE);
    when(configuration.hasStatement(SPAN_NAME)).thenReturn(true);
    when(configuration.getMappedStatement(SPAN_NAME)).thenReturn(mappedStatement);
    when(configuration.getObjectFactory()).thenReturn(defaultObjectFactory);
    when(defaultObjectFactory.isCollection(Void.class)).thenReturn(false);
    Class<?> mapper = Class.forName(RecordMapper.class.getName());
    Method method = mapper.getMethod("updateRecord");
    MapperMethod mapperMethod = new MapperMethod(mapper, method, configuration);
    try {
      mapperMethod.execute(sqlSession, null);
      testSpan(SPAN_NAME);
    } catch (RuntimeException e) {
      throw new RuntimeException(e);
    }
  }

  public void testSpan(String spanName) {
    testing.waitAndAssertTracesWithoutScopeVersionVerification(trace -> {
      trace.hasSize(1)
          .hasSpansSatisfyingExactly(span -> {
            span.hasKind(SpanKind.INTERNAL)
                .hasName(spanName);
          });
    });
  }
}