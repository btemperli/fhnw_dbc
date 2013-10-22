
/*
    ===========================================================================
      Written by Beat Temperli
      (c) 2013, beat@temper.li
    ===========================================================================
 */

-- create table Personen
CREATE TABLE IF NOT EXISTS Personen (ID Integer Key, Name varchar(30), Email varchar(50));

-- check table
DESCRIBE Personen;

-- insert persons into table Personen
INSERT INTO Personen (ID, Name, Email) VALUES
    (1, "Max Moritz", "max@moritz.ch"),
    (2, "Peter Platzhalter", "peter@peter.ch"),
    (3, "Bruno Mars", "bruno@bruno.ch"),
    (4, "Franz Ferdinand", "franz@ferdinand.ch");

-- check content of table
SELECT * FROM Personen;

