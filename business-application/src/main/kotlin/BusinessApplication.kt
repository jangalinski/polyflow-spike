package io.github.jangalinski.business

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.security.AnyTypePermission
import io.github.jangalinski.business.model.MyBusinessObject
import io.holunda.camunda.taskpool.api.business.AuthorizationChange
import io.holunda.camunda.taskpool.api.business.Modification
import io.holunda.camunda.taskpool.api.business.ProcessingType
import io.holunda.polyflow.datapool.EnableDataEntrySender
import io.holunda.polyflow.datapool.sender.DataEntryCommandSender
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.axonframework.serialization.xml.CompactDriver
import org.axonframework.springboot.util.XStreamSecurityTypeUtility
import org.camunda.bpm.engine.variable.Variables
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.*
import java.time.OffsetDateTime
import java.util.*
import java.util.concurrent.ConcurrentHashMap

fun main(args: Array<String>) = runApplication<BusinessApplication>().let { }


@EnableDataEntrySender

@SpringBootApplication

@OpenAPIDefinition(info = Info(title = "Business Application", description = "providing business objects and correlations", version = "1"))
class BusinessApplication {
  @Bean("defaultAxonXStream")
  @ConditionalOnMissingBean
  fun defaultAxonXStream(applicationContext: ApplicationContext): XStream = XStream(CompactDriver()).apply {
    // just ignore xstream security
    allowTypesByWildcard(XStreamSecurityTypeUtility.autoConfigBasePackages(applicationContext))
    addPermission(AnyTypePermission.ANY)
  }
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
