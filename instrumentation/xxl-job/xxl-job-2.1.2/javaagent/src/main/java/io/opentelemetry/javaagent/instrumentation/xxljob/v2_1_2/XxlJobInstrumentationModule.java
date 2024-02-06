/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.javaagent.instrumentation.xxljob.v2_1_2;

import static java.util.Arrays.asList;

import com.google.auto.service.AutoService;
import io.opentelemetry.javaagent.extension.instrumentation.InstrumentationModule;
import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;
import java.util.List;

@AutoService(InstrumentationModule.class)
public class XxlJobInstrumentationModule extends InstrumentationModule {

  public XxlJobInstrumentationModule() {
    super("xxl-job", "xxl-job-2.1.2");
  }

  @Override
  public List<TypeInstrumentation> typeInstrumentations() {
    return asList(
        new MethodJobHandlerInstrumentation(),
        new ScriptJobHandlerInstrumentation(),
        new SimpleJobHandlerInstrumentation(),
        new GlueJobHandlerInstrumentation());
  }
}
