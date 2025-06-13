# StockSemences
Logiciel de gestion de stock de semences de plantes
--------
En cours de développement
--------

Logiciels nécessaires
-----------------------
Sous Windows 7 64 bits
-----------------------

-- Poste Développeur --

 - Eclipse IDE for Java Developers - Windows 64 bit (LUNA SR2)
 - Librairie postgresql-9.4-1201.jdbc4.jar (dossier lib)
 - Librairie jcalendar-1.4.jar (dossier lib)
 - PostgreSQL 9.4.1-3 x64
      - port d'écoute 5432
      - locale French, France
      - UTF-8
 - pgAdmin III
 - pgModeler ?? (devenu payant)
 - JRE (JavaSE-1.6)
 - code source

-- Poste Utilisateur --

 - PostgreSQL 9.4.1-3 x64
 - StockSemence.jar (obtenu après compilation des sources, non disponible au moment de la première publication github)
 - scripts d'installation sur BDD (contenus dans le dossier postgres)

## Tests

Ce projet utilise Maven pour lancer les tests unitaires.
Vous pouvez exécuter les tests avec :

```bash
mvn test
```
