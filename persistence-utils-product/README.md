# Persistence Utils

Persistence Utils is a library based on the Java Persistence API (JPA) which helps you to start a
database-related Axon Ivy project quickly. It provides an abstraction DAO hierarchy to allow for more complex
database access and convenient use.

The library helps you create DAO classes of your entities which have all the APIs for fully database access.
Besides, it also helps you quickly create some typical Entity classes with boiler-plate requirements, like
simple auditing features.

**Key features**

- Simplify database integration for Axon Ivy projects with JPA-based utilities.
- Provide a DAO abstraction layer that reduces boilerplate and improves maintainability.
- Generate common entity classes with built-in auditing and basic audit fields.
- Include demo modules and tooling to accelerate development and testing.
- Provide Maven-compatible installers and dependencies for easy packaging and deployment.
- Integrate with project configuration and role-based access to secure data operations.

## Demo

Check the demo implementations provided in the demo modules. They showcase user-facing workflows such as Person Search, Department Search and Raise Salaries.

### Demo Workflows

#### Persistence Utils Demo (persistence-utils-demo)

##### Person Search

1. Launch the Person Search demo from the demo menu.
2. You'll see a Person Search dialog displaying a searchable list of persons and filters.
3. Enter search criteria or use the filter to find persons; select a person to view details.
4. Edit or save person details; confirmation will show updated data.

![Person Search](images/PersonSearch.png)

##### Department Search

1. Launch the Department Search demo from the demo menu.
2. You'll see a Department Search dialog listing departments and related controls.
3. Use the search and filters to find departments and review details.
4. Edit and save changes as required; confirmations show updated data.

![Department Search](images/DepartmentSearch.png)

##### Raise Salaries

1. Launch the Raise Salaries demo from the demo menu.
2. The demo executes a salary update action on sample data; confirm when prompted.
3. Review updated salary values in the person list or reports.

#### Persistence Utils Demo Tool (persistence-utils-demo-tool)

##### Execute test tasks

1. Launch Execute test tasks from the demo menu.
2. The Test Tasks dialog appears where you can run predefined test actions.
3. Execute tasks and review results and logs.

![Test data](images/Testdata.png)

## Setup

- **Roles:** Everybody (configured in config/roles.xml)
- **OpenAPI:** No information was delivered for this section.

### Variables

```yaml
# yaml-language-server: $schema=https://json-schema.axonivy.com/app/12.0.0/variables.json
# == Variables ==
# 
# You can define here your project Variables.
# If you want to define/override a Variable for a specific Environment, 
# add an additional ‘variables.yaml’ file in a subdirectory in the ‘Config’ folder: 
# '<project>/Config/_<environment>/variables.yaml
#
Variables:
#  myVariable: value
```

### Optional authentication and runtime sections

- No information was delivered for this section.

## Components

### Callable Subprocesses

- No connector processes delivered by this extension.

### Dialog Components

- No form components delivered by this extension.

### Web Services

- No information was delivered for this section.

### Maven Artifacts

1. persistence-utils

```xml
<dependency>
  <groupId>com.axonivy.utils.persistence</groupId>
  <artifactId>persistence-utils</artifactId>
  <type>jar</type>
</dependency>
```

2. persistence-utils-demo

```xml
<dependency>
  <groupId>com.axonivy.utils.persistence</groupId>
  <artifactId>persistence-utils-demo</artifactId>
  <type>iar</type>
</dependency>
```

3. persistence-utils-demo-tool

```xml
<dependency>
  <groupId>com.axonivy.utils.persistence</groupId>
  <artifactId>persistence-utils-demo-tool</artifactId>
  <type>iar</type>
</dependency>
```
