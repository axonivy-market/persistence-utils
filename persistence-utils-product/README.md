# Persistence Utils

Persistence Utils is a library based on the Java Persistence API (JPA) which helps you start a
database-related Axon Ivy project quickly. It provides an abstraction DAO hierarchy to allow for more complex
database access and convenient use.

The library helps you create DAO classes for your entities which provide full database access APIs. It also helps
you quickly create typical Entity classes with common boilerplate requirements such as simple auditing features.

**Key features**

- Accelerate setup of JPA-based Axon Ivy projects with reusable DAO patterns and examples.
- Generate entity and DAO classes to reduce boilerplate and speed development.
- Provide built-in auditing and common persistence scaffolds for reliable data handling.
- Offer higher-level abstractions for queries and transactions to simplify database code.
- Include demo modules and example workflows to explore functionality quickly.
- Available as Maven artifacts for easy integration into your build pipeline.

## Demo

Check the demo implementations provided in `persistence-utils-demo` and `persistence-utils-demo-tool` for hands-on examples and walkthroughs.

### Demo Workflows

#### persistence-utils-demo (persistence-utils-demo)

##### Person Search

1. Launch the Person Search process from the demo menu.
2. You will see a dialog with a searchable table of persons (filter by name, department, status).

![Person Search](images/PersonSearch.png)

3. Use the search or filters to narrow results and select a person to view or edit details.
4. Save changes to persist updates to the database.

##### Department Search

1. Launch the Department Search process from the demo menu.
2. The search form lets you find departments by name and view matching results.

![Department Search](images/DepartmentSearch.png)

3. Optionally add or delete departments using the UI.
4. Review changes and confirm persistence.

##### Raise Salaries

1. Launch the Raise Salaries demo from the demo menu.
2. Configure criteria or accept defaults to select the affected employees.
3. Execute the action to apply salary changes.
4. Review confirmation and updated salaries.

#### persistence-utils-demo-tool (persistence-utils-demo-tool)

##### Execute test tasks

1. Launch the Execute test tasks process from the demo menu.
2. Choose options (for example, clean DB and reload test data) and prepare the test environment.
3. Use Export to download the database snapshot or run other maintenance actions.

![Test data](images/Testdata.png)

4. Review messages and proceed when done.

## Setup

- **Roles:** Everybody (configured in config/roles.xml)
- **OpenAPI:** No information was delivered for this section.

### Variables

```
@variables.yaml@
```

- No information was delivered for this section.

## Components

### Callable Subprocesses

- No connector processes delivered by this extension.

### Dialog Components

#### PersonSearch - Person Search
- **Namespace:** com.axonivy.utils.persistence.demo.PersonSearch
- **Component type:** UI dialog
- **Fields:** - (none)
- **Purpose:** Search and manage persons with fast, filterable results and inline edit capabilities.

#### DepartmentSearch - Department Search
- **Namespace:** com.axonivy.utils.persistence.demo.DepartmentSearch
- **Component type:** UI dialog
- **Fields:** - (none)
- **Purpose:** Find and manage departments; supports listing, add and delete operations.

#### Info - Info dialog
- **Namespace:** com.axonivy.utils.persistence.demo.components.Info
- **Component type:** Component dialog
- **Fields:** - (none)
- **Purpose:** Small info dialog component used to display contextual information.

#### TestTasks - Test Tasks
- **Namespace:** com.axonivy.utils.persistence.demo.tool.TestTasks
- **Component type:** UI dialog
- **Fields:** - (none)
- **Purpose:** Utilities to prepare test data, export the database, and execute maintenance tasks.

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

2. persistence-utils-demo *(optional)*

```xml
<dependency>
  <groupId>com.axonivy.utils.persistence</groupId>
  <artifactId>persistence-utils-demo</artifactId>
  <type>iar</type>
</dependency>
```

3. persistence-utils-demo-tool *(optional)*

```xml
<dependency>
  <groupId>com.axonivy.utils.persistence</groupId>
  <artifactId>persistence-utils-demo-tool</artifactId>
  <type>iar</type>
</dependency>
```
