SET NAMES utf8;

DROP DATABASE IF EXISTS project0;
CREATE DATABASE project0 CHARACTER SET utf8 COLLATE utf8_bin;

USE project0;


CREATE TABLE roles
(

-- id has the INTEGER type (other name is INT), it is the primary key
    id   INTEGER     NOT NULL PRIMARY KEY,

-- name has the VARCHAR type - a string with a variable length
-- names values should not be repeated (UNIQUE)
    name VARCHAR(30) NOT NULL UNIQUE
);

INSERT INTO roles
VALUES (0, 'admin');
INSERT INTO roles
VALUES (1, 'patient');
INSERT INTO roles
VALUES (2, 'med_sis');
INSERT INTO roles
VALUES (3, 'doctor');
CREATE TABLE users
(

    id         INTEGER     NOT NULL auto_increment PRIMARY KEY,

-- 'UNIQUE' means logins values should not be repeated in login column of table
    login      VARCHAR(20) NOT NULL UNIQUE,

-- not null string columns
    password   VARCHAR(20) NOT NULL,
    first_name VARCHAR(20) NOT NULL,
    last_name  VARCHAR(20) NOT NULL,
    birth_date DATE NULL,
-- this declaration contains the foreign key constraint
-- role_id in users table is associated with id in roles table
-- role_id of user = id of role
    role_id    INTEGER     NOT NULL REFERENCES roles (id)

-- removing a row with some ID from roles table implies removing
-- all rows from users table for which ROLES_ID=ID
-- default value is ON DELETE RESTRICT, it means you cannot remove
-- row with some ID from the roles table if there are rows in
-- users table with ROLES_ID=ID
        ON DELETE CASCADE

-- the same as previous but updating is used insted deleting
        ON UPDATE RESTRICT
);

-- id = 1
INSERT INTO users
VALUES (DEFAULT, 'admin', 'admin', 'Ivan', 'Ivanov','1960-12-03', 0);
-- id = 2
INSERT INTO users
VALUES (DEFAULT, 'patient_1', 'patient', 'Aoung', 'Aoptsev', '2002-12-03' , 1);
-- id = 3
INSERT INTO users
VALUES (DEFAULT, 'patient_2', 'patient', 'Kirill', 'Selegay', '1980-02-03' , 1);
-- id = 4
INSERT INTO users
VALUES (DEFAULT, 'patient_3', 'patient', 'Zivan', 'Zogdan', '1990-06-02', 1);
-- id = 5
INSERT INTO users
VALUES (DEFAULT, 'doctor_1', 'doctor', 'Ivan', 'Petrov', '1985-10-05', 3);
-- id = 6
INSERT INTO users
VALUES (DEFAULT, 'doctor_2', 'doctor', 'Ivan1', 'Petrov1','1975-08-22', 3);
-- id = 7
INSERT INTO users
VALUES (DEFAULT, 'doctor_3', 'doctor', 'Ivan2', 'Petrov2', '1980-12-22', 3);

-- id = 8
INSERT INTO users
VALUES (DEFAULT, 'med_sis', 'med_sis', 'Anna', 'Ivanova', '1970-06-24', 2);

-- +++++++++++++++++++++++++++++++++++++--


CREATE TABLE positions
(
    id       INTEGER     NOT NULL PRIMARY KEY,
    position VARCHAR(20) NOT NULL
);

INSERT INTO positions
VALUES ('0', 'syrgery');

INSERT INTO positions
VALUES ( '1', 'oculist');

INSERT INTO positions
VALUES ('2', 'therapist');


CREATE TABLE doctor_position
(
    doc_id      INTEGER NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    position_id INTEGER NOT NULL REFERENCES positions (id) ON DELETE CASCADE,
    PRIMARY KEY (doc_id, position_id)
);

INSERT INTO doctor_position VALUES('5','0');
INSERT INTO doctor_position VALUES('6','1');
INSERT INTO doctor_position VALUES('7','2');

CREATE TABLE doctor_to_patient
(
    id         INTEGER     NOT NULL auto_increment PRIMARY KEY,
    doc_id     INTEGER NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    patient_id INTEGER NOT NULL UNIQUE REFERENCES users (id) ON DELETE CASCADE,
    UNIQUE (doc_id, patient_id)
);
-- 1
INSERT INTO doctor_to_patient VALUES (DEFAULT, '5','2');
-- 2
INSERT INTO doctor_to_patient VALUES (DEFAULT, '6','3');
-- 3
INSERT INTO doctor_to_patient VALUES (DEFAULT, '7','4');
-- INSERT INTO doctor_to_patient VALUES()


CREATE TABLE med_card
(
    id         INTEGER     NOT NULL auto_increment PRIMARY KEY,
    doc_pat    INTEGER     NOT NULL UNIQUE REFERENCES doctor_to_patient (id),
    diagnose   VARCHAR(40) NULL,
    procedures VARCHAR(40) NULL,
    medicament VARCHAR(40) NULL,
    operation  VARCHAR(40) NULL,
    vipiska BOOLEAN NULL
);

INSERT INTO med_card VALUES (DEFAULT,'1','diagnose1','procedure1','medicament1','operation1',true);
INSERT INTO med_card VALUES (DEFAULT,'2','diagnose2','procedure2','medicament2','operation2',false);
INSERT INTO med_card VALUES (DEFAULT,'3','diagnose3','procedure3','medicament3','operation3', false);
