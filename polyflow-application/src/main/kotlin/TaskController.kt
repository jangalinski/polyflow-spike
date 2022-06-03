package io.github.jangalinski.polyflow

import io.holunda.polyflow.view.QueryGatewayExt.tasksForApplication
import io.holunda.polyflow.view.QueryGatewayExt.tasksWithDataEntriesForUser
import io.holunda.polyflow.view.auth.User
import io.holunda.polyflow.view.query.task.TaskQueryResult
import io.holunda.polyflow.view.query.task.TasksForApplicationQuery
import io.holunda.polyflow.view.query.task.TasksWithDataEntriesForUserQuery
import io.holunda.polyflow.view.query.task.TasksWithDataEntriesQueryResult
import org.axonframework.queryhandling.QueryGateway
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tasks")
class TaskController(
  val queryGateway: QueryGateway
) {
  companion object {
    const val CAMUNDA_APP_NAME = "camunda-application"
  }

  @GetMapping
  fun findAll(): TaskQueryResult {
    val query = TasksForApplicationQuery(
      applicationName = CAMUNDA_APP_NAME
    )

    return queryGateway.tasksForApplication(query).join()
  }

  @GetMapping("/user/{userId}")
  fun findForUser(@PathVariable("userId") userId: String): TasksWithDataEntriesQueryResult {
    val query = TasksWithDataEntriesForUserQuery(user = User(username = userId, setOf()))

    return queryGateway.tasksWithDataEntriesForUser(query).join()
  }

}

