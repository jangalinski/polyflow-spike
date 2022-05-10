package io.github.jangalinski.polyflow.axon

import io.holunda.polyflow.view.query.data.DataEntriesQuery
import io.holunda.polyflow.view.query.data.DataEntriesQueryResult
import io.holunda.polyflow.view.query.task.TaskQueryResult
import io.holunda.polyflow.view.query.task.TasksForApplicationQuery
import io.holunda.polyflow.view.query.task.TasksWithDataEntriesForUserQuery
import io.holunda.polyflow.view.query.task.TasksWithDataEntriesQueryResult
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import java.util.concurrent.CompletableFuture

object PolyFlowQueryExt {

  fun QueryGateway.taskForApplication(query: TasksForApplicationQuery): CompletableFuture<TaskQueryResult> = query(query,
    ResponseTypes.instanceOf(TaskQueryResult::class.java)
  )

  fun QueryGateway.tasksWithDataEntriesForUser(query: TasksWithDataEntriesForUserQuery): CompletableFuture<TasksWithDataEntriesQueryResult> = query(query,
    ResponseTypes.instanceOf(TasksWithDataEntriesQueryResult::class.java)
  )

  fun QueryGateway.dataEntries(query: DataEntriesQuery) = query(query, ResponseTypes.instanceOf(DataEntriesQueryResult::class.java))
}
