package io.github.jangalinski.polyflow

import io.holixon.axon.gateway.query.QueryResponseMessageResponseType
import io.holunda.polyflow.view.QueryGatewayExt.dataEntries
import io.holunda.polyflow.view.query.data.DataEntriesQuery
import io.holunda.polyflow.view.query.data.DataEntriesQueryResult
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/data-entries")
class DataEntryController(
  private val queryGateway: QueryGateway
) {

  @GetMapping
  fun findAll(): DataEntriesQueryResult {
    return queryGateway.query(DataEntriesQuery(), QueryResponseMessageResponseType.queryResponseMessageResponseType<DataEntriesQueryResult>()).join()
  }

}
