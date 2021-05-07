#Vorlesungplaner 
entwickelt von Denis Trautner, Henrik Volmari und Niklas Kröning

##Verzeichnis
1. Vorbereitung
2. Flows
3. ChangeLog

##Vorbereitung
Um diese Anwendung mit der Funktionalität des Email versendes zu nutzen ist es notwendig vorab einige Vorkehrungen zu treffen.
Es ist Notwendig, dass auf dem Gerät eine Version von Node.js mit NPM installiert ist.

1. Ausführung von `npm install maildev` in der Konsole
2. In der Konsole muss der Befehl `maildev --web 80 --smtp 25 --hide-extensions STARTTLS` ausgeführt werden.

Nach ausübung dieser Schritte sollte in dem Terminal/der Konsole etwa folgendes stehen: 

`MailDev webapp running at http://0.0.0.0:80`

`MailDev SMTP Server running at 0.0.0.0:25`

Unter `localhost:80` sollte dann MailDev aufrufbar sein.

Für die weitere Nutzung der Anwendung sind keine weiteren Vorkehrungen nötig.

##Flows
Die Anwendung gliedert sich in drei unterschiedliche Flows.

1. Nutzer/Student
2. Dozent
3. Admin

###Student
Der Student kann nach dem einloggen lediglich auf die Kalenderansichten der Kurse zugreifen. In diesen werden alle Vorlesungen für den Kurs angezeigt.

###Dozent
Der Dozent besitzt wie der Student die Möglichkeit auf die Kalenderansichten der Kurse zuzugreifen. Darüber hinaus hat dieser auch die Möglichkeit auf das TutorBoard zuzugreifen.

Auf dem Tutorboard erhält der Dozent eine Übersicht über die Kurse bei welchen er noch Termine planen muss. Wenn die Planung vom Admin gestartet wurde, kann dieser Termine erstellen und bearbeiten.
Ist der Dozent mit seiner Planung fertig kann der button "Planung beenden" gedrückt werden. Für den Dozenten wird in diesem Kurs die Möglichkeit der Planung beendet.

Wenn der Dozent auf "Kurs bearbeiten" drückt, wird auf die Planungsseite des Kurses weitergeleitet. Hier wird eine Kalenderansicht dargestellt mit allen Terminen des Kurses.
Diese lässt sich auch in der Ansicht von Monat auf Woche und Tagesansicht ändern.
Unter dem Kalender sieht der Dozent alle bisher von ihm angelegten Termine. Diese können dann auch direkt in dieser Ansicht bearbeitet werden.
Um neue Termine anlegen zu können wird auf der rechten Seite der Kalenderansicht ein Eingabefeld dargestellt.
Es ist wichtig anzumerken, dass sich die Termine nur innerhalb des bei der Planung ausgewählten Semesters befinden dürfen.
Auch dürfen sich die Termine nicht mit bereits erstellten überschneiden

Unter der Übersicht mit den Kursen befindet sich eine Kalenderdarstellung.
In dieser bekommt der Dozent eine Übersicht aller seine angelegten Termine.

###Admin
Der Admin hat die Möglichkeit wie der Student und Dozent auf die Kurskalender zuzugreifen. Das Tutorboard ist für diesen allerdings gesperrt.
Dafür steht dem Admin das "Adminboard" zur Verfügung. Hier hat dieser die Möglichkeit einen neuen Studiengang und neue Kurse anzulegen.
Neuen Kursen müssen ein Start- und Enddatum zugeteilt werden und ein Studiengang.

Unter diesen Eingabefeldern werden alle Studiengänge dargestellt und deren Kurse angezeigt welche sich auch auswählen lassen um tiefer in die Planung zu gelangen.

Ist der Admin nun auf dem einer Kursansicht angelangt hat dieser hier verschieden Möglichkeiten.
So lassen sich Semester anlegen, welche benötigt werden für die Planungsreihenfolge.
Ein Semester gibt an in welchem Zeitraum Termine für Vorlesungen platziert werden können.

Weiter kann der Admin neue Vorlesungen anlegen.
Dafür muss angegeben werden, wie diese heißt, welchem Modul diese zugehört, welcher Dozent oder welche Dozenten an dieser beteiligt sind und welchen Stundenumfang diese hat.

Wenn nun soweit alles angelegt wurde und der Kurs für die Planung freigegeben werden soll, lässt sich dies über das Feld **Reifenfolge** lösen.
Hier kann mittels "Drag and drop" die Reihenfolge estgelegt werden, in welcher die Dizenten ihre Vorlesungen planen dürfen.
Wichtig ist hier, dass zunächst ein Semester übergeben werden muss, da sonst die Planung nicht gestartet werden kann.

##ChangeLog
Aufgrund der Kritik bei der Präsentation, wurde die Möglichkeit des Anlegens von neuen Dozenten entfernt.
Es wurde angemerkt, dass die Dozenten einfach per Datenbank eingeschoben werden. 
Es können sich neue Nutzer registrieren, aber besitzen lediglich die Rolle "Student"

Ansonsten wurde sich bei der Umsetzung sehr eng an dem präsentierten Fimga Prototypen orientier.
