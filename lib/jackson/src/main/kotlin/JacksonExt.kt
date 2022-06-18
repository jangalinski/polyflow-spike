package io.github.jangalinski.lib.jackson

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.holixon.axon.gateway.jackson.module.AxonGatewayJacksonModule
import io.holunda.camunda.taskpool.api.business.AuthorizationChange
import io.holunda.polyflow.bus.jackson.KotlinTypeInfo
import io.holunda.polyflow.bus.jackson.configurePolyflowJacksonObjectMapper
import io.holunda.polyflow.view.DataEntry
import io.holunda.polyflow.view.filter.Criterion

object JacksonExt {

  fun defaultJacksonObjectMapper(): ObjectMapper = jacksonObjectMapper().apply {
    registerModule(Jdk8Module())
    registerModule(JavaTimeModule())
    disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
  }.configurePolyflowJacksonObjectMapper()
    .additionalConfig()

  fun ObjectMapper.additionalConfig(): ObjectMapper = apply {
    addMixIn(AuthorizationChange::class.java, KotlinTypeInfo::class.java)
    addMixIn(Criterion::class.java, KotlinTypeInfo::class.java)
    addMixIn(DataEntry::class.java, DataEntryMixin::class.java)
    registerModule(AxonGatewayJacksonModule())
  }

  @JsonIgnoreProperties(value = ["identity"])
  interface DataEntryMixin {

  }
}
