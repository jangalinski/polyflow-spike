package io.github.jangalinski.camunda.process

import io.holunda.polyflow.taskpool.collector.task.enricher.ProcessVariableCorrelation
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.runtime.ProcessInstance
import org.camunda.bpm.engine.variable.Variables
import org.springframework.stereotype.Component

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
  }


  fun start(businessKey : String = "1"): ProcessInstance {
    return runtimeService.startProcessInstanceByKey(KEY, businessKey, Variables.putValue("businessKey", businessKey))
  }

}

val taskDoStuffVariablesCorrelator = ProcessVariableCorrelation(
  processDefinitionKey = DummyProcess.KEY,
  mapOf(),
  mapOf("businessKey" to "business.object")
)
