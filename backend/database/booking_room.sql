-- DATABASE POSTGRESQL

DROP TABLE USER_ROLE;
DROP TABLE ROOM_BOOKED;
DROP TABLE ROLE_PERMISSION;
DROP TABLE PERMISSIONS;
DROP TABLE ROLES;
DROP TABLE BOOKINGROOM;
DROP TABLE REGISTER_LOG;
DROP TABLE POSTS;
DROP TABLE ROOMS;
DROP TABLE HOTELS;
DROP TABLE CITYS;
DROP TABLE ROOM_TYPE;
DROP TABLE USERS;
DROP TABLE USER_TYPE;

CREATE TABLE USERS (USERID SERIAL PRIMARY KEY,
					EMAIL CHARACTER VARYING(255) NOT NULL UNIQUE,
					PASSWORD CHARACTER VARYING(255) NOT NULL,
					FULLNAME CHARACTER VARYING(255) NOT NULL,
					PHONENUMBER CHARACTER VARYING(255) UNIQUE,
					DATEOFBIRTH TIMESTAMP,
					GENDER CHARACTER VARYING(255),
					AVATAR CHARACTER VARYING(255),
					STATUS CHARACTER VARYING(255) NOT NULL,
					DATE_CREATED TIMESTAMP,
					UPDATED_DATE TIMESTAMP,
				   	USERTYPEID SERIAL NOT NULL);

CREATE TABLE ROLES (ROLEID SERIAL PRIMARY KEY,
					ROLENAME CHARACTER VARYING(255) NOT NULL UNIQUE,
					KEYNAME CHARACTER VARYING(255) NOT NULL UNIQUE,
					STATUS CHARACTER VARYING(255) NOT NULL,
					DATE_CREATED TIMESTAMP,
					UPDATED_DATE TIMESTAMP);

CREATE TABLE USER_ROLE (USERID SERIAL NOT NULL,
						ROLEID SERIAL NOT NULL,
						PRIMARY KEY(USERID, ROLEID));

CREATE TABLE PERMISSIONS (PERMISSIONID SERIAL PRIMARY KEY,
						PERMISSIONNAME CHARACTER VARYING(255) NOT NULL UNIQUE,
						PERMISSIONKEY CHARACTER VARYING(255) NOT NULL UNIQUE,
						DATE_CREATED TIMESTAMP,
						UPDATED_DATE TIMESTAMP);

CREATE TABLE ROLE_PERMISSION (ROLEID SERIAL NOT NULL,
						PERMISSIONID SERIAL NOT NULL,
						PRIMARY KEY(PERMISSIONID, ROLEID));
						
CREATE TABLE CITYS(CITYID SERIAL PRIMARY KEY,
					CITYNAME CHARACTER VARYING(255) NOT NULL UNIQUE,
					DESCRIPTION CHARACTER VARYING(255) NOT NULL,
				   	IMAGE CHARACTER VARYING(255) NOT NULL,
					DATE_CREATED TIMESTAMP,
					UPDATED_DATE TIMESTAMP);

CREATE TABLE HOTELS(HOTELID SERIAL PRIMARY KEY,
					HOTELNAME CHARACTER VARYING(255) NOT NULL UNIQUE,
					ADDRESS CHARACTER VARYING(255) NOT NULL,
					PHONENUMBER CHARACTER VARYING(255) NOT NULL UNIQUE,
					EMAIL CHARACTER VARYING(255) NOT NULL UNIQUE,
					IMAGE CHARACTER VARYING(255) NOT NULL,
					DESCRIPTION TEXT,
					DATE_CREATED TIMESTAMP,
					UPDATED_DATE TIMESTAMP,
					CITYID SERIAL NOT NULL);


CREATE TABLE ROOM_TYPE(ROOMTYPEID SERIAL PRIMARY KEY,
						ROOMTYPENAME CHARACTER VARYING(255) NOT NULL UNIQUE,
						DESCRIPTION CHARACTER VARYING(255) NOT NULL,
						DATE_CREATED TIMESTAMP,
						UPDATED_DATE TIMESTAMP);

CREATE TABLE ROOMS(ROOMID SERIAL PRIMARY KEY,
					ROOMNUMBER CHARACTER VARYING(255) NOT NULL,
					CONTENTS TEXT NOT NULL,
					IMAGE CHARACTER VARYING(255) NOT NULL,
					NUMBEROFPEOPLE SERIAL, ROOMCOST SERIAL, DISCOUNT SERIAL, DATE_CREATED TIMESTAMP,
					UPDATED_DATE TIMESTAMP,
					HOTELID SERIAL NOT NULL,
					ROOMTYPEID SERIAL NOT NULL);
					
CREATE TABLE POSTS(POSTID SERIAL PRIMARY KEY,
					TITLE CHARACTER VARYING(255),
					CONTENTS TEXT, IMAGE CHARACTER VARYING(255),
					DATECREATED TIMESTAMP,
					UPDATEDDATE TIMESTAMP,
					USERID SERIAL NOT NULL);

CREATE TABLE REGISTER_LOG (ID SERIAL PRIMARY KEY,
							TOKEN CHARACTER VARYING(255) NOT NULL UNIQUE,
							STATUS CHARACTER VARYING(255) NOT NULL,
							DATE_ACTIVE TIMESTAMP NOT NULL,
							DATE_CREATED TIMESTAMP,
							UPDATED_DATE TIMESTAMP,
							USERID SERIAL NOT NULL);
						
CREATE TABLE BOOKINGROOM(BOOKINGROOMID SERIAL PRIMARY KEY,
						 CHECKINDATE TIMESTAMP,
						 CHECKOUTDATE TIMESTAMP,
						 TOTALNUMBEROFPEOPLE SERIAL,
						 STATUS CHARACTER VARYING(255) NOT NULL,
						 DATE_CREATED TIMESTAMP,
						 UPDATED_DATE TIMESTAMP,
						 USERID SERIAL NOT NULL);
						
CREATE TABLE ROOM_BOOKED(BOOKINGROOMID SERIAL NOT NULL,
						 ROOMID SERIAL NOT NULL,
						 PRIMARY KEY(BOOKINGROOMID, ROOMID));
						
CREATE TABLE USER_TYPE (USERTYPEID SERIAL PRIMARY KEY,
						USERTYPENAME CHARACTER VARYING(255) NOT NULL,
						KEYNAME CHARACTER VARYING(255) NOT NULL,
						DATE_CREATED TIMESTAMP,
						UPDATED_DATE TIMESTAMP);

-- CREATE TABLE TEMPLATES (TEMPLATEID SERIAL PRIMARY KEY,
-- 						DESCRIPTIONS CHARACTER VARYING(255),
-- 						CONTENTS TEXT);

ALTER TABLE USER_ROLE ADD CONSTRAINT FK_USER_ROLE_USERS
FOREIGN KEY (USERID) REFERENCES USERS (USERID);
ALTER TABLE USER_ROLE ADD CONSTRAINT FK_USER_ROLE_ROLES
FOREIGN KEY (ROLEID) REFERENCES ROLES (ROLEID);

ALTER TABLE ROLE_PERMISSION ADD CONSTRAINT FK_ROLE_PERMISSION_ROLES
FOREIGN KEY (ROLEID) REFERENCES ROLES (ROLEID);
ALTER TABLE ROLE_PERMISSION ADD CONSTRAINT FK_ROLE_PERMISSION_PERMISSIONS
FOREIGN KEY (PERMISSIONID) REFERENCES PERMISSIONS (PERMISSIONID);

ALTER TABLE USERS ADD CONSTRAINT FK_USERS_USER_TYPE
FOREIGN KEY (USERTYPEID) REFERENCES USER_TYPE (USERTYPEID);

ALTER TABLE HOTELS ADD CONSTRAINT FK_HOTELS_CITYS
FOREIGN KEY (CITYID) REFERENCES CITYS (CITYID);

ALTER TABLE POSTS ADD CONSTRAINT FK_POSTS_USERS
FOREIGN KEY (USERID) REFERENCES USERS (USERID);

ALTER TABLE REGISTER_LOG ADD CONSTRAINT FK_REGISTER_LOG_USERS
FOREIGN KEY (USERID) REFERENCES USERS (USERID);

ALTER TABLE BOOKINGROOM ADD CONSTRAINT FK_BOOKINGROOM_USERS
FOREIGN KEY (USERID) REFERENCES USERS (USERID);

ALTER TABLE ROOMS ADD CONSTRAINT FK_ROOMS_HOTELS
FOREIGN KEY (HOTELID) REFERENCES HOTELS (HOTELID);
ALTER TABLE ROOMS ADD CONSTRAINT FK_ROOMS_ROOM_TYPE
FOREIGN KEY (ROOMTYPEID) REFERENCES ROOM_TYPE (ROOMTYPEID);

ALTER TABLE ROOM_BOOKED ADD CONSTRAINT FK_ROOM_BOOKED_ROOMS
FOREIGN KEY (ROOMID) REFERENCES ROOMS (ROOMID);
ALTER TABLE ROOM_BOOKED ADD CONSTRAINT FK_ROOM_BOOKED_BOOKINGROOM
FOREIGN KEY (BOOKINGROOMID) REFERENCES BOOKINGROOM (BOOKINGROOMID);

INSERT INTO ROLES(ROLENAME, KEYNAME,STATUS)
VALUES('Quản trị viên','ADMIN','ACTIVE');
INSERT INTO ROLES(ROLENAME, KEYNAME,STATUS)
VALUES('Nhân viên','EMPLOYEE','ACTIVE');
INSERT INTO ROLES(ROLENAME, KEYNAME,STATUS)
VALUES('Khách hàng','CUSTOMER','ACTIVE');

INSERT INTO PERMISSIONS(PERMISSIONNAME, PERMISSIONKEY)
VALUES('trang quản trị','admin_page');
INSERT INTO PERMISSIONS(PERMISSIONNAME, PERMISSIONKEY)
VALUES('trang thông tin người dùng','profile');
INSERT INTO PERMISSIONS(PERMISSIONNAME, PERMISSIONKEY)
VALUES('tạo tài khoản','create_account');
INSERT INTO PERMISSIONS(PERMISSIONNAME, PERMISSIONKEY)
VALUES('danh sách tài khoản','list_account');
INSERT INTO PERMISSIONS(PERMISSIONNAME, PERMISSIONKEY)
VALUES('cập nhật tài khoản','updated_account');
INSERT INTO PERMISSIONS(PERMISSIONNAME, PERMISSIONKEY)
VALUES('xóa tài khoản','deleted_account');

INSERT INTO ROLE_PERMISSION(ROLEID, PERMISSIONID)
VALUES(1,1);
INSERT INTO ROLE_PERMISSION(ROLEID, PERMISSIONID)
VALUES(1,2);
INSERT INTO ROLE_PERMISSION(ROLEID, PERMISSIONID)
VALUES(1,3);
INSERT INTO ROLE_PERMISSION(ROLEID, PERMISSIONID)
VALUES(1,4);
INSERT INTO ROLE_PERMISSION(ROLEID, PERMISSIONID)
VALUES(1,5);
INSERT INTO ROLE_PERMISSION(ROLEID, PERMISSIONID)
VALUES(1,6);
INSERT INTO ROLE_PERMISSION(ROLEID, PERMISSIONID)
VALUES(2,1);
INSERT INTO ROLE_PERMISSION(ROLEID, PERMISSIONID)
VALUES(2,2);
INSERT INTO ROLE_PERMISSION(ROLEID, PERMISSIONID)
VALUES(2,3);
INSERT INTO ROLE_PERMISSION(ROLEID, PERMISSIONID)
VALUES(2,4);
INSERT INTO ROLE_PERMISSION(ROLEID, PERMISSIONID)
VALUES(2,5);
INSERT INTO ROLE_PERMISSION(ROLEID, PERMISSIONID)
VALUES(2,6);
INSERT INTO ROLE_PERMISSION(ROLEID, PERMISSIONID)
VALUES(3,2);

INSERT INTO USER_TYPE(USERTYPENAME, KEYNAME)
VALUES('Quản trị viên','admin');
INSERT INTO USER_TYPE(USERTYPENAME, KEYNAME)
VALUES('Nhân viên','employee');
INSERT INTO USER_TYPE(USERTYPENAME, KEYNAME)
VALUES('Khách hàng','customer');

INSERT INTO USERS(EMAIL, PASSWORD, FULLNAME, STATUS, USERTYPEID)
VALUES('admin@gmail.com','$2a$10$IvZtUldlF7F4KLWEIz5HEutcfs8nSC4nwbBBIiu667mdShCk.84Iq', 'Quản trị viên','ACTIVE',1);
INSERT INTO USER_ROLE(USERID, ROLEID)
VALUES(1,1);


