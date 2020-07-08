CREATE  TABLE test.users (
  username VARCHAR(20) NOT NULL ,
  password VARCHAR(20) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (username));
  
CREATE TABLE test.user_roles (
  user_role_id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(20) NOT NULL,
  role varchar(20) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (role,username),
  KEY fk_username_idx (username),
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES test.users (username));
  

INSERT INTO test.users(username,password,enabled) VALUES ('jack','jack', true);
INSERT INTO test.users(username,password,enabled) VALUES ('peter','peter', true);

INSERT INTO test.user_roles (username, role) VALUES ('jack', 'ROLE_USER');
INSERT INTO test.user_roles (username, role) VALUES ('jack', 'ROLE_ADMIN');
INSERT INTO test.user_roles (username, role) VALUES ('peter', 'ROLE_USER');



create table customers ( id serial not null primary key,
  firstname varchar(20),
  lastname varchar(20),
  status int,
  phone varchar(50),
  email varchar(50)
);