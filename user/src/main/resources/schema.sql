CREATE TABLE IF NOT EXISTS user (
    id_user BIGINT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    id_role BIGINT NOT NULL,
    PRIMARY KEY (id_user)
);

CREATE TABLE IF NOT EXISTS role (
    id_role BIGINT NOT NULL AUTO_INCREMENT,
    label VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_role)
);

INSERT IGNORE INTO role (id_role, label) VALUES (1, 'DEFAULT');
INSERT IGNORE INTO role (id_role, label) VALUES (2, 'ADMIN');
