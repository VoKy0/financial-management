CREATE DATABASE financial_management_db;
USE financial_management_db;

CREATE TABLE users (
	id int AUTO_INCREMENT,
    first_name varchar(255),
    last_name varchar(255),
    dob datetime,
    address varchar(255),
    CONSTRAINT PK_users_id PRIMARY KEY(id)
) ENGINE = InnoDB;

CREATE TABLE accounts (
	id int AUTO_INCREMENT,
    user_id int,
    email varchar(255),
    password varchar(255),
	avatar_url varchar(255),
    CONSTRAINT PK_accounts_id PRIMARY KEY(id)
) ENGINE = InnoDB;

CREATE TABLE wallets (
	id int AUTO_INCREMENT,
	account_id int,
    name varchar(255),
    CONSTRAINT PK_wallets_id PRIMARY KEY(id)
) ENGINE = InnoDB;

-- Xem xét lại khóa ngoại bảng này: zo account hay wallet
CREATE TABLE transactions (
	id int AUTO_INCREMENT,
    account_id int,
    transaction_date datetime,
    amount float,
    note text,
    CONSTRAINT PK_transactions_id PRIMARY KEY(id)
) ENGINE = InnoDB;

CREATE TABLE transaction_categories (
	id int AUTO_INCREMENT,
    transaction_id int,
    category varchar(255),
    img_url varchar(255),
    CONSTRAINT PK_transaction_categories_id PRIMARY KEY(id)
) ENGINE = InnoDB;

-- XEM LAI: CÓ NÊN GỌP 3 ledgers lại vs nhau ?
CREATE TABLE transaction_ledgers (
	id int AUTO_INCREMENT,
    transaction_id int,
    CONSTRAINT PK_transaction_ledgers_id PRIMARY KEY(id, transaction_id)
) ENGINE = InnoDB;

CREATE TABLE budget_ledgers (
	id int AUTO_INCREMENT,
    transaction_id int,
    CONSTRAINT PK_budget_ledgers_id PRIMARY KEY(id, transaction_id)
) ENGINE = InnoDB;

CREATE TABLE dept_ledgers (
	id int AUTO_INCREMENT,
    transaction_id int,
    CONSTRAINT PK_dept_ledgers_id PRIMARY KEY(id, transaction_id)
) ENGINE = InnoDB;

ALTER TABLE accounts ADD CONSTRAINT FK_accounts_and_users_on_user_id FOREIGN KEY(user_id) REFERENCES users(id);
ALTER TABLE wallets ADD CONSTRAINT FK_wallets_and_accounts_on_account_id FOREIGN KEY(account_id) REFERENCES accounts(id);
ALTER TABLE transactions ADD CONSTRAINT FK_transactions_and_accounts_on_account_id FOREIGN KEY(account_id) REFERENCES accounts(id);
ALTER TABLE transaction_categories ADD CONSTRAINT FK_transaction_categories_and_transactions_on_transaction_id FOREIGN KEY(transaction_id) REFERENCES transactions(id);
ALTER TABLE transaction_ledgers ADD CONSTRAINT FK_transaction_ledgers_and_transactions_on_transaction_id FOREIGN KEY(transaction_id) REFERENCES transactions(id);
ALTER TABLE budget_ledgers ADD CONSTRAINT FK_budget_ledgers_and_transactions_on_transaction_id FOREIGN KEY(transaction_id) REFERENCES transactions(id);
ALTER TABLE dept_ledgers ADD CONSTRAINT FK_dept_ledgers_and_transactions_on_transaction_id FOREIGN KEY(transaction_id) REFERENCES transactions(id);

