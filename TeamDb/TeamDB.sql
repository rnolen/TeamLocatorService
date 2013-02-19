drop table mission_teams;
drop table location;
drop table member;
drop table team;
drop table mission;
drop table objective;

CREATE TABLE OBJECTIVE (
    ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    DESCRIPTION VARCHAR(255),
    LATITUDE NUMERIC(9,6),
    LONGITUDE NUMERIC(9,6),
    PRIMARY KEY (ID)
);

CREATE TABLE MISSION (
    ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    DESCRIPTION VARCHAR(255),
    MISSION_DATE DATE,
    OBJECTIVE_ID INTEGER NOT NULL,
    COMPLETED VARCHAR(1),
    PRIMARY KEY (ID),
    CONSTRAINT OBJECTIVE_FK FOREIGN KEY (OBJECTIVE_ID)
        REFERENCES OBJECTIVE (ID)
);

CREATE TABLE TEAM (
    ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    NAME VARCHAR(50),
    PRIMARY KEY (ID)
);

CREATE TABLE MEMBER(
    ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    FIRST_NAME VARCHAR(20) NOT NULL,
    LAST_NAME VARCHAR(20) NOT NULL,
    MIDDLE_NAME VARCHAR(20),
    RANK VARCHAR(20),
    TEAM_ID INTEGER NOT NULL,
    ROLE VARCHAR(20) NOT NULL,
    PRIMARY KEY (ID),
    CONSTRAINT TEAM_FK FOREIGN KEY (TEAM_ID)
        REFERENCES TEAM (ID)
);

CREATE TABLE LOCATION(
    ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    MEMBER_ID INTEGER NOT NULL,
    TEAM_ID INTEGER NOT NULL,
    MISSION_ID INTEGER NOT NULL,
    LATTITUDE NUMERIC(9,6) NOT NULL,
    LONGITUDE NUMERIC(9,6) NOT NULL,
    ELEVATION INTEGER NOT NULL,
    DATE_STAMP TIMESTAMP NOT NULL,
    PRIMARY KEY (ID),
    CONSTRAINT MEMBER_LOC_FK FOREIGN KEY (MEMBER_ID)
        REFERENCES MEMBER (ID),
    CONSTRAINT TEAM_LOC_FK FOREIGN KEY (TEAM_ID)
        REFERENCES TEAM (ID),
    CONSTRAINT MISSION_LOC_FK FOREIGN KEY (MISSION_ID)
        REFERENCES MISSION (ID)
);

CREATE TABLE MISSION_TEAMS(
    ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    MISSION_ID INTEGER NOT NULL,
    TEAM_ID INTEGER NOT NULL,
    PRIMARY KEY (ID),
    CONSTRAINT MISSION_FK_1 FOREIGN KEY (MISSION_ID)
        REFERENCES MISSION (ID),
    CONSTRAINT TEAM_FK_1 FOREIGN KEY (TEAM_ID)
        REFERENCES TEAM (ID)
);


--
-- Initialize with some test data
--
INSERT INTO TEAM (NAME)
    VALUES
        ('ALPHA'),
        ('BRAVO'),
        ('CHARLIE');

INSERT INTO MEMBER (FIRST_NAME, MIDDLE_NAME, LAST_NAME, RANK, TEAM_ID, ROLE)
    VALUES
        ('KIRK','','PATTERSON','CAPTAIN',(SELECT ID FROM TEAM WHERE NAME ='ALPHA'),'TEAM_LEADER'),
        ('ALVIN','','YORK','SARGEANT',(SELECT ID FROM TEAM WHERE NAME ='ALPHA'),'MEMBER'),
        ('KEN','','MCCOY','CORPORAL',(SELECT ID FROM TEAM WHERE NAME ='ALPHA'),'MEMBER'),
        ('BANNING','','ED','CAPTAIN',(SELECT ID FROM TEAM WHERE NAME ='BRAVO'),'TEAM_LEADER'),
        ('RICHARDSON','','JAMES','SARGEANT',(SELECT ID FROM TEAM WHERE NAME ='BRAVO'),'MEMBER'),
        ('KOFFLER','','STEVE','CORPORAL',(SELECT ID FROM TEAM WHERE NAME ='BRAVO'),'MEMBER'),
        ('REACHER','','JACK','CORPORAL',(SELECT ID FROM TEAM WHERE NAME ='BRAVO'),'MEMBER');

INSERT INTO OBJECTIVE (DESCRIPTION, LATITUDE, LONGITUDE)
    VALUES
        ('Palmetto Scholars Academy',32.867179,-79.975685);

INSERT INTO MISSION (DESCRIPTION, MISSION_DATE, OBJECTIVE_ID, COMPLETED)
    VALUES
        ('RECON PSA', current_date, (SELECT ID FROM OBJECTIVE WHERE DESCRIPTION='Palmetto Scholars Academy'),'N');

INSERT INTO MISSION_TEAMS (MISSION_ID, TEAM_ID)
    VALUES
        ((SELECT ID FROM MISSION WHERE DESCRIPTION = 'RECON PSA'),(SELECT ID FROM TEAM WHERE NAME = 'ALPHA')),
        ((SELECT ID FROM MISSION WHERE DESCRIPTION = 'RECON PSA'),(SELECT ID FROM TEAM WHERE NAME = 'BRAVO'));

 INSERT INTO LOCATION (MEMBER_ID,TEAM_ID,MISSION_ID,LATTITUDE,LONGITUDE,ELEVATION,DATE_STAMP)
    VALUES
        ((SELECT ID FROM MEMBER WHERE FIRST_NAME='KIRK'),(SELECT ID FROM TEAM WHERE NAME='ALPHA'),(SELECT ID FROM MISSION WHERE DESCRIPTION='RECON PSA'),32.86000,-79.96000,0,current_timestamp),
        ((SELECT ID FROM MEMBER WHERE FIRST_NAME='ALVIN'),(SELECT ID FROM TEAM WHERE NAME='ALPHA'),(SELECT ID FROM MISSION WHERE DESCRIPTION='RECON PSA'),32.86800,-79.97000,0,current_timestamp),
        ((SELECT ID FROM MEMBER WHERE FIRST_NAME='KEN'),(SELECT ID FROM TEAM WHERE NAME='ALPHA'),(SELECT ID FROM MISSION WHERE DESCRIPTION='RECON PSA'),32.86890,-79.96900,0,current_timestamp);
        
    

