nysenate-java-utils
===================

A collection of Java classes that are useful across multiple NY Senate projects.


Packaging
==============

To package and install as a maven dependency:

```
mvn compile

```

replace X.X.X with version number
```
mvn clean package
mvn install:install-file -Dfile=target/nysenate-java-utils-X.X.X.jar -DpomFile=pom.xml
   -- or --
mvn install:install-file -Dfile=target/nysenate-java-utils-X.X.X.jar -DgroupId=gov.nysenate -DartifactId=nysenate-java-utils -Dversion=X.X.X -Dpackaging=jar -DgeneratePom=true

```

To package all dependencies into the jar:

```
mvn assembly:assembly -DdescriptorId=jar-with-dependencies
```

To gather all dependencies into the ``target/dependency`` folder:

```
mvn dependency:copy-dependencies
```

