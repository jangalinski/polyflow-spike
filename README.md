# polyflow-spike

getting it to work - hopefully

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



## User journey

how it feels to set up a fresh running minimal example with polyflow

### polyflow application

- add 2 dependencies to dependencyManagement
- add "polyflow-taskpool-core", add EnablePolyflowTaskpool
- 

### camunda application

- docs say: "It includes all process application modules and provides meaningful defaults for their options.". but

`2022-05-10 08:30:14.246  WARN 14332 --- [           main] ConfigServletWebServerApplicationContext : Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'io.holunda.polyflow.datapool.DataEntrySenderConfiguration': Unsatisfied dependency expressed through constructor parameter 0; nested exception is org.springframework.boot.context.properties.ConfigurationPropertiesBindException: Error creating bean with name 'polyflow.integration.sender.data-entry-io.holunda.polyflow.datapool.DataEntrySenderProperties': Could not bind properties to 'DataEntrySenderProperties' : prefix=polyflow.integration.sender.data-entry, ignoreInvalidFields=false, ignoreUnknownFields=true; nested exception is org.springframework.boot.context.properties.bind.BindException: Failed to bind properties under 'polyflow.integration.sender.data-entry' to io.holunda.polyflow.datapool.DataEntrySenderProperties`

- does not work with camunda 7.17 - when changing the version to 7.17.0, just having the polyflow/camunda spring boot starter on the classpath leads to

```
2022-05-10 08:33:56.918 ERROR 14409 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Exception sending context initialized event to listener instance of class [org.camunda.bpm.cockpit.impl.web.bootstrap.CockpitContainerBootstrap]

java.lang.NoSuchMethodError: 'org.camunda.bpm.engine.impl.telemetry.dto.ApplicationServerImpl org.camunda.bpm.engine.impl.telemetry.PlatformTelemetryRegistry.getApplicationServer()'
at org.camunda.bpm.engine.rest.util.WebApplicationUtil.setApplicationServer(WebApplicationUtil.java:31) ~[camunda-engine-rest-jaxrs2-7.17.0.jar:7.17.0]
at org.camunda.bpm.cockpit.impl.web.bootstrap.CockpitContainerBootstrap.contextInitialized(CockpitContainerBootstrap.java:43) ~[camunda-webapp-7.17.0-classes.jar:7.17.0]
at org.apache.catalina.core.StandardContext.listenerStart(StandardContext.java:4766) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
at org.apache.catalina.core.StandardContext.startInternal(StandardContext.java:5230) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1396) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1386) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264) ~[na:na]
at org.apache.tomcat.util.threads.InlineExecutorService.execute(InlineExecutorService.java:75) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
at java.base/java.util.concurrent.AbstractExecutorService.submit(AbstractExecutorService.java:145) ~[na:na]
at org.apache.catalina.core.ContainerBase.startInternal(ContainerBase.java:919) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
at org.apache.catalina.core.StandardHost.startInternal(StandardHost.java:835) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1396) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1386) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264) ~[na:na]
at org.apache.tomcat.util.threads.InlineExecutorService.execute(InlineExecutorService.java:75) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
at java.base/java.util.concurrent.AbstractExecutorService.submit(AbstractExecutorService.java:145) ~[na:na]
at org.apache.catalina.core.ContainerBase.startInternal(ContainerBase.java:919) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
at org.apache.catalina.core.StandardEngine.startInternal(StandardEngine.java:263) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
at org.apache.catalina.core.StandardService.startInternal(StandardService.java:432) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
at org.apache.catalina.core.StandardServer.startInternal(StandardServer.java:927) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:183) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
at org.apache.catalina.startup.Tomcat.start(Tomcat.java:486) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
at org.springframework.boot.web.embedded.tomcat.TomcatWebServer.initialize(TomcatWebServer.java:123) ~[spring-boot-2.6.7.jar:2.6.7]
```

### auto login

```


### yaml



## Why different query/result pattern fpr tasks and dataEntries?
