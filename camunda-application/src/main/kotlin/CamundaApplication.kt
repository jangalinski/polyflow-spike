package io.github.jangalinski.camunda

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.security.AnyTypePermission
import io.github.jangalinski.camunda.process.taskDoStuffVariablesCorrelator
import io.holunda.polyflow.taskpool.EnableTaskpoolEngineSupport
import io.holunda.polyflow.taskpool.collector.task.enricher.ProcessVariablesCorrelator
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.axonframework.serialization.xml.CompactDriver
import org.axonframework.springboot.util.XStreamSecurityTypeUtility
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean

fun main(args: Array<String>) = runApplication<CamundaApplication>().let { }

@SpringBootApplication
@EnableProcessApplication

// send tasks to polyflow
@EnableTaskpoolEngineSupport

@OpenAPIDefinition(info = Info(title = "Camunda Application", description = "running processes with tasks", version = "1"))
class CamundaApplication {

  @Bean("defaultAxonXStream")
  @ConditionalOnMissingBean
  fun defaultAxonXStream(applicationContext: ApplicationContext): XStream = XStream(CompactDriver()).apply {
    // just ignore xstream security
    allowTypesByWildcard(XStreamSecurityTypeUtility.autoConfigBasePackages(applicationContext))
    addPermission(AnyTypePermission.ANY)
  }

  @Bean
  fun polyflowCorrelations() : ProcessVariablesCorrelator = ProcessVariablesCorrelator(taskDoStuffVariablesCorrelator)
}
