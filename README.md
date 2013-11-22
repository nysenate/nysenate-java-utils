nysenate-java-utils
===================

A collection of Java classes that are useful across multiple NY Senate projects.


Packaging
==============

To package and install as a maven dependency:

```
mvn clean package
mvn install:install-file -Dfile=target/nysenate-java-utils-X.X.X.jar -DpomFile=pom.xml
   -- or --
mvn install:install-file -Dfile=target/nysenate-java-utils-1.0.1.jar -DgroupId=gov.nysenate -DartifactId=nysenate-java-utils -Dversion=1.0.1 -Dpackaging=jar -DgeneratePom=true

```

To package all dependencies into the jar:

```
mvn assembly:assembly -DdescriptorId=jar-with-dependencies
```

To gather all dependencies into the ``target/dependency`` folder:

```
mvn dependency:copy-dependencies
```

