# Persistence Utils

Axon Ivy's JPA Persistence Lib unterstützt dich dabei, Prozessautomatisierungsinitiativen zu beschleunigen, indem eine Abstraktionsschicht (DAO Layer) eingeführt wird. Dieses Tool:

- ermöglicht robusten und komplexen Datenbankzugriff basierend auf dem [JPA 2.2 Standard](https://www.oracle.com/java/technologies/persistence-jsp.html).
- hilft dir, DAO-Klassen für deine Entities zu erstellen und bietet direkten Zugriff auf Datenbanken mithilfe von Standard-APIs.
- vereinfacht die Erstellung typischer Entity-Klassen.
- unterstützt dich mit einer Demoumsetzung
- zeigt Beispiele für die Verwendung einiger optionaler verwandter Technologien.

## Projekte
- *persistence-utils-demo*: Das Demo-Projekt
- *persistence-utils-demo-tool*: Tool zum Generieren von Testdaten für das Demo-Projekt
- *persistence-utils-test*: JUnit-Tests für die Persistence Library und das Demo-Projekt
- *persistence-utils-demo*: JUnit-Webtest-Demo

## Release Notes

### 11.3.2-SNAPSHOT
*Änderungen*

- [APS-231](https://1ivy.atlassian.net/browse/APS-231) Fix GitHub issue 73 (SearchFilter descending sort)

### 10.0.8
*Änderungen*
- [APS-224](https://1ivy.atlassian.net/browse/APS-224) Behebung des Handlings von leeren Listen oder Null-Werten in Enum-Suchfiltern.
- [APS-226](https://1ivy.atlassian.net/browse/APS-226) Anpassung der externen Datenbankabfrage an Methoden, die mit Ivy 10 und Ivy 11 kompatibel sind.

### 10.0.7
*Änderungen*
- Fehlende Dokumentation zu 10.0.5 hinzugefügt.
- Sortierung von CMS auf lokale, fallunempfindliche Sortierung korrigiert.

*Inkompatibilitäten*
- Hinweis: Diese Notiz wurde zu 10.0.5 hinzugefügt: `HasCmsName` ist jetzt Teil der Persistence Utils. Falls du eine eigene Version hast, musst du diese ersetzen oder vererben.

... (weitere Release Notes folgen in ähnlicher Struktur)
