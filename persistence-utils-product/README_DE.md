# Persistence-Utilities
Das JPA Persistence Lib-Dienstprogramm von Axon Ivy hilft Ihnen dabei,
Initiativen zur Prozessautomatisierung durch die Einführung einer
Abstraktions-DAO-Schicht zu beschleunigen. Dieses Dienstprogramm:
- Ermöglicht einen robusten und komplexen Datenbankzugriff auf Basis des [JPA
  2.2-Standards](https://www.oracle.com/java/technologies/persistence-jsp.html).
- Hilft Ihnen beim Erstellen von DAO-Klassen für Ihre Entitäten mit direktem
  Zugriff auf Datenbanken unter Verwendung von Standard-APIs.
- Vereinfacht die Erstellung typischer Entitätsklassen.
- Unterstützt Sie mit einer Demo-Implementierung.
- Zeigt Anwendungsbeispiele für einige optionale verwandte Technologien.

## Projekte
- *persistence-utils-demo* das Demo-Projekt
- *persistence-utils-demo-tool* Tool zum Generieren von Testdaten für das
  Demo-Projekt
- *persistence-utils-test* JUnit-Tests für die Persistenz-Bibliothek und das
  Demo-Projekt
- *persistence-utils-demo* JUnit-Webtest-Demo

## Versionshinweise

***Informationen zu neueren Versionen finden Sie im Changelog im Axon Ivy
Market!***

### 12.0.1
*Änderungen*

- [Problem 82](https://github.com/axonivy-market/persistence-utils/issues/82)
  Fix getExpression ganzer Objekte

### 11.3.2-SNAPSHOT
*Änderungen*

- [APS-232](https://1ivy.atlassian.net/browse/APS-232) GitHub-Problem 73 beheben
  (SearchFilter absteigende Sortierung)

### 10.0.8
*Änderungen*

- [APS-224](https://1ivy.atlassian.net/browse/APS-224) Behandlung von leeren
  Listen oder Nullwerten in enum SearchFilters korrigiert
- [APS-226](https://1ivy.atlassian.net/browse/APS-226) Das Abrufen externer
  Datenbanken wurde an die Methoden Ivy 10 und Ivy 11 angepasst.

### 10.0.7
*Änderungen*

- Fehlende Dokumentation zu 10.0.5 hinzugefügt.
- Sortierung des CMS nach Sprachumgebung ohne Berücksichtigung der
  Groß-/Kleinschreibung korrigiert

*Inkompatibilitäten*
- Hinweis: Dieser Hinweis wurde zu 10.0.5 hinzugefügt: `HasCmsName` ist jetzt
  Teil der Persistenz-Utilities. Wenn Sie eigene haben, müssen Sie diese
  ersetzen oder vererben.

### 10.0.6
*Änderungen*

- Fester Webtest

### 10.0.5
*Änderungen*

- [APS-206](https://1ivy.atlassian.net/browse/APS-206) Make forceSingleResult
  allgemeiner gestalten
- [APS-220](https://1ivy.atlassian.net/browse/APS-220) Komfortfunktionen für die
  Implementierung von Suchfiltern hinzufügen
  - Neue Funktionen in GenericDAO zur Unterstützung der Implementierung von
    Suchfiltern, die Aufzählungen mit CMS-Namen enthalten.
  - Zugriff auf aktuell erstellte Suchanfragen, während Prädikate erstellt
    werden.
- [APS-222](https://1ivy.atlassian.net/browse/APS-222) Aktualisierung der
  Protokollierung und der DB-GUI in Junit-Tests

*Inkompatibilitäten*
- `FilterPredicate.getSearchFilter` wurde umbenannt in
  `FilterPredicate.getSearchEnum`
- `FilterOrder.getSearchFilter` wurde umbenannt in `FilterOrder.getSearchEnum`
- `HasCmsName` ist jetzt Teil der Persistenz-Utilities. Wenn Sie Ihre eigenen
  haben, müssen Sie diese ersetzen oder übernehmen.

### 10.0.4
*Änderungen*
- [APS-156](https://1ivy.atlassian.net/browse/APS-156) Behandlung von Sitzungen
  in CriteriaQueryContext korrigieren
- [APS-86](https://1ivy.atlassian.net/browse/APS-86) Verwenden Sie Instant für
  überprüfbare Entitäten erstellen/aktualisieren/löschen
- [APS-87](https://1ivy.atlassian.net/browse/APS-87) Umbenennen von „isEnabled”
  in „ToggleableEntity”
- [APS-158](https://1ivy.atlassian.net/browse/APS-158) Refaktorisierung der
  Typhierarchie für Auditable- und ToggleableEntity

*Inkompatibilitäten*

:exclamation::exclamation: **HINWEIS: Dieses Update enthält wesentliche
Änderungen an Spaltennamen und Datentypen sowie eine Umgestaltung der Entitäts-
und DAO-Struktur.** **Bitte beachten Sie die hier und in den Empfehlungen
aufgeführten Änderungen**:exclamation::exclamation:

- Datentyp von „ `” AuditableEntity`s `Header` Attribute für
  Erstellungs-/Aktualisierungs- und Löschdatum wurden geändert in `Instant`
- Attribut `expiryDate` von `ToggleableEntity` wurde umbenannt in **expiry** und
  sein Datentyp wurde geändert in `Instant`
- Attribut `ToggleableEntity`s **isEnabled** wurde umbenannt in **enabled** und
  sein Datentyp wurde geändert in `boolean`
- `AuditableEntity` und `ToggleableEntity` werden nun von `abgeleitet.
  VersionableEntity` anstelle von `GenericIdEntity`
- `` Um eine Version der geänderten Entitäten mit einer vordefinierten
  Zeichenfolge „ `“ bereitzustellen, wurden die neuen Entitäten „ `“, „` “,
  „AuditableIdEntity“, „ “ und „ `“ sowie die entsprechenden DAOs eingeführt.

*Empfehlungen*
- Benennen Sie die Spalte „expiry” in „ **expiry” um,**, es sei denn, Sie haben
  die Spaltennamen ohnehin angepasst. Beispiel: ALTER TABLE **yourtable** RENAME
  COLUMN **expiryDate** TO **expiry**.
- Benennen Sie die Spalte „isEnabled” in „ **” um, und die Spalte „enabled” in
  „** ”, es sei denn, Sie haben die Spaltennamen ohnehin angepasst. Beispiel:
  ALTER TABLE **yourtable** RENAME COLUMN **isEnabled** TO **enabled**.
- * Stellen Sie sicher, dass es keine NULL-Werte ( *) für die Spalte „ **” (** )
  in Ihrer Datenbank gibt, die für „ ” ( ) aktiviert ist. Die Spalte wurde als
  nicht nullfähig definiert, sodass nur manuelle Änderungen in der Datenbank zu
  NULL-Werten ( *) für „* ” ( ) geführt haben sollten.
- Verwenden Sie die Methoden „ `get/set...AsDate“` oder „
  `get/set...AsLocalDateTime“`, wenn Sie konvertierte Datentypen von
  „create/modify/delete/expiry“ `Instant`s abrufen müssen.
- Ändern Sie die Verwendung von `AuditableEntity` und `ToggleableEntity` in
  `AuditableIdEntity` und `ToggleableIdEntity`. Verwenden Sie passende DAOs.

### 10.0.3
*Änderungen*
- Quellen zu Maven-Artefakt hinzugefügt

*Inkompatibilitäten*
- *Keine*

*Empfehlungen*
- *Keine*

### 10.0.2
*Änderungen*
- [APS-148](https://1ivy.atlassian.net/browse/APS-148) Ausnahme beim Speichern
  wird stillschweigend ignoriert

*Inkompatibilitäten*
- *Keine*

*Empfehlungen*
- **forceSingleResult()** wurde in eine höhere Klasse verschoben. Falls Sie Ihre
  eigene Version implementiert haben, prüfen Sie, ob Sie diese entfernen können.
- Einige Funktionen in „ **“, „AbstractDAO“ und „** “, die den Typ „ **“
  zurückgaben, „Object“ und „** “, geben nun denselben Typ zurück, der
  eingegeben wurde. Wenn Sie diese Funktionen verwendet und Typumwandlungen
  vorgenommen haben, sind diese möglicherweise nicht mehr erforderlich.

### 10.0.0
*Änderungen*
- Die Persistenz-Bibliothek wurde in den Marktplatz verschoben und folgt dort
  dem Versionsnummerierungsschema.
- Das JPA-Demoprojekt ist nun direkt in der
  Persistence-Utilities-Marketplace-Komponente enthalten.

*Inkompatibilitäten*
- Die Abhängigkeit von der Persistenz-Bibliothek muss aus den Projekten entfernt
  und durch die Verwendung der Marktplatzkomponente ersetzt werden.

### 0.10.0
*Änderungen*
- [APS-84](https://1ivy.atlassian.net/browse/APS-84) forceSingleResult() sollte
  für jeden Typ funktionieren.
- [APS-91](https://1ivy.atlassian.net/browse/APS-91)
  AbstractDAO.unproxyAndInitialize() typsicher machen
- [APS-108](https://1ivy.atlassian.net/browse/APS-108) Entferntes throw() in
  GenericDAO.save() wieder hinzufügen

*Inkompatibilitäten*
- *Keine*

### 0.9.4
*Änderungen*
- [TG-1013](https://1ivy.atlassian.net/browse/TG-1013) Persistenz-Bibliothek –
  Historie korrigiert

*Inkompatibilitäten*
- Änderungen an der Historie
    - HistoryPK.timestamp wurde von String zu Timestamp geändert.
    - History.jsonData wurde von Clob zu String geändert.
    - Der ursprüngliche Bean-Wert wurde zu AuditHandler.handleUpdate()
      hinzugefügt.

### 0.9.3
*Änderungen*
- [TG-781](https://1ivy.atlassian.net/browse/TG-781) Persistence Library:
  Supertypen in QuerySettings zulassen

*Inkompatibilitäten*
- *Keine*

### 0.9.2
*Änderungen*
- [TG-871](https://1ivy.atlassian.net/browse/TG-871) Persistence Library:
  SearchFilter: Sortierung funktioniert nicht für Felder, die nicht Teil des
  Ergebnistupels sind.

*Inkompatibilitäten*
- Entfernte Funktion „ **“ GenericDAO.isSyncableEntity()**
- Nur wenn der Code auf einem Fehler basiert, dass das Sortieren für nicht
  ausgewählte Felder nicht funktioniert.

### 0.9.1
*Änderungen*
- [TG-780](https://1ivy.atlassian.net/browse/TG-780) Persistence Library:
  Aufteilung in Bibliothek und Test

*Inkompatibilitäten*
- *Keine*

### 0.9.0
*Inkompatibilitäten*
- Ändern Sie die Gruppen-ID von „ **com.axonivy** ” in „ **com.axonivy.utils”.**
- Ändern Sie die Artefakt-ID von „ **persistence-** ” in „
  **persistence-utils”.**

### 0.0.2
- Neuer Entitätstyp „ **“ „VersionableEntity“** unterstützt optimistisches
  Locking

*Inkompatibilitäten*
- Wenn Sie **GenericEntity** direkt verwendet haben und sich auf optimistisches
  Locking verlassen, ändern Sie dies in **VersionableEntity.**

### 0.0.1
Dies ist die erste Version.

## Demo
## Funktionen
- Verwendung der AxonIvy Persistence Library
- [Java Bean Validation](https://beanvalidation.org/) Beispiel
- [JUnit](https://junit.org/junit5/) Tests für Persistenz-Bibliothek und
  Demo-Projekt
- [DBUnit](https://www.dbunit.org/) Testdaten zur Verwendung im Demo-Projekt und
  auch in JUnit-Tests
- [Quartz](http://www.quartz-scheduler.org/) Aufgabe, die Gehälter in
  regelmäßigen Abständen zu erhöhen :-)

## Testdaten
Bereiten Sie Testdaten vor, die in einer integrierten Excel-Datei mit DBUnit
verwaltet werden. Dieselben Testdaten werden in der Benutzeroberfläche und in
JUnit-Tests verwendet.

## Datenbank
Das Demo-Projekt verwendet die hsqldb-Datenbank und löscht und erstellt alle
Tabellen beim Start. Sie können dies auf eine andere Datenbank ändern, aber
achten Sie darauf, die Einstellungen „ `“, „hibernate.hbm2ddl.auto“, „` “ in „
`“, „persistence.xml“ und „` “ richtig zu verstehen und einzustellen!

![Testdaten-Vorbereitungs-UI](images/Testdata.png "Testdaten-Vorbereitung-UI")

## Abteilungen
Listen Sie Abteilungen auf, bearbeiten und löschen Sie sie. Das Löschen einer
Abteilung erfolgt auf logischer Ebene unter Verwendung der Funktionen von „ `”
und „AuditableEntity”`.

![Department Search UI](images/DepartmentSearch.png
"Abteilungssuche-Benutzeroberfläche")

## Personen
Personen auflisten, bearbeiten und löschen. Personen können mit Ivy-Benutzern
synchronisiert werden.

In dieser Liste kann eine Person nur andere Personen derselben Abteilung sehen.
Die Sichtbarkeit von Personen wird direkt in PersonDAO implementiert und für
fast alle DAO-Funktionen durchgesetzt. Nur die Administratorrolle darf alle
Personen sehen.

Bean Validation wird zur Validierung von Personendaten verwendet. Validierungen
können zu Fehlern (z. B. wenn ein Pflichtfeld fehlt) oder Warnungen (z. B. wenn
der Ivy-Benutzername nicht der Namenskonvention Vorname.Nachname entspricht)
führen.

Die Liste bietet Sortier- und Filterfunktionen für Spalten mit einem
Lazy-Datenmodell. Das bedeutet, dass das Sortieren, Filtern und Blättern von der
Datenbank mit einer dynamisch erstellten Abfrage durchgeführt wird.

![Personensuche-Benutzeroberfläche](images/PersonSearch.png "Benutzeroberfläche
für die Personensuche")
