--- ENTITIES INVOLVED ---
-- USER
-- ROLE
-- BANK
-- ACCOUNT
-- CARD
-- USER_ACCOUNT
-- BLACK_TOKEN

--- RELATIONSHIPS ---
-- USER - ROLE --> OneToMany --> One user has one role, One role have many users
-- ACCOUNT - BANK --> OneToMany --> One account has one bank, One bank have many accounts
-- ACCOUNT - CARD --> OneToOne --> One account has one card, One card has one account
-- USER - ACCOUNT --> ManyToMany --> One user have many accounts, One account have many users --> Pivot Table user_account

--- TABLES ---
-- ROLE TABLE
CREATE TABLE IF NOT EXISTS role (
    id_role BIGINT AUTO_INCREMENT PRIMARY KEY,
    label VARCHAR(255)
);

-- USER TABLE
CREATE TABLE IF NOT EXISTS user (
    id_user BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    id_role BIGINT,
    FOREIGN KEY (id_role) REFERENCES role(id_role)
);

-- BANK TABLE
CREATE TABLE IF NOT EXISTS bank (
    id_bank BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

-- ACCOUNT TABLE
CREATE TABLE IF NOT EXISTS account (
    id_account BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    amount BIGINT,
    id_bank BIGINT,
    FOREIGN KEY (id_bank) REFERENCES bank(id_bank)
);

-- CARD TABLE
CREATE TABLE IF NOT EXISTS card (
    id_card BIGINT AUTO_INCREMENT PRIMARY KEY,
    iban VARCHAR(255),
    card_number VARCHAR(255),
    expiration_date DATE,
    cvc VARCHAR(255),
    id_account BIGINT UNIQUE,
    FOREIGN KEY (id_account) REFERENCES account(id_account)
);

-- USER_ACCOUNT PIVOT TABLE
CREATE TABLE IF NOT EXISTS user_account (
    id_user_account BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_user BIGINT,
    id_account BIGINT,
    FOREIGN KEY (id_user) REFERENCES user(id_user),
    FOREIGN KEY (id_account) REFERENCES account(id_account)
);

-- BLACK_TOKEN TABLE
CREATE TABLE IF NOT EXISTS black_token (
    id_black_token BIGINT AUTO_INCREMENT PRIMARY KEY,
    value_black_token VARCHAR(255) NOT NULL
);

-- INSERT APPLICATION ROLES
INSERT IGNORE INTO role (id_role, label) VALUES (1, 'DEFAULT');
INSERT IGNORE INTO role (id_role, label) VALUES (2, 'ADMIN');
