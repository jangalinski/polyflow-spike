# polyflow-spike

getting it to work - hopefully

## Running it

- `docker compose up` to run the default axon server se
  - [dashboard](http://localhost:8024/#overview)
- start 3 apps in IDE
  - [PolyflowApplication](./polyflow-application/src/main/kotlin/PolyflowApplication.kt) - combined view for tasks and datEntries
    - [swagger](http://localhost:10000/)
  - [CamundaApplication](./camunda-application/src/main/kotlin/CamundaApplication.kt) - running processes with user tasks
    - [cockpit](http://localhost:10001/camunda/app/cockpit/default/#/dashboard)
    - [swagger](http://localhost:10001/swagger-ui/index.html)



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

without camunda/polyflow starter : auto login works.

with polyflw-starter:

```

java.lang.reflect.InaccessibleObjectException: Unable to make protected final java.lang.Class java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int) throws java.lang.ClassFormatError accessible: module java.base does not "opens java.lang" to unnamed module @7ec7ffd3
	at java.base/java.lang.reflect.AccessibleObject.checkCanSetAccessible(AccessibleObject.java:354) ~[na:na]
	at java.base/java.lang.reflect.AccessibleObject.checkCanSetAccessible(AccessibleObject.java:297) ~[na:na]
	at java.base/java.lang.reflect.Method.checkCanSetAccessible(Method.java:199) ~[na:na]
	at java.base/java.lang.reflect.Method.setAccessible(Method.java:193) ~[na:na]
	at com.sun.xml.bind.v2.runtime.reflect.opt.Injector$1.run(Injector.java:177) ~[jaxb-impl-2.2.4.jar:2.2.4]
	at com.sun.xml.bind.v2.runtime.reflect.opt.Injector$1.run(Injector.java:174) ~[jaxb-impl-2.2.4.jar:2.2.4]
	at java.base/java.security.AccessController.doPrivileged(AccessController.java:318) ~[na:na]
	at com.sun.xml.bind.v2.runtime.reflect.opt.Injector.<clinit>(Injector.java:172) ~[jaxb-impl-2.2.4.jar:2.2.4]
	at com.sun.xml.bind.v2.runtime.reflect.opt.AccessorInjector.prepare(AccessorInjector.java:83) ~[jaxb-impl-2.2.4.jar:2.2.4]
	at com.sun.xml.bind.v2.runtime.reflect.opt.OptimizedAccessorFactory.get(OptimizedAccessorFactory.java:176) ~[jaxb-impl-2.2.4.jar:2.2.4]
	at com.sun.xml.bind.v2.runtime.reflect.Accessor$FieldReflection.optimize(Accessor.java:282) ~[jaxb-impl-2.2.4.jar:2.2.4]
	at com.sun.xml.bind.v2.runtime.property.ArrayProperty.<init>(ArrayProperty.java:69) ~[jaxb-impl-2.2.4.jar:2.2.4]
	at com.sun.xml.bind.v2.runtime.property.ArrayERProperty.<init>(ArrayERProperty.java:88) ~[jaxb-impl-2.2.4.jar:2.2.4]
	at com.sun.xml.bind.v2.runtime.property.ArrayElementProperty.<init>(ArrayElementProperty.java:100) ~[jaxb-impl-2.2.4.jar:2.2.4]
	at com.sun.xml.bind.v2.runtime.property.ArrayElementNodeProperty.<init>(ArrayElementNodeProperty.java:62) ~[jaxb-impl-2.2.4.jar:2.2.4]
	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method) ~[na:na]
	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:77) ~[na:na]
	at java.base/jdk.internal.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45) ~[na:na]
	at java.base/java.lang.reflect.Constructor.newInstanceWithCaller(Constructor.java:499) ~[na:na]
	at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:480) ~[na:na]
	at com.sun.xml.bind.v2.runtime.property.PropertyFactory.create(PropertyFactory.java:128) ~[jaxb-impl-2.2.4.jar:2.2.4]
	at com.sun.xml.bind.v2.runtime.ClassBeanInfoImpl.<init>(ClassBeanInfoImpl.java:181) ~[jaxb-impl-2.2.4.jar:2.2.4]
	at com.sun.xml.bind.v2.runtime.JAXBContextImpl.getOrCreate(JAXBContextImpl.java:509) ~[jaxb-impl-2.2.4.jar:2.2.4]
	at com.sun.xml.bind.v2.runtime.JAXBContextImpl.<init>(JAXBContextImpl.java:326) ~[jaxb-impl-2.2.4.jar:2.2.4]
	at com.sun.xml.bind.v2.runtime.JAXBContextImpl.<init>(JAXBContextImpl.java:141) ~[jaxb-impl-2.2.4.jar:2.2.4]
	at com.sun.xml.bind.v2.runtime.JAXBContextImpl$JAXBContextBuilder.build(JAXBContextImpl.java:1157) ~[jaxb-impl-2.2.4.jar:2.2.4]
	at com.sun.xml.bind.v2.ContextFactory.createContext(ContextFactory.java:145) ~[jaxb-impl-2.2.4.jar:2.2.4]
	at com.sun.xml.bind.v2.ContextFactory.createContext(ContextFactory.java:236) ~[jaxb-impl-2.2.4.jar:2.2.4]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77) ~[na:na]
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:568) ~[na:na]
	at javax.xml.bind.ContextFinder.newInstance(ContextFinder.java:217) ~[jaxb-api-2.3.1.jar:2.3.0]
	at javax.xml.bind.ContextFinder.newInstance(ContextFinder.java:175) ~[jaxb-api-2.3.1.jar:2.3.0]
	at javax.xml.bind.ContextFinder.find(ContextFinder.java:353) ~[jaxb-api-2.3.1.jar:2.3.0]
	at javax.xml.bind.JAXBContext.newInstance(JAXBContext.java:508) ~[jaxb-api-2.3.1.jar:2.3.0]
	at javax.xml.bind.JAXBContext.newInstance(JAXBContext.java:465) ~[jaxb-api-2.3.1.jar:2.3.0]
	at org.glassfish.jersey.server.wadl.internal.WadlApplicationContextImpl.getJAXBContextFromWadlGenerator(WadlApplicationContextImpl.java:121) ~[jersey-server-2.35.jar:na]
	at org.glassfish.jersey.server.wadl.internal.WadlApplicationContextImpl.isJaxbImplAvailable(WadlApplicationContextImpl.java:270) ~[jersey-server-2.35.jar:na]
	at org.glassfish.jersey.server.wadl.WadlFeature.configure(WadlFeature.java:65) ~[jersey-server-2.35.jar:na]
	at org.glassfish.jersey.model.internal.CommonConfig.configureFeatures(CommonConfig.java:728) ~[jersey-common-2.35.jar:na]
	at org.glassfish.jersey.model.internal.CommonConfig.configureMetaProviders(CommonConfig.java:647) ~[jersey-common-2.35.jar:na]
	at org.glassfish.jersey.server.ResourceConfig.configureMetaProviders(ResourceConfig.java:824) ~[jersey-server-2.35.jar:na]
	at org.glassfish.jersey.server.ApplicationHandler.initialize(ApplicationHandler.java:332) ~[jersey-server-2.35.jar:na]
	at org.glassfish.jersey.server.ApplicationHandler.lambda$initialize$1(ApplicationHandler.java:297) ~[jersey-server-2.35.jar:na]
	at org.glassfish.jersey.internal.Errors.process(Errors.java:292) ~[jersey-common-2.35.jar:na]
	at org.glassfish.jersey.internal.Errors.process(Errors.java:274) ~[jersey-common-2.35.jar:na]
	at org.glassfish.jersey.internal.Errors.processWithException(Errors.java:232) ~[jersey-common-2.35.jar:na]
	at org.glassfish.jersey.server.ApplicationHandler.initialize(ApplicationHandler.java:296) ~[jersey-server-2.35.jar:na]
	at org.glassfish.jersey.server.ApplicationHandler.<init>(ApplicationHandler.java:261) ~[jersey-server-2.35.jar:na]
	at org.glassfish.jersey.servlet.WebComponent.<init>(WebComponent.java:311) ~[jersey-container-servlet-core-2.35.jar:na]
	at org.glassfish.jersey.servlet.ServletContainer.init(ServletContainer.java:154) ~[jersey-container-servlet-core-2.35.jar:na]
	at org.glassfish.jersey.servlet.ServletContainer.init(ServletContainer.java:347) ~[jersey-container-servlet-core-2.35.jar:na]
	at javax.servlet.GenericServlet.init(GenericServlet.java:158) ~[tomcat-embed-core-9.0.62.jar:4.0.FR]
	at org.apache.catalina.core.StandardWrapper.initServlet(StandardWrapper.java:1164) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
	at org.apache.catalina.core.StandardWrapper.loadServlet(StandardWrapper.java:1117) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
	at org.apache.catalina.core.StandardWrapper.allocate(StandardWrapper.java:788) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:128) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:97) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:541) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:135) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:78) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:360) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:399) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:65) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:890) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1743) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
	at org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1191) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
	at org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) ~[tomcat-embed-core-9.0.62.jar:9.0.62]
	at java.base/java.lang.Thread.run(Thread.java:833) ~[na:na]

```


### yaml

properties are not recognized by auto completion

application name is required although I set enabled to false

      data-entry:
        enabled: false
        application-name: ${spring.application.name}  # default


- if it defaults to `spring.application.name` anyway, why do I have to set it at all?


