/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.instrumentation.spring.autoconfigure.exporters.otlp;

import static org.assertj.core.api.Assertions.assertThat;

import io.opentelemetry.instrumentation.spring.autoconfigure.OpenTelemetryAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

class OtlpLogExporterAutoConfigurationTest {

  private final ApplicationContextRunner runner =
      new ApplicationContextRunner()
          .withConfiguration(
              AutoConfigurations.of(
                  OpenTelemetryAutoConfiguration.class,
                  OtlpLogRecordExporterAutoConfiguration.class));

  @Test
  void otlpDisabled() {
    runner
        .withPropertyValues("otel.logs.exporter=none")
        .run(context -> assertThat(context.containsBean("otelOtlpLogRecordExporter")).isFalse());
  }
}
