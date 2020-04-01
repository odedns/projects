--<ScriptOptions statementTerminator=";"/>

ALTER TABLE INCOMING DROP CONSTRAINT SYS_C007148;

DROP INDEX I_NCOMING_PAYLOAD;

DROP INDEX SYS_C007148;

DROP TABLE INCOMING;

CREATE TABLE INCOMING (
		ID NUMBER NOT NULL,
		BIPMSG VARCHAR2(255),
		CLOSETIME TIMESTAMP(11),
		ERRORCODE VARCHAR2(255),
		ERRORMESSAGE CLOB,
		ERRORTIME TIMESTAMP(11),
		EXPIRYTIME TIMESTAMP(11),
		FROMOUTGOINGID NUMBER,
		FROMQMGR VARCHAR2(255),
		FROMQUEUE VARCHAR2(255),
		FROMSERVERIP VARCHAR2(255),
		FROMSERVERNAME VARCHAR2(255),
		FULLEXCEPTION VARCHAR2(255),
		GETTIME TIMESTAMP(11),
		INCOMINGTIME TIMESTAMP(11),
		LASTUSER VARCHAR2(255),
		MQBACKOUTCOUNT NUMBER,
		MQID NUMBER,
		MQMD CLOB,
		MQMESSAGEID VARCHAR2(255),
		MQRFH2 VARCHAR2(255),
		PAYLOADUUID VARCHAR2(255),
		RERUNSNUM NUMBER,
		SERVICENAME VARCHAR2(255),
		SERVICETYPE VARCHAR2(255),
		STATUS VARCHAR2(255),
		STATUSTIME TIMESTAMP(11),
		UUID VARCHAR2(255),
		PAYLOAD_ID NUMBER
	);

CREATE INDEX I_NCOMING_PAYLOAD ON INCOMING (PAYLOAD_ID ASC);

CREATE UNIQUE INDEX SYS_C007148 ON INCOMING (ID ASC);

ALTER TABLE INCOMING ADD CONSTRAINT SYS_C007148 PRIMARY KEY (ID);

--<ScriptOptions statementTerminator=";"/>

ALTER TABLE OUTGOING DROP CONSTRAINT SYS_C007152;

DROP INDEX SYS_C007152;

DROP INDEX I_OUTGONG_PAYLOAD;

DROP TABLE OUTGOING;

CREATE TABLE OUTGOING (
		ID NUMBER NOT NULL,
		INCOMINGID NUMBER,
		INCOMINGUUID VARCHAR2(255),
		MQID NUMBER,
		MQRFH2 VARCHAR2(255),
		PAYLOADUUID VARCHAR2(255),
		RESENDTIME TIMESTAMP(11),
		TOQMGR VARCHAR2(255),
		TOQUEUE VARCHAR2(255),
		UUID VARCHAR2(255),
		PAYLOAD_ID NUMBER
	);

CREATE UNIQUE INDEX SYS_C007152 ON OUTGOING (ID ASC);

CREATE INDEX I_OUTGONG_PAYLOAD ON OUTGOING (PAYLOAD_ID ASC);

ALTER TABLE OUTGOING ADD CONSTRAINT SYS_C007152 PRIMARY KEY (ID);

--<ScriptOptions statementTerminator=";"/>

ALTER TABLE PAYLOAD DROP CONSTRAINT SYS_C007154;

DROP INDEX SYS_C007154;

DROP TABLE PAYLOAD;

CREATE TABLE PAYLOAD (
		ID NUMBER NOT NULL,
		PAYLOAD CLOB,
		PAYLOADSIZE NUMBER,
		UUID VARCHAR2(255)
	);

CREATE UNIQUE INDEX SYS_C007154 ON PAYLOAD (ID ASC);

ALTER TABLE PAYLOAD ADD CONSTRAINT SYS_C007154 PRIMARY KEY (ID);

--<ScriptOptions statementTerminator=";"/>

ALTER TABLE QMGRCONNDATA DROP CONSTRAINT SYS_C007156;

DROP INDEX SYS_C007156;

DROP TABLE QMGRCONNDATA;

CREATE TABLE QMGRCONNDATA (
		ID NUMBER NOT NULL,
		CHLNAME VARCHAR2(255),
		IP VARCHAR2(255),
		PORT NUMBER,
		QMGRNAME VARCHAR2(255),
		UUID VARCHAR2(255)
	);

CREATE UNIQUE INDEX SYS_C007156 ON QMGRCONNDATA (ID ASC);

ALTER TABLE QMGRCONNDATA ADD CONSTRAINT SYS_C007156 PRIMARY KEY (ID);

create sequence  soa_seq start with 1000;

Insert Into QMGRCONNDATA (ID,CHLNAME,IP,PORT,QMGRNAME,UUID)  VALUES (soa_seq.NEXTVAL ,'EMPLOYERS','172.22.5.49',1475,'QMIIBQA1','UUID');

UPDATE 