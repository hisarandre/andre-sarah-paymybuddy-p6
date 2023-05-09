CREATE DATABASE IF NOT EXISTS paymybuddy;
USE paymybuddy;


DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS bank_transaction;
DROP TABLE IF EXISTS bank;
DROP TABLE IF EXISTS connection_transaction;
DROP TABLE IF EXISTS connection;
DROP TABLE IF EXISTS user;

CREATE TABLE user (
  id_user INT NOT NULL AUTO_INCREMENT,
  email VARCHAR(255) NOT NULL UNIQUE,
  firstname VARCHAR(255),
  lastname VARCHAR(255),
  password VARCHAR(255),
  balance FLOAT(2) DEFAULT 0,
  enabled TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (id_user)
);

CREATE TABLE authorities (
  email VARCHAR(50) NOT NULL,
  authority VARCHAR(50) DEFAULT 'USER',
  FOREIGN KEY (email) REFERENCES user(email) ON DELETE RESTRICT ON UPDATE CASCADE
);


CREATE TABLE bank (
  id_bank INT NOT NULL AUTO_INCREMENT,
  iban VARCHAR(255),
  swift VARCHAR(255),
  id_user INT NOT NULL,
  PRIMARY KEY (id_bank),
  FOREIGN KEY (id_user) REFERENCES user(id_user) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE bank_transaction (
  id_transaction INT NOT NULL AUTO_INCREMENT,
  date TIMESTAMP,
  description VARCHAR(255),
  amount FLOAT,
  fees FLOAT,
  id_bank INT NOT NULL,
  PRIMARY KEY (id_transaction),
  FOREIGN KEY (id_bank) REFERENCES bank(id_bank) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE connection (
  id_connection INT NOT NULL AUTO_INCREMENT,
  id_sender INT NOT NULL,
  id_receiver INT NOT NULL,
  FOREIGN KEY (id_sender) REFERENCES user (id_user) ON DELETE RESTRICT ON UPDATE CASCADE,
  FOREIGN KEY (id_receiver) REFERENCES user (id_user) ON DELETE RESTRICT ON UPDATE CASCADE,
  PRIMARY KEY (id_connection)
);

CREATE TABLE connection_transaction (
  id_transaction INT NOT NULL AUTO_INCREMENT,
  date TIMESTAMP,
  description VARCHAR(255),
  amount FLOAT,
  fees FLOAT,
  id_connection INT NOT NULL,
  PRIMARY KEY (id_transaction),
  FOREIGN KEY (id_connection) REFERENCES connection (id_connection) ON DELETE RESTRICT ON UPDATE CASCADE
);