# Beharrlichkeit Utils
#Axon Efeus JPA Beharrlichkeit Lib Nutzen hilft du beschleunigst Arbeitsgang
Automatisierung Initiativen hereinbringen mal eine Abstraktion DAO Auflage.
Dieser Nutzen:
- Erlaubt stabile und vielschichtige Datenbank Zugang gegründet weiter den [JPA
  2.2 tariflich](https://www.oracle.com/java/technologies/persistence-jsp.html).
- Hilft du schaffst DAO Klassen für eure Entitäten mit direkt Zugang zu
  Datenbanken benutzend tariflich APIs
- Vereinfacht die Kreation von typisch Entität Klassen
- Unterstützt du mit eine Demo Ausführung
- Zeigt Beispiel Gebrauch von manche optionale verwandte Technologien

## Projekte
- *Beharrlichkeit-utils-Demo* das Demo Projekt
- *Beharrlichkeit-utils-Demo-Tool* Tool tp generiert Klausur #Daten für das Demo
  Projekt
- *Beharrlichkeit-utils-Klausur* JUnit Klausuren für die Beharrlichkeit
  Bibliothek und das Demo Projekt
- *Beharrlichkeit-utils-Demo* JUnit Web Klausur Demo

## #Befreien Beachtet

**Für neuer Versionen, bitte sehen die changelog in den #Axon Efeu Vermarktet!**

### 12.0.1
*Änderungen*

- [Gib heraus 82](https://github.com/axonivy-market/persistence-utils/issues/82)
  Bring an getExpression von ganz Objekte

### 11.3.2-SCHNAPPSCHUSS
*Änderungen*

- [APS-232](https://1ivy.atlassian.net/browse/APS-232) Bring an GitHub
  Sachverhalt 73 (SearchFilter #absteigend Sorte)

### 10.0.8
*Änderungen*

- [APS-224](https://1ivy.atlassian.net/browse/APS-224) Klemme bedienen von leer
  Listen oder nichtige Werte herein enum SearchFilters
- [APS-226](https://1ivy.atlassian.net/browse/APS-226) Klemme holen von extern
  Datenbank zu Methode vereinbar mit Efeu 10 und Efeu 11

### 10.0.7
*Änderungen*

- Zugefügt fehlen Dokumentation von 10.0.5
- Fest ordnen von CMS zu Schauplatz bewussten Fall empfindungslos

*Unverträglichkeiten*
- Note: Diese Note war zugefügt zu 10.0.5: `HasCmsName` Ist jetzt Teil von der
  Beharrlichkeit utils, ob du hast eure eigenes, du brauchst zu austauschen oder
  erben.

### 10.0.6
*Änderungen*

- Feste Web Klausur

### 10.0.5
*Änderungen*

- [APS-206](https://1ivy.atlassian.net/browse/APS-206) Mach forceSingleResult
  More General
- [APS-220](https://1ivy.atlassian.net/browse/APS-220) Fügen zu Annehmlichkeit
  Aufgaben für Suche Filter Ausführung
  - Neue Aufgaben herein GenericDAO zu Hilfe implementierend SearchFilters
    zügelnd Aufzählungen mit CMS Namen.
  - Zugang zu zurzeit #errichtet Suche Anfrage Weile Satzaussagen sind gebaut.
- [APS-222](https://1ivy.atlassian.net/browse/APS-222) Update #Loggen und DB
  #grafische Benutzeroberfläche herein Junit Klausuren

*Unverträglichkeiten*
- `FilterPredicate.getSearchFilter` War umbenannt zu
  `FilterPredicate.getSearchEnum`
- `FilterOrder.getSearchFilter` War umbenannt zu `FilterOrder.getSearchEnum`
- `HasCmsName` Ist jetzt Teil von der Beharrlichkeit utils, ob du hast eure
  eigenes, du brauchst zu austauschen oder erben.

### 10.0.4
*Änderungen*
- [APS-156](https://1ivy.atlassian.net/browse/APS-156) Klemme bedienen herein
  von Session CriteriaQueryContext
- [APS-86](https://1ivy.atlassian.net/browse/APS-86) Benutz Augenblick für
  Auditable Entitäten schaffen/Update/streicht
- [APS-87](https://1ivy.atlassian.net/browse/APS-87) Benennen um isEnabled
  herein ToggleableEntity
- [APS-158](https://1ivy.atlassian.net/browse/APS-158) Refactor Typ hirachy für
  Auditable- und ToggleableEntity

*Unverträglichkeiten*

:Ausruf::Ausruf: **NOTE: Dieses Update zügelt allzugroße Änderungen zu Spalte
Namen und datatypes sowie refactoring von Entität und DAO Struktur.** **Bitte
übergeben zu der Liste von Änderungen erwähnten hier und in den #Anraten**
:Ausruf::Ausruf:

- Datatype Von `AuditableEntity`s `Kopfball` Attribute für schaffen/verbessern
  und streichen #wo Datum gewechselt zu `Augenblick`
- Attribut `expiryDate` von `ToggleableEntity` hat gewesen umbenannt zu
  **expiry** und sein datatype #abgeändert zu `Augenblick`
- Attribut `ToggleableEntity`s **isEnabled** hat gewesen umbenannt zu
  **aktiviert** und sein datatype #abgeändert zu `aussagenlogisch`
- `AuditableEntity` Und `ToggleableEntity` sind jetzt #ableiten von
  `VersionableEntity` statt `GenericIdEntity`
- Zu versehen eine Version von den #abgeändert Entitäten mit ein #vordefiniert
  `Schnur` id die neuen Entitäten `AuditableIdEntity` und `ToggleableIdEntity`
  und #zusammenpassend DAOs haben gewesen hereingebracht

*#Anraten*
- Benenn um das expiry Spalte zu **expiry** #außer angepasst hast du die Spalte
  Namen anyways. #Z.B. #ÄNDERN TISCH **yourtable** BENENNT UM SPALTE
  **expiryDate** ZU **expiry**.
- Benenn um das isEnabled Spalte zu **aktiviert** #außer angepasst hast du die
  Spalte Namen anyways. #Z.B. #ÄNDERN TISCH **yourtable** BENENNT UM SPALTE
  **isEnabled** ZU **aktiviert**.
- Herstellung sicher dort ist nein *NULL* Werte für die **aktiviert** Spalte in
  eurer Datenbank. Die Spalte war definiert da nicht nullable, so einziges
  Handbuch Änderungen in der Datenbank sollte haben zeitigen *NULL* Werte
- Nutzung `bekommt/gesetzt...AsDate` Oder `#bekommen/gesetzt...AsLocalDateTime`
  Methoden ob du brauchst zu bekommen #ausgebaut datatypes von
  schaffen/modifizieren/streichen/expiry `Augenblick`s
- Änderung Gebrauch von `AuditableEntity` und `ToggleableEntity` zu
  `AuditableIdEntity` und `ToggleableIdEntity`. Nutzung #zusammenpassend DAOs

### 10.0.3
*Änderungen*
- Zugefügt Quellen zu Experten Werkzeug

*Unverträglichkeiten*
- *#Nichts*

*#Anraten*
- *#Nichts*

### 10.0.2
*Änderungen*
- [APS-148](https://1ivy.atlassian.net/browse/APS-148) Ausnahme während
  speichern ist #schweigend schluckte

*Unverträglichkeiten*
- *#Nichts*

*#Anraten*
- **forceSingleResult()** War begeben zu höher Klasse. #Im Fall implementiertest
  du eure eigene Version, Kontrolle ob kannst du entfernen ihm
- Einige Aufgaben herein **AbstractDAO** welcher kehrte zurück den Typ
  **Objekt** will jetzt zurückkehren ebensolchen gleichen Typ da #hereinkommen.
  Ob du benutztest ihnen und hatten Typisierungen, sie dürfen deswegen sein
  nicht mehr nötig

### 10.0.0
*Änderungen*
- Die Beharrlichkeit Bibliothek war begeben zu dem Marktplatz und will folgen
  die Version Nummerierung #mauscheln dort
- Das JPA Demo projiziert herein jetzt direkt eingeschlossen in der
  Beharrlichkeit utils Marktplatz Komponente

*Unverträglichkeiten*
- Die Kolonie zu die Beharrlichkeit Bibliothek muss sein entfernt von projiziert
  und tauschte aus mal die Marktplatz Komponente benutzen

### 0.10.0
*Änderungen*
- [APS-84](https://1ivy.atlassian.net/browse/APS-84) forceSingleResult() sollten
  arbeiten für irgendwelchen Typ
- [APS-91](https://1ivy.atlassian.net/browse/APS-91) Mach
  AbstractDAO.unproxyAndInitialize() typesafe
- [APS-108](https://1ivy.atlassian.net/browse/APS-108) #Zufügen gestrichen
  Wurf() herein GenericDAO.Speicher() nochmal

*Unverträglichkeiten*
- *#Nichts*

### 0.9.4
*Änderungen*
- [TG-1013](https://1ivy.atlassian.net/browse/TG-1013) Beharrlichkeit Bibliothek
  Geschichte Klemme

*Unverträglichkeiten*
- Änderungen zu Geschichte
    - HistoryPK.#Zeitstempel gewechselt von #Aufreihen zu #Zeitstempel
    - Geschichte.jsonData Gewechselt von Clob zu #Aufreihen
    - Zugefügt originalen Bohne Wert zu AuditHandler.handleUpdate()

### 0.9.3
*Änderungen*
- [TG-781](https://1ivy.atlassian.net/browse/TG-781) Beharrlichkeit Bibliothek:
  Erlaub super tippt ein QuerySettings

*Unverträglichkeiten*
- *#Nichts*

### 0.9.2
*Änderungen*
- [TG-871](https://1ivy.atlassian.net/browse/TG-871) Beharrlichkeit Bibliothek:
  SearchFilter: Ordnen tut nicht #arbeiten# für #auffangen #welche ist nicht von
  dem Resultat trennen tuple

*Unverträglichkeiten*
- Entfernt Aufgabe **GenericDAO.isSyncableEntity()**
- Nur ob #kodieren vertraut auf Käfer dass ordnen für unselected Felder arbeiten
  nicht

### 0.9.1
*Änderungen*
- [TG-780](https://1ivy.atlassian.net/browse/TG-780) Beharrlichkeit Bibliothek:
  Geteilt hinein Bibliothek und Klausur

*Unverträglichkeiten*
- *#Nichts*

### 0.9.0
*Unverträglichkeiten*
- Änderung Gruppiert ID von **com.axonivy** Zu **com.axonivy.utils**
- Änderung Werkzeug ID von **Beharrlichkeit** zu **Beharrlichkeit-utils**

### 0.0.2
- Neuer Entität Typ **VersionableEntity** Unterstützungen optimistisch
  #abschließen

*Unverträglichkeiten*
- Ob du benutztest **GenericEntity** direkt und du vertraust weiter optimistisch
  #abschließen, dann wechselst dies zu **VersionableEntity**

### 0.0.1
Dies ist die einleitende Version

## Demo
## Charakterzüge
- Nutzung von die AxonIvy Beharrlichkeit Bibliothek
- [#Java Bohne Bestätigung](https://beanvalidation.org/) Beispiel
- [JUnit](https://junit.org/junit5/) Klausuren für Beharrlichkeit Bibliothek und
  Demo Projekt
- [DBUnit](https://www.dbunit.org/) testdata zu benutzen in Demo Projekt und
  auch herein JUnit Klausuren
- [Quarz](http://www.quartz-scheduler.org/) Beruf zu hochheben Saläre an
  regelmäßig Zeit-Intervalle :-)

## Klausur #Daten
Bereite vor Klausur #Daten erhielt aufrecht herein #exzellieren ein integratives
Datei benutzend DBUnit. Ebensolche gleiche Klausur Daten sind benutzt herein die
UI und herein JUnit Klausuren.

## Datenbank
Das Demo Projekt Nutzungen die hsqldb Datenbank und lässt fallen und schafft
#wann alle Tische starteten. Du darfst wechseln dies zu einer verschiedenen
Datenbank aber sein vorsichtig tu verstehen und gesetzt richtig der
`überwintert.hbm2ddl.Auto` setzend herein `Beharrlichkeit.xml`!

![Testdata Präparat UI](images/Testdata.png "Testdata Präparat UI")

## Abteilungen
Liste, bearbeite und streichen Abteilungen. Streichend eine Abteilung will sein
benutzt auf einem logischen Level Charakterzüge von die `AuditableEntity`.

![Abteilung Sucht UI](images/DepartmentSearch.png "Abteilung Sucht UI")

## Personen
Liste, bearbeite und streichen Personen. Personen können sein synchronisiert zu
Efeu Nutzer.

In dieser Liste, eine Person kann nur anderen Personen von ebensolcher gleichen
Abteilung sehen. Sicht von Personen sind implementiert direkt herein die
PersonDAO und vollstreckt für fast alle DAO Aufgaben. Nur #dürfen sieht die
Administrator Rolle alle Personen.

#Bean Bestätigung ist benutzt zu validieren Person #Daten. Bestätigungen können
Fehler veranlassen (z.B. ob ein obligatorisches Feld fehlt) oder Warnungen (z.B.
ob den Efeu Benutzernamen folgt nicht den heißend Konvention
firstname.lastname).

Die Liste ist bieten ordnen und filtern von Spalten mit #ein #faul
#Daten-modellier. Dieses Mittel, jener ordnend, filternd und #Funkruf will sein
getan #bei der Datenbank mit eine #dynamisch #errichtet Anfrage.

![Person Sucht UI](images/PersonSearch.png "Person Sucht UI")
