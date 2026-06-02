# Persistence Utils

Persistence Utils ist eine Bibliothek auf Basis der Java Persistence API (JPA), die dir hilft, schnell ein
datenbankbezogenes Axon Ivy Projekt zu starten. Sie bietet eine abstrakte DAO-Hierarchie, damit du komplexere
Datenbankzugriffe bequem umsetzen kannst.

Die Bibliothek hilft dir, DAO-Klassen fuer deine Entitaeten zu erstellen, die vollstaendige APIs fuer den Datenbankzugriff bereitstellen. Ausserdem unterstuetzt sie dich dabei, typische Entity-Klassen mit gaengigen Boilerplate-Anforderungen wie einfachen Audit-Funktionen schnell zu erzeugen.

**Wichtigste Funktionen**

- Beschleunige das Setup von JPA-basierten Axon Ivy Projekten mit wiederverwendbaren DAO-Mustern und Beispielen.
- Erzeuge Entity- und DAO-Klassen, um Boilerplate zu reduzieren und die Entwicklung zu beschleunigen.
- Nutze integrierte Audit-Funktionen und gaengige Persistenz-Scaffolds fuer eine robuste Datenhaltung.
- Verwende hoeherwertige Abstraktionen fuer Abfragen und Transaktionen, um Datenbankcode zu vereinfachen.
- Nutze Demo-Module und Beispiel-Workflows, um Funktionen schnell zu erkunden.
- Integriere die Loesung einfach ueber Maven-Artefakte in deine Build-Pipeline.

## Demo

Sieh dir die Demo-Implementierungen in `persistence-utils-demo` und `persistence-utils-demo-tool` fuer praxisnahe Beispiele und Schritt-fuer-Schritt-Abläufe an.

### Demo-Workflows

#### persistence-utils-demo (persistence-utils-demo)

##### Personensuche

1. Starte den Prozess Personensuche im Demo-Menue.
2. Du siehst einen Dialog mit einer durchsuchbaren Personentabelle (Filter nach Name, Abteilung, Status).

![Personensuche](images/PersonSearch.png)

3. Nutze Suche oder Filter, um Ergebnisse einzugrenzen, und waehle eine Person zum Anzeigen oder Bearbeiten aus.
4. Speichere die Aenderungen, damit Updates in der Datenbank persistiert werden.

##### Abteilungssuche

1. Starte den Prozess Abteilungssuche im Demo-Menue.
2. Das Suchformular hilft dir, Abteilungen nach Namen zu finden und passende Ergebnisse anzuzeigen.

![Abteilungssuche](images/DepartmentSearch.png)

3. Optional kannst du Abteilungen ueber die UI hinzufuegen oder loeschen.
4. Pruefe die Aenderungen und bestaetige die Persistierung.

##### Gehaltserhoehungen

1. Starte die Demo Gehaltserhoehungen im Demo-Menue.
2. Konfiguriere Kriterien oder verwende die Standardwerte, um betroffene Mitarbeitende auszuwaehlen.
3. Fuehre die Aktion aus, um Gehaltsaenderungen anzuwenden.
4. Pruefe die Bestaetigung und die aktualisierten Gehaelter.

#### persistence-utils-demo-tool (persistence-utils-demo-tool)

##### Testaufgaben ausfuehren

1. Starte den Prozess Testaufgaben ausfuehren im Demo-Menue.
2. Waehle Optionen (zum Beispiel DB bereinigen und Testdaten neu laden) und bereite die Testumgebung vor.
3. Nutze Export, um einen Datenbank-Snapshot herunterzuladen oder weitere Wartungsaktionen auszufuehren.

![Testdaten](images/Testdata.png)

4. Pruefe die Meldungen und fahre danach fort.

## Einrichtung

- **Rollen:** Everybody (konfiguriert in config/roles.xml)
- **OpenAPI:** Fuer diesen Abschnitt wurden keine Informationen bereitgestellt.

### Variablen

```
@variables.yaml@
```

- Fuer diesen Abschnitt wurden keine Informationen bereitgestellt.

## Komponenten

### Aufrufbare Subprozesse

Diese Erweiterung liefert keine Connector-Prozesse.

### Dialogkomponenten

#### PersonSearch - Personensuche
- **Namespace:** com.axonivy.utils.persistence.demo.PersonSearch
- **Komponententyp:** UI-Dialog
- **Felder:** - (keine)
- **Zweck:** Personen suchen und verwalten, mit schnellen Filterfunktionen und Bearbeitung direkt in der Ansicht.

#### DepartmentSearch - Abteilungssuche
- **Namespace:** com.axonivy.utils.persistence.demo.DepartmentSearch
- **Komponententyp:** UI-Dialog
- **Felder:** - (keine)
- **Zweck:** Abteilungen finden und verwalten; unterstuetzt Anzeigen, Hinzufuegen und Loeschen.

#### Info - Info-Dialog
- **Namespace:** com.axonivy.utils.persistence.demo.components.Info
- **Komponententyp:** Komponenten-Dialog
- **Felder:** - (keine)
- **Zweck:** Kleine Info-Dialogkomponente zur Anzeige kontextbezogener Informationen.

#### TestTasks - Test Tasks
- **Namespace:** com.axonivy.utils.persistence.demo.tool.TestTasks
- **Komponententyp:** UI-Dialog
- **Felder:** - (keine)
- **Zweck:** Funktionen zum Vorbereiten von Testdaten, zum Exportieren der Datenbank und zum Ausfuehren von Wartungsaufgaben.

### Webdienste

- Fuer diesen Abschnitt wurden keine Informationen bereitgestellt.

### Maven-Artefakte

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
