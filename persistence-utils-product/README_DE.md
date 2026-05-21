# Persistence Utils

Persistence Utils ist eine Bibliothek, die auf der Java Persistence API (JPA) basiert und dir hilft, ein datenbankbezogenes Axon Ivy‑Projekt schnell zu starten. Sie stellt eine abstrakte DAO‑Hierarchie bereit, die komplexeren Datenbankzugriff ermöglicht und die Nutzung vereinfacht.

Die Bibliothek unterstützt dich beim Erstellen von DAO‑Klassen für deine Entitäten, die alle APIs für den vollständigen Datenbankzugriff enthalten. Außerdem hilft sie, typische Entitätsklassen mit Boilerplate‑Anforderungen wie einfachen Audit‑Funktionen schnell zu erstellen.

## Hauptmerkmale

- Vereinfachter Datenbankzugriff mit wiederverwendbaren DAO‑Klassen, die Boilerplate‑Code reduzieren und die Entwicklung beschleunigen.
- Schnelles Erstellen von Entitätsklassen mit Standardmustern (Auditing, Lifecycle‑Hooks).
- Enthält Demo‑Module und Beispiel‑Workflows (Personensuche, Abteilungssuche, Gehaltserhöhungen) zur Veranschaulichung.
- Bereitstellung als Maven‑Artefakte und IAR/JAR‑Pakete für einfache Installation und Workspace‑Import.
- Konfigurierbare Rollen und ein variables Konfigurationsmodell, um verschiedene Umgebungen zu unterstützen.
- JPA‑basierte Architektur, geeignet für komplexe Abfragen und skalierbare Persistenzanforderungen.

## Demo

Schau dir die Demo‑Implementierungen in den Demo‑Modulen `persistence-utils-demo` und `persistence-utils-demo-tool` an.

### Demo‑Workflows

#### Persistence Utils Demo (persistence-utils-demo)

##### Personensuche

1. Starte die Personensuche über das Demo‑Menü.
2. Es öffnet sich ein Dialog, in dem du Suchkriterien (Name, Abteilung, …) eingeben kannst.
3. Gib die Suchbegriffe ein und starte die Suche.
4. Sieh dir die Ergebnisse an und wähle einen Eintrag zur Detailansicht aus.

![Personensuche](images/PersonSearch.png)

##### Abteilungssuche

1. Starte die Abteilungssuche über das Demo‑Menü.
2. Es erscheint ein Auswahl‑Dialog mit Spalten und Filtern.
3. Verwende die Filter, um Ergebnisse einzugrenzen, und klicke, um eine Abteilung zu bearbeiten oder anzusehen.
4. Prüfe die Ergebnisse oder lege eine neue Abteilung an.

![Abteilungssuche](images/DepartmentSearch.png)

##### Gehaltserhöhungen

1. Starte den Workflow „Gehaltserhöhungen“ über das Demo‑Menü.
2. Bestätige die Aktion, um die konfigurierten Gehaltserhöhungen anzuwenden.
3. Prüfe die Bestätigung und verifiziere die aktualisierten Gehälter im System.

#### Persistence Utils Demo Tool (persistence-utils-demo-tool)

##### Testaufgaben ausführen

1. Starte „Testaufgaben ausführen“ im Demo‑Menü.
2. Bestätige die Ausführung der Testaufgaben.
3. Prüfe die Testergebnisse oder Logs nach Abschluss.

![Testaufgaben ausführen](images/Testdata.png)

## Einrichtung

- **Rollen:** Everybody (konfiguriert in config/roles.xml)
- **OpenAPI:** No public OpenAPI specs delivered by this extension.

### Variablen

- Es wurden keine Variablen erkannt.

- Für diesen Abschnitt wurden keine Informationen bereitgestellt.

## Komponenten

### Connector‑Prozesse

- No connector processes delivered by this extension.

### Formular‑Komponenten

- No form components delivered by this extension.

### Maven‑Artefakte

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
