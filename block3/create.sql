CREATE TABLE client (
  client_id INTEGER NOT NULL AUTO_INCREMENT,
  firstName VARCHAR(50) NOT NULL,
  lastName VARCHAR(50) NOT NULL,
  PRIMARY KEY (client_id)
);

CREATE TABLE movie (
  movie_id INTEGER NOT NULL AUTO_INCREMENT,
  title VARCHAR(50) NOT NULL,
  PRIMARY KEY (movie_id)
);

CREATE TABLE real_movie (
  movie_id INTEGER NOT NULL AUTO_INCREMENT,
  actor VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (movie_id),
  CONSTRAINT FK_REAL_MOVIE FOREIGN KEY (movie_id) REFERENCES movie (movie_id)
);

CREATE TABLE animation_movie (
  movie_id INTEGER NOT NULL AUTO_INCREMENT,
  drawer VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (movie_id),
  CONSTRAINT FK_ANIMATION_MOVIE FOREIGN KEY (movie_id) REFERENCES movie (movie_id)
);

CREATE TABLE hire (
  client_id
  INTEGER
    REFERENCES client (id)
      ON DELETE CASCADE,
  movie_id
  INTEGER
    REFERENCES movie (movie_id)
      ON DELETE CASCADE,
  PRIMARY KEY (client_id, movie_id)
);