# Persistence Utils

**Persistence Utils** is a library based on the Java Persistence API (JPA) which will help you to start a
database related Axon Ivy project quickly. It provides an abstraction DAO hierarchy to allow for more complex
database access and convenient use.

The library helps you create DAO classes of your entities which have all the APIs for fully database access.
Besides, it also helps you quickly create some typical Entity classes with boiler-plate requirements, like
simple auditing features.

## Key features

- Simplify database access with ready-to-use DAO classes that reduce boilerplate code and speed development.
- Quickly create entity classes with common patterns (auditing, lifecycle hooks) out of the box.
- Includes demo modules and sample workflows (Person Search, Department Search, Raise Salaries) to illustrate usage.
- Provides Maven artifacts and IAR/JAR packaging for easy installation and workspace import.
- Configurable roles and a variables-based configuration model to adapt to different environments.
- Built on a JPA-based architecture suitable for complex queries and scalable persistence needs.

## Demo

Check the demo implementations provided in the demo modules: `persistence-utils-demo` and `persistence-utils-demo-tool`.

### Demo workflows

#### Persistence Utils Demo (persistence-utils-demo)

##### Person Search

1. Launch the Person Search demo from the demo menu.
2. You'll see a dialog where you can enter search criteria (name, department, etc.).
3. Enter the search terms and run the search.
4. Review results and select an item to view details.

![Person Search](images/PersonSearch.png)

##### Department Search

1. Launch the Department Search demo from the demo menu.
2. A department selection dialog appears showing columns and filters.
3. Use the filters to narrow results and click to view or edit a department.
4. Review results or add a new department.

![Department Search](images/DepartmentSearch.png)

##### Raise Salaries

1. Launch the Raise Salaries workflow from the demo menu.
2. Confirm the action to increase salaries as configured.
3. Observe the confirmation and verify updated salaries in the system.

#### Persistence Utils Demo Tool (persistence-utils-demo-tool)

##### Execute test tasks

1. Launch Execute test tasks from the demo menu.
2. Confirm to run the test tasks.
3. Review test output or logs after execution.

![Execute test tasks](images/Testdata.png)

## Setup

- **Roles:** Everybody (configured in config/roles.xml)
- **OpenAPI:** No public OpenAPI specs delivered by this extension.

### Variables

- No variables were detected.

- No information was delivered for this section.

## Components

### Connector Processes

- No connector processes delivered by this extension.

### Form Components

- No form components delivered by this extension.

### Maven artifacts

1. com.axonivy.utils.persistence:persistence-utils

```xml
<dependency>
  <groupId>com.axonivy.utils.persistence</groupId>
  <artifactId>persistence-utils</artifactId>
  <version>@version@</version>
  <type>jar</type>
</dependency>
```

2. com.axonivy.utils.persistence:persistence-utils-demo

```xml
<dependency>
  <groupId>com.axonivy.utils.persistence</groupId>
  <artifactId>persistence-utils-demo</artifactId>
  <version>@version@</version>
  <type>iar</type>
</dependency>
```

3. com.axonivy.utils.persistence:persistence-utils-demo-tool

```xml
<dependency>
  <groupId>com.axonivy.utils.persistence</groupId>
  <artifactId>persistence-utils-demo-tool</artifactId>
  <version>@version@</version>
  <type>iar</type>
</dependency>
```
