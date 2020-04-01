select count(* ) from Incoming;
select count(* ) from OUTGOING;
select id, mqRfh2, incomingtime from INCOMING;
update incoming set mqRfh2='some value';
update incoming set servicetype='DP';
update incoming set servicetype='IIB' where id < 20;

update incoming set errorcode='999';
update incoming set servicename='asrv' where id < 50;
update incoming set servicename='csrv' where id > 50 and id < 100;

update incoming set servicename='bsrv' where id > 110 and id < 220;
update incoming set status='NEW';
update incoming set status='CLOSED_MANUALLY' where id <110;

select id, status from incoming where servicename='asrv';
alter table INCOMING add ( EXPIRYTIME  TIMESTAMP(9));
alter table OUTGOING drop column payloaduuid;

select * from incoming;

update Incoming set errorMessage='';


alter table INCOMING add ( mqMD  VARCHAR(255);
update Incoming set mqRfh2='<Transactional>true</Transactional>
  <Encoding>546</Encoding>
  <CodedCharSetId>1208</CodedCharSetId>
  <Format>MQSTR   </Format>
  <Version>2</Version>
  <Report>0</Report>
  <MsgType>8</MsgType>
  <Expiry>-1</Expiry>
  <Feedback>0</Feedback>';
update Incoming set mqMD='<Transactional>true</Transactional>
  <Encoding>546</Encoding>
  <CodedCharSetId>1208</CodedCharSetId>
  <Format>MQSTR   </Format>
  <Version>2</Version>
  <Report>0</Report>
  <MsgType>8</MsgType>
  <Expiry>-1</Expiry>
  <Feedback>0</Feedback>
  <Priority>0</Priority>
  <Persistence>0</Persistence>
  <MsgId>414d5120514d49494244312020202020c4b5a05702c6ae20</MsgId>
  <CorrelId>000000000000000000000000000000000000000000000000</CorrelId>
  <BackoutCount>0</BackoutCount>
  <ReplyToQ>                                                </ReplyToQ>
  <ReplyToQMgr>QMIIBD1                                         </ReplyToQMgr>
  <UserIdentifier>mqm         </UserIdentifier>
  <AccountingToken>0000000000000000000000000000000000000000000000000000000000000000</AccountingToken>';
  
update Payload set   payload='<Transactional>true</Transactional>
  <Encoding>546</Encoding>
  <CodedCharSetId>1208</CodedCharSetId>
  <Format>MQSTR   </Format>
  <Version>2</Version>
  <Report>0</Report>
  <MsgType>8</MsgType>
  <Expiry>-1</Expiry>
  <Feedback>0</Feedback>
  <Priority>0</Priority>
  <Persistence>0</Persistence>
  <MsgId>414d5120514d49494244312020202020c4b5a05702c6ae20</MsgId>
  <CorrelId>000000000000000000000000000000000000000000000000</CorrelId>
  <BackoutCount>0</BackoutCount>
  <ReplyToQ>                                                </ReplyToQ>
  <ReplyToQMgr>QMIIBD1                                         </ReplyToQMgr>
  <UserIdentifier>mqm         </UserIdentifier>
  <AccountingToken>0000000000000000000000000000000000000000000000000000000000000000</AccountingToken>';

update incoming  set MQMESSAGEID = null;
alter table INCOMING MODIFY MQMESSAGEID VARCHAR(255);

select dpSubCode from Incoming;

update Incoming set dpSubCode='Code-1';

update OUTGOING set MQID=null
alter table OUTGOING MODIFY MQID VARCHAR(255);


update incoming set EXPIRYTIME =TO_TIMESTAMP('2017-07-02 06:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF');
update Incoming set STATUSTIME =TO_TIMESTAMP('2017-02-02 06:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF') 
where id ='1DEA260082FD11E68DF2C23EC5E5920E';

CREATE INDEX FROMOUTGOINGID_INX ON INCOMING (FROMOUTGOINGID);

create user soaarchive identified by soaarchive;
GRANT create session TO soaarchive;
GRANT create table TO soaarchive;
GRANT create view TO soaarchive;
GRANT create any trigger TO soaarchive;
GRANT create any procedure TO soaarchive;
GRANT create sequence TO soaarchive;
GRANT create synonym TO soaarchive;
GRANT all privileges TO soaarchive;
ALTER USER system IDENTIFIED BY system;
Exec DBMS_XDB.SETHTTPPORT(3010);


--<ScriptOptions statementTerminator=";"/>

ALTER TABLE QMGRCONNDATA DROP CONSTRAINT SYS_C007004;

DROP INDEX SYS_C007004;

DROP TABLE QMGRCONNDATA;

    

CREATE TABLE QMGRCONNDATA (
		ID NUMBER NOT NULL,
		CHLNAME VARCHAR2(255),
		IP VARCHAR2(255),
		PORT NUMBER,
		QMGRNAME VARCHAR2(255)
	);

CREATE UNIQUE INDEX SYS_C007004 ON QMGRCONNDATA (ID ASC);

ALTER TABLE QMGRCONNDATA ADD CONSTRAINT SYS_C007004 PRIMARY KEY (ID);
--<ScriptOptions statementTerminator=";"/>

CREATE TABLE QMGRALIAS (
		ALIASNAME VARCHAR2(255) NOT NULL,
		QMGRNAME VARCHAR2(255)
	);

CREATE UNIQUE INDEX SYS_C007004 ON QMGRALIAS (ALIASNAME ASC);

ALTER TABLE QMGRALIAS ADD CONSTRAINT SYS_C007004_QMGRALIAS PRIMARY KEY (ALIASNAME);





ALTER TABLE PAYLOAD DROP CONSTRAINT SYS_C007002;

DROP INDEX SYS_C007002;

DROP TABLE PAYLOAD;

CREATE TABLE PAYLOAD (
		ID NUMBER NOT NULL,
		PAYLOAD VARCHAR2(255),
		PAYLOADSIZE NUMBER
	);

CREATE UNIQUE INDEX SYS_C007002 ON PAYLOAD (ID ASC);

ALTER TABLE PAYLOAD ADD CONSTRAINT SYS_C007002 PRIMARY KEY (ID);

--<ScriptOptions statementTerminator=";"/>

ALTER TABLE OPENJPA_SEQUENCE_TABLE DROP CONSTRAINT SYS_C007000;

DROP INDEX SYS_C007000;

DROP TABLE OPENJPA_SEQUENCE_TABLE;

CREATE TABLE OPENJPA_SEQUENCE_TABLE (
		ID NUMBER NOT NULL,
		SEQUENCE_VALUE NUMBER
	);

CREATE UNIQUE INDEX SYS_C007000 ON OPENJPA_SEQUENCE_TABLE (ID ASC);

ALTER TABLE OPENJPA_SEQUENCE_TABLE ADD CONSTRAINT SYS_C007000 PRIMARY KEY (ID);
--<ScriptOptions statementTerminator=";"/>

ALTER TABLE INCOMING DROP CONSTRAINT SYS_C007006;

DROP INDEX SYS_C007006;

DROP INDEX I_NCOMING_PAYLOAD;

DROP TABLE INCOMING;

--<ScriptOptions statementTerminator=";"/>

CREATE TABLE INCOMING (
		ID NUMBER NOT NULL,
		BIPMSG VARCHAR2(255),
		CLOSETIME TIMESTAMP(11),
		ERRORCODE VARCHAR2(255),
		ERRORMESSAGE VARCHAR2(255),
		ERRORTIME TIMESTAMP(11),
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
		MQMESSAGEID NUMBER,
		MQRFH2 VARCHAR2(255),
		RERUNSNUM NUMBER,
		SERVICENAME VARCHAR2(255),
		SERVICETYPE VARCHAR2(255),
		STATUS VARCHAR2(255),
		STATUSTIME TIMESTAMP(11),
		PAYLOAD_ID NUMBER,
		EXPIRYTIME null
	);

CREATE INDEX I_NCOMING_PAYLOAD ON INCOMING (PAYLOAD_ID ASC);

CREATE INDEX FROMOUTGOINGID_INX ON INCOMING (FROMOUTGOINGID ASC);

CREATE UNIQUE INDEX SYS_C007006 ON INCOMING (ID ASC);

ALTER TABLE INCOMING ADD CONSTRAINT SYS_C007006 PRIMARY KEY (ID);


--<ScriptOptions statementTerminator=";"/>

ALTER TABLE OUTGOING DROP CONSTRAINT SYS_C007008;

DROP INDEX I_OUTGONG_PAYLOAD;

DROP INDEX SYS_C007008;

DROP TABLE OUTGOING;

CREATE TABLE OUTGOING (
		ID NUMBER NOT NULL,
		INCOMINGID NUMBER,
		MQID NUMBER,
		MQRFH2 VARCHAR2(255),
		RESENDTIME TIMESTAMP(11),
		TOQMGR VARCHAR2(255),
		TOQUEUE VARCHAR2(255),
		PAYLOAD_ID NUMBER
	);

CREATE INDEX I_OUTGONG_PAYLOAD ON OUTGOING (PAYLOAD_ID ASC);

CREATE UNIQUE INDEX SYS_C007008 ON OUTGOING (ID ASC);

ALTER TABLE OUTGOING ADD CONSTRAINT SYS_C007008 PRIMARY KEY (ID);


update incoming set fromqmgr='AAA' where id='5c3f89c6-82fe-11e6-ab25-7f0001010000';

