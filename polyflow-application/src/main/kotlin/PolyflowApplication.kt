package io.github.jangalinski.polyflow

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.security.AnyTypePermission
import io.holunda.polyflow.datapool.core.EnablePolyflowDataPool
import io.holunda.polyflow.taskpool.core.EnablePolyflowTaskPool
import io.holunda.polyflow.view.simple.EnablePolyflowSimpleView
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.axonframework.serialization.xml.CompactDriver
import org.axonframework.springboot.util.XStreamSecurityTypeUtility
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling

fun main(args: Array<String>) = runApplication<PolyflowApplication>().let { }

@SpringBootApplication

// this application collects tasks from camunda-application
@EnablePolyflowTaskPool

// this application collects data-entries from business-application
@EnablePolyflowDataPool

// and provides data in simple view
@EnablePolyflowSimpleView


@OpenAPIDefinition(info = Info(title = "Polyflow Application", description = "collects tasks and dataentries", version = "1"))

@EnableScheduling
class PolyflowApplication {

  @Bean("defaultAxonXStream")
  @ConditionalOnMissingBean
  fun defaultAxonXStream(applicationContext: ApplicationContext): XStream = XStream(CompactDriver()).apply {
    // just ignore xstream security
    allowTypesByWildcard(XStreamSecurityTypeUtility.autoConfigBasePackages(applicationContext))
    addPermission(AnyTypePermission.ANY)
  }

}
