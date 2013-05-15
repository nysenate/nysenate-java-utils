nysenate-java-utils
===================

A collection of Java classes that are useful across multiple NY Senate projects.


Packaging
==============

To package and install as a maven dependency:

```
mvn clean package
mvn install:install-file -Dfile target/nysenate-java-utils-X.X.X.jar -DpomFile=target/pom.xml
```

To package all dependencies into the jar:

```
mvn assembly:assembly -DdescriptorId=jar-with-dependencies
```

To gather all dependencies into the ``target/dependency`` folder:

```
mvn dependency:copy-dependencies
```

