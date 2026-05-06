# Persistence Utils

A compact library that simplifies database integration for Axon Ivy processes. It
provides reusable DAOs, query and search helpers, transaction and entity-manager
utilities, audit/history support, and a demo project with test data to explore
features quickly.

### Key features
- Simplifies JPA/Hibernate usage with a reusable `GenericDAO` and query helpers.
- Server-side filtering, sorting and pagination via lazy data models for large tables.
- Ivy-aware entity manager and transaction helpers for safe process integration.
- Built-in audit/history and validation helpers for auditable entities.
- Demo application and test data to validate setup and explore features.

## Demo

Below are short, user-facing workflows that reproduce the demo scenarios provided
in the `persistence-utils-demo` module.

### Person Search
1. Open the demo and choose **Person Search** from the start page.
2. Enter search terms or use the column filters to narrow the results; the list
   is paginated and supports server-side sorting and filtering.
3. Click a row or the **Add** button to open the edit dialog, change fields and
   save your changes.

![Person Search](images/PersonSearch.png)

### Department Search
1. Open **Department Search** from the demo start page.
2. View the department list, use the paginator to browse, and click **Add** or
   the edit icon to create or edit a department.

![Department Search](images/DepartmentSearch.png)

### Prepare Test Data
1. The demo includes a simple test-data preparation UI to populate the demo
   database with example entities used by the sample dialogs.

![Testdata Preparation UI](images/Testdata.png)

## Setup

- Demo configuration: the demo module uses an in-memory demo datasource and
  `hibernate.hbm2ddl.auto` is configured for create-drop in the demo. For
  production, configure your datasource in `config/databases.yaml` and adjust
  `persistence.xml` accordingly.

## Components

### Exposed CALLABLE_SUB processes
No callable sub delivered by this extension.

### Form components
No form components delivered by this extension.

### Open API resources
No public OpenAPI specs delivered by this extension.

### Maven artifacts

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
