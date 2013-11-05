/*
    ===========================================================================
        Written by Beat Temperli
        (c) 2013, beat@temper.li
    ===========================================================================
 */


/*
    ---------------------------------------------------------------------------
        Projekt 2 - Aufgabe 1b & 1c
    ---------------------------------------------------------------------------
*/

-- create Database with Tables (Aufgabe 1b)
CREATE DATABASE firma;

CREATE TABLE IF NOT EXISTS Person (
    person_id INTEGER KEY AUTO_INCREMENT,
    name VARCHAR (30) NOT NULL,
    email VARCHAR (50)
);

CREATE TABLE IF NOT EXISTS Project (
    project_id INTEGER KEY AUTO_INCREMENT,
    title VARCHAR(50) NOT NULL,
    start TIMESTAMP,
    stop TIMESTAMP,
    proceed FLOAT
);

CREATE TABLE IF NOT EXISTS Work (
    project_id
        INTEGER NOT NULL
        REFERENCES Project (project_id)
        ON DELETE CASCADE,
    person_id
        INTEGER NOT NULL
        REFERENCES Person (person_id)
        ON DELETE CASCADE,
    PRIMARY KEY (project_id, person_id)
);

-- fill Database with content (Aufgabe 1c)
INSERT INTO Person (name, email) VALUES
    ('Max Moritz', 'max@moritz.ch'),
    ('Peter Platzhalter', 'peter@peter.ch'),
    ('Bruno Mars', 'bruno@bruno.ch'),
    ('Franz Ferdinand', 'franz@ferdinand.ch'),
    ('Gustav Gans', 'g@g.g'),
    ('Max Muster', 'max@muster.ch'),
    ('Mickey Mouse', 'mouse@mouse.org'),
    ('Viola Violetta', 'viola@viola.com'),
    ('Walter Werner', 'ww@werner.ch'),
    ('Xaver Xylophon', 'xaver@xylophon.ch');

INSERT INTO Project (title, start, stop, proceed) VALUES
    ('Projekt A', '2013-10-22 00:00:00', '2013-12-22 00:00:00', 123456.78),
    ('Projekt B', '2014-10-22 00:00:00', '2014-12-22 00:00:00', 100000),
    ('Projekt C', '2010-10-22 00:00:00', '2013-10-22 00:00:00', 1000 ),
    ('Projekt D', '2010-01-01 00:00:00', '2010-12-31 23:59:59', 30000),
    ('Projekt E', '2010-01-31 00:00:00', '2015-02-28 00:00:00', 900000000),
    ('Projekt F', '2013-10-22 00:00:00', '2013-10-23 00:00:00', 2000),
    ('Projekt G', '2013-01-01 00:00:00', '2020-10-22 00:00:00', 300000000),
    ('Projekt H', '2013-01-02 00:00:00', '2013-01-03 00:00:00', 250000),
    ('Projekt I', '2013-03-03 00:00:00', '2013-10-22 00:00:00', 333333.33),
    ('Projekt K', '2013-04-04 00:00:00', '2013-10-22 00:00:00', 200000),
    ('Projekt L', '2013-01-01 00:00:00', '2013-12-31 00:00:00', 20000);

INSERT INTO Work (person_id, project_id) VALUES
    (1,1), (1,3), (1,5), (1,7), (1,9), (1,11),
    (2,2), (2,4), (2,6), (2,8), (2,11),
    (4,1), (4,2), (4,3),
    (5,1), (5,2), (5,3),
    (6,4), (6,5), (6,6),
    (7,4), (7,8),
    (8,4), (8,5),
    (9,4), (9,8), (9,9),
    (10,1), (10,2), (10,3), (10,4), (10,5), (10,6), (10,7), (10,8), (10,9);

/*
    ---------------------------------------------------------------------------
        Projekt 2 - Aufgabe 2 (Relationen & Beziehungen)
    ---------------------------------------------------------------------------
*/

-- add foreign key to table "Work" / add relationship between Work and Person.
ALTER TABLE Work
ADD CONSTRAINT FK_work
FOREIGN KEY (person_id) REFERENCES Person(person_id)
    ON UPDATE CASCADE
    ON DELETE CASCADE;

/*
    Aufgabe 2a)
    ---------------------------------------------------------------------------
    Schreiben Sie eine Abfrage, welche eine Relation zurückgibt, in der alle
    Beziehungen zwischen Personen aufgelistet sind. Eine Zeile dieser
    gewünschten Relation beinhaltet zwei Referenzen (Foreign Keys auf
    Primary Key der Personenrelation) auf je eine Person, genau dann wenn
    diese zwei Personen zusammen an einem Projekt arbeiten. Sollte es zwei
    Personen geben, welche in mehreren Projekten zusammen arbeiten,
    dann sollen pro Projektzusammenarbeit solch eine Zeile vorhanden sein.
 */

SELECT
    p.person_id "A id",
    p.name "A name",
    w.person_id "B id",
    (SELECT pp.name
        FROM Person as pp
        WHERE pp.person_id = w.person_id) "B name",
    w.project_id "Projekt id"

FROM Person AS p
    JOIN Work AS w ON w.person_id < p.person_id

WHERE p.person_id IN (
    SELECT w_inner.person_id
    FROM Work as w_inner
    WHERE w_inner.project_id = w.project_id
)
;


/*
    Aufgabe 2b)
    ---------------------------------------------------------------------------
    Gegeben die oben erhaltene Relation, schreiben Sie eine Abfrage, welche für
    jede Person die Anzahl anderer Personen zurückgibt, mit der sie verbunden
    ist (Node Centrality, Out Degree).
 */

SELECT outerResult.a_id AS "personId", COUNT(*) AS "relations" FROM (
    SELECT DISTINCT innerResult.a_id,innerResult.b_id FROM (
        SELECT
            p.person_id "a_id",
            p.name "a_name",
            w.person_id "b_id"

        FROM Person AS p
            JOIN Work AS w ON w.person_id != p.person_id

        WHERE p.person_id IN (
            SELECT w_inner.person_id
            FROM Work as w_inner
            WHERE w_inner.project_id = w.project_id
        )
        ORDER BY a_id, b_id
    )
    AS innerResult
)
AS outerResult
GROUP BY outerResult.a_id
;


