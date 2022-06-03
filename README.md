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
  - [BusinessApplication](./business-application/src/main/kotlin/BusinessApplication.kt) - publishing data entry changes that should be correlated with tasks
    - [swagger](http://localhost:20002/)


```
2022-06-03 16:06:25.849  INFO 96343 --- [96343@macleod-0] i.a.a.c.query.impl.QueryChannelImpl      : QueryChannel for context 'default' connected, 12 registrations resubscribed
2022-06-03 16:06:34.324  INFO 96343 --- [em-processor]-0] o.a.e.TrackingEventProcessor             : Worker assigned to segment Segment[0/0] for processing
2022-06-03 16:06:34.326  INFO 96343 --- [em-processor]-0] o.a.e.TrackingEventProcessor             : Using current Thread for last segment worker: TrackingSegmentWorker{processor=in-mem-processor, segment=Segment[0/0]}
2022-06-03 16:06:34.326  INFO 96343 --- [em-processor]-0] o.a.e.TrackingEventProcessor             : Fetched token: null for segment: Segment[0/0]
2022-06-03 16:07:13.269  INFO 96343 --- [io-20000-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2022-06-03 16:07:13.270  INFO 96343 --- [io-20000-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2022-06-03 16:07:13.271  INFO 96343 --- [io-20000-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 1 ms
2022-06-03 16:07:33.961 ERROR 96343 --- [io-20000-exec-2] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is java.util.concurrent.CompletionException: org.axonframework.serialization.SerializationException: Error while deserializing object] with root cause

java.lang.UnsupportedOperationException: null
	at java.base/java.util.AbstractCollection.add(AbstractCollection.java:267) ~[na:na]
	at com.fasterxml.jackson.databind.deser.std.StringCollectionDeserializer.deserialize(StringCollectionDeserializer.java:203) ~[jackson-databind-2.13.3.jar:2.13.3]
	at com.fasterxml.jackson.databind.deser.std.StringCollectionDeserializer.deserialize(StringCollectionDeserializer.java:25) ~[jackson-databind-2.13.3.jar:2.13.3]
	at com.fasterxml.jackson.databind.deser.impl.SetterlessProperty.deserializeAndSet(SetterlessProperty.java:134) ~[jackson-databind-2.13.3.jar:2.13.3]
	at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserialize(BeanDeserializer.java:277) ~[jackson-databind-2.13.3.jar:2.13.3]
	at com.fasterxml.jackson.databind.deser.BeanDeserializer._deserializeUsingPropertyBased(BeanDeserializer.java:462) ~[jackson-databind-2.13.3.jar:2.13.3]
	at com.fasterxml.jackson.databind.deser.BeanDeserializerBase.deserializeFromObjectUsingNonDefault(BeanDeserializerBase.java:1405) ~[jackson-databind-2.13.3.jar:2.13.3]
	at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserializeFromObject(BeanDeserializer.java:351) ~[jackson-databind-2.13.3.jar:2.13.3]
	at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserialize(BeanDeserializer.java:184) ~[jackson-databind-2.13.3.jar:2.13.3]
	at com.fasterxml.jackson.databind.deser.std.CollectionDeserializer._deserializeFromArray(CollectionDeserializer.java:355) ~[jackson-databind-2.13.3.jar:2.13.3]
	at com.fasterxml.jackson.databind.deser.std.CollectionDeserializer.deserialize(CollectionDeserializer.java:244) ~[jackson-databind-2.13.3.jar:2.13.3]
	at com.fasterxml.jackson.databind.deser.std.CollectionDeserializer.deserialize(CollectionDeserializer.java:28) ~[jackson-databind-2.13.3.jar:2.13.3]
	at com.fasterxml.jackson.databind.deser.SettableBeanProperty.deserialize(SettableBeanProperty.java:542) ~[jackson-databind-2.13.3.jar:2.13.3]
```

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
