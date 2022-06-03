package io.github.jangalinski.camunda.process

import io.holunda.camunda.bpm.data.CamundaBpmData.builder as variables
import io.holunda.camunda.bpm.data.CamundaBpmDataKotlin
import io.holunda.polyflow.taskpool.collector.task.enricher.ProcessVariableCorrelation
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.runtime.ProcessInstance
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDateTime
import java.util.Date

@Component(DummyProcess.NAME)
class DummyProcess(
  private val runtimeService: RuntimeService
) {
  companion object {
    const val KEY = "process_dummy"
    const val NAME = "dummyProcess"

    object ELEMENTS {
      const val USER_TASK = "task_do_stuff"
    }

    object VARIABLES {
      val businessKey = CamundaBpmDataKotlin.stringVariable("businessKey")
      val foo = CamundaBpmDataKotlin.customVariable<Foo>("foo")
    }
  }


  fun start(businessKey : String = "1"): ProcessInstance {
    return runtimeService.startProcessInstanceByKey(KEY, businessKey, variables()
      .set(VARIABLES.businessKey, businessKey)
      .set(VARIABLES.foo, Foo("jan", LocalDateTime.now()))
      .build())
  }

}

data class Foo(val name: String, val time: LocalDateTime)

val taskDoStuffVariablesCorrelator = ProcessVariableCorrelation(
  processDefinitionKey = DummyProcess.KEY,
  mapOf(),
  mapOf("businessKey" to "business.object")
)
