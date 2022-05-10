package io.github.jangalinski.camunda

import io.github.jangalinski.camunda.process.DummyProcess
import org.camunda.bpm.engine.runtime.ProcessInstance
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/dummy-processes")
class ProcessController(
  private val dummyProcess: DummyProcess
) {

  @PostMapping
  fun start(businessKey: String): DummyProcessInstance {
    return DummyProcessInstance(dummyProcess.start(businessKey).id)
  }

  data class DummyProcessInstance(val id:String)
}
