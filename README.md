# polyflow-spike

This example project contains 3 applications. One keeps business objects and publishes changes as dataEntries to polyflow, one starts processes with a single "do it" user task and the third one collects and correlates dataEntries and userTasks for an aggregated task list.

## Running it

- `docker compose up` to run the default axon server se
  - [dashboard](http://localhost:8024/#overview)
- start 3 apps in IDE
  - [PolyflowApplication](./polyflow-application/src/main/kotlin/PolyflowApplication.kt) - combined view for tasks and datEntries
    - [swagger](http://localhost:20000/)
  - [CamundaApplication](./camunda-application/src/main/kotlin/CamundaApplication.kt) - running processes with user tasks
    - [cockpit](http://localhost:20001/camunda/app/cockpit/default/#/dashboard)
    - [swagger](http://localhost:20001/swagger-ui/index.html)
  - [BusinessApplication](./business-application/src/main/kotlin/BusinessApplication.kt)
    - [swagger](http://localhost:20002/)

- create a business Object with key and name in BusinessApplication
- start a process with same key in CamundaApplication
- check tasks for user `kermit` in PolyflowApplication
