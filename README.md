# bare-metal-flow

**bare-metal-flow** or **Anvil**, have not settled on a name yet... is a fragment of a fully functioning app, that will eventually become a
fully functioning app.

it is a showcase app targeting recruiters and tech interviewers, but in time, as I add more features it'll turn it into a full
application.

I made also a front end code that will be showcased in videos and screenshots, but it is a licensed template for which I have a professional
use license but not yet a FOSS one.

for the moment the project looks like this:
![hero-kanban-dark.jpeg](hero-kanban-dark.jpeg)

to understand what it does and why this exists in more detail check the accompanying website.<br>
[bare metal code - Anvil](https://www.baremetalcode.com/bmc_showcase/)

---

## bare-metal-flow tech

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:

```shell script
mvn package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
mvn package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using
```shell script
java -jar target/*-runner.jar
```

## Creating a native executable

You can create a native executable using:

```shell script
mvn package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
mvn package -DskipTests=true -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/bare-metal-flow-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- Hibernate ORM ([guide](https://quarkus.io/guides/hibernate-orm)): Define your persistent model with Hibernate ORM and
  JPA
- Hibernate Validator ([guide](https://quarkus.io/guides/validation)): Validate object properties (field, getter) and method parameters for
  your beans (REST, CDI, Jakarta Persistence)
- Reactive PostgreSQL client ([guide](https://quarkus.io/guides/reactive-sql-clients)): Connect to the PostgreSQL database using the
  reactive pattern
- Cache ([guide](https://quarkus.io/guides/cache)): Enable application data caching in CDI beans
- Micrometer Registry Prometheus ([guide](https://quarkus.io/guides/micrometer)): Enable Prometheus support for
  Micrometer
- YAML Configuration ([guide](https://quarkus.io/guides/config#yaml)): Use YAML to configure your Quarkus application
- Liquibase ([guide](https://quarkus.io/guides/liquibase)): Handle your database schema migrations with Liquibase
- Logging JSON ([guide](https://quarkus.io/guides/logging#json-logging)): Add JSON formatter for console logging
- Micrometer metrics ([guide](https://quarkus.io/guides/micrometer)): Instrument the runtime and your application with
  dimensional metrics using Micrometer.
- JDBC Driver - PostgreSQL ([guide](https://quarkus.io/guides/datasource)): Connect to the PostgreSQL database via JDBC

## Provided Code

### YAML Config

Configure your application with YAML

[Related guide section...](https://quarkus.io/guides/config-reference#configuration-examples)

The Quarkus application configuration is located in `src/main/resources/application.yml`.

### Hibernate ORM

Create your first JPA entity

[Related guide section...](https://quarkus.io/guides/hibernate-orm)

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
