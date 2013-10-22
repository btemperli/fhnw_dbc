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
        ON DELETE CASCADE ,
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
    ('Projekt K', '2013-04-04 00:00:00', '2013-10-22 00:00:00', 200000);

INSERT INTO Work (person_id, project_id) VALUES
    (1,1), (1,3), (1,5), (1,7), (1,9),
    (2,2), (2,4), (2,6), (2,8),
    (4,1), (4,2), (4,3),
    (5,1), (5,2), (5,3),
    (6,4), (6,5), (6,6),
    (7,4), (7,8),
    (8,4), (8,5),
    (9,4), (9,8), (9,9),
    (10,1), (10,2), (10,3), (10,4), (10,5), (10,6), (10,7), (10,8), (10,9);