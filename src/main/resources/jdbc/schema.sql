create table ADDRESS
(
    address_id varchar(100) PRIMARY KEY,
    address varchar(255) NOT NULL UNIQUE
);
create table USER_DATA
(
    username varchar(100) NOT NULL,
    address_id varchar(100),
    FOREIGN KEY (address_id) REFERENCES ADDRESS(address_id)
);