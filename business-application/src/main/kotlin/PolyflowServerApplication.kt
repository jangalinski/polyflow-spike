package io.github.jangalinski.server

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.security.AnyTypePermission
import io.holunda.camunda.taskpool.api.task.ProcessReference
import io.holunda.polyflow.view.DataEntry
import io.holunda.polyflow.view.Task
import io.holunda.polyflow.view.TaskWithDataEntries
import io.holunda.polyflow.view.query.task.TasksWithDataEntriesForUserQuery
import io.holunda.polyflow.view.query.task.TasksWithDataEntriesQueryResult
import org.axonframework.queryhandling.QueryHandler
import org.axonframework.serialization.xml.XStreamSerializer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

fun main(args: Array<String>) = runApplication<PolyflowServerApplication>().let { }

@SpringBootApplication
class PolyflowServerApplication {

  @Bean
  @Primary
  fun serializer() =   XStreamSerializer.builder()
    .xStream(XStream().apply { this.addPermission(AnyTypePermission()) })
    .lenientDeserialization()
    .build()

  @QueryHandler
  fun query(query: TasksWithDataEntriesForUserQuery): TasksWithDataEntriesQueryResult = TasksWithDataEntriesQueryResult(
    listOf(
      TaskWithDataEntries(
        task = Task(
          id = "923847239",
          sourceReference = ProcessReference(
            instanceId = "instance-id",
            executionId = "exec-id",
            definitionId = "def-id",
            definitionKey = "def-key",
            name = "process name",
            applicationName = "test-application"
          ),
          taskDefinitionKey = "task-def",
          candidateUsers = setOf("kermit"),
          assignee = "piggy",
          candidateGroups = setOf("muppets"),
        ), dataEntries = listOf(
          DataEntry(
            entryType = "io.type",
            entryId = "0239480234",
            type = "data entry",
            applicationName = "test-application",
            name = "Data Entry for case 4711",
          )
        )
      )
    )
  )

}


