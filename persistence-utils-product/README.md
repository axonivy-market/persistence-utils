# Persistence Utils
Persistence Utils provides reusable components to simplify database access in Axon Ivy projects. The product speeds up development by offering a consistent data-access layer, ready-made search and audit helpers, and demo applications that illustrate common patterns.

### Key features
- Save development time with a reusable data-access layer and ready-made query helpers.
- Reliable, thread-safe session management integrated with Axon Ivy to avoid common session/transaction pitfalls.
- Powerful server-side search and filtering for responsive, pageable lists and efficient data retrieval.
- Built-in audit/history support and helpful utilities for JSON, string handling and reflection.
- Ready-to-run demo apps and test data to explore features quickly and reduce onboarding time.

## Demo

### Person Search
1. Launch the "Person Search" demo from the demo start page.
2. You see a searchable, pageable list of people with column filters and sorting. Use the filters to narrow results.
3. Click "Edit" on a person to open the detail dialog. Change fields and save; validation may show Errors or Warnings. If only Warnings appear, use "Save Anyway" to persist changes.
4. Use the "Ivy User" control to sync a person to an Ivy user when required.

![Person Search UI](images/PersonSearch.png "Person Search UI")

### Department Search
1. Launch the "Department Search" demo.
2. The list shows all departments and supports client-side filtering and sorting. Add, edit, or delete entries using the provided controls.
3. Deleting a department is performed logically (soft delete) and can be undone by adjusting the data if required.

![Department Search UI](images/DepartmentSearch.png "Department Search UI")

### Raise Salaries
1. Start the "Raise Salaries" process to execute the example salary update.
2. This workflow runs a demo script that increases salaries for the included test data. Review results in the Person Search UI after execution.

## Components

### Maven artifacts
1. com.axonivy.utils.persistence:persistence-utils

```xml
<dependency>
  <groupId>com.axonivy.utils.persistence</groupId>
  <artifactId>persistence-utils</artifactId>
  <type>jar</type>
</dependency>
```

2. com.axonivy.utils.persistence:persistence-utils-demo

```xml
<dependency>
  <groupId>com.axonivy.utils.persistence</groupId>
  <artifactId>persistence-utils-demo</artifactId>
  <type>iar</type>
</dependency>
```

3. com.axonivy.utils.persistence:persistence-utils-demo-tool

```xml
<dependency>
  <groupId>com.axonivy.utils.persistence</groupId>
  <artifactId>persistence-utils-demo-tool</artifactId>
  <type>iar</type>
</dependency>
```
