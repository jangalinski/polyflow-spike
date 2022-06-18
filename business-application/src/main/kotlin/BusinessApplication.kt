package io.github.jangalinski.business

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.jangalinski.business.model.MyBusinessObject
import io.github.jangalinski.lib.jackson.JacksonExt
import io.holunda.camunda.taskpool.api.business.AuthorizationChange
import io.holunda.camunda.taskpool.api.business.Modification
import io.holunda.camunda.taskpool.api.business.ProcessingType
import io.holunda.polyflow.datapool.EnableDataEntrySender
import io.holunda.polyflow.datapool.sender.DataEntryCommandSender
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import mu.KLogging
import org.axonframework.messaging.Message
import org.axonframework.messaging.interceptors.LoggingInterceptor
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap

fun main(args: Array<String>) = runApplication<BusinessApplication>().let { }


@EnableDataEntrySender

@SpringBootApplication

@OpenAPIDefinition(info = Info(title = "Business Application", description = "providing business objects and correlations", version = "1"))
class BusinessApplication {
  companion object : KLogging()

  @Primary
  @Bean("defaultJacksonObjectMapper")
  fun defaultJacksonObjectMapper(): ObjectMapper = JacksonExt.defaultJacksonObjectMapper()

  @Bean
  fun loggingInterceptor() = LoggingInterceptor<Message<*>>()
}

@RestController
@RequestMapping("/business-objects")
class BusinessRestController(
  private val sender: DataEntryCommandSender
) {

  private val store = ConcurrentHashMap<String, MyBusinessObject>()

  @GetMapping
  fun findAll(): List<MyBusinessObject> = store.values.toList()

  @GetMapping("/{key}")
  fun findByKey(key: String): Optional<MyBusinessObject> = Optional.ofNullable(store[key])

  @PutMapping
  fun save(@RequestBody pojo: MyBusinessObject) {
    store[pojo.key] = pojo

    sender.sendDataEntryChange(
      entryType = "business.object",
      entryId = pojo.key,
      payload = pojo,

      // human readable
      name = "MyBusinessObject(${pojo.key})",
      type = "My Business Object",

      state = ProcessingType.IN_PROGRESS.of("created"),

      modification = Modification(
        username = "piggy",
        log = "new object(${pojo.key}) created",
        logNotes = "this is the first entry $pojo"
      ),
      authorizationChanges = listOf(AuthorizationChange.addUser("kermit"))
    )
  }
}
