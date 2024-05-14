USE financial_management_db;

-- Chèn bản ghi mẫu vào bảng `users`
INSERT INTO users (first_name, last_name, dob, address) VALUES
("John", "Doe", "1990-01-15", "123 Elm St"),
("Mary", "Smith", "1985-06-20", "456 Oak St"),
("Alice", "Johnson", "1992-03-25", "789 Pine Ave"),
("Michael", "Brown", "1988-09-30", "321 Cedar St"),
("David", "Wilson", "1991-02-10", "102 Maple St"),
("Linda", "White", "1989-11-05", "305 Birch St"),
("Robert", "Green", "1993-07-18", "456 Redwood Ave"),
("Susan", "Taylor", "1990-04-27", "678 Aspen Rd"),
("William", "Anderson", "1987-12-22", "789 Cypress Dr"),
("Elizabeth", "Thomas", "1992-09-14", "123 Spruce St"),
("James", "Harris", "1986-03-11", "456 Willow St"),
("Patricia", "Martinez", "1993-05-19", "789 Sycamore Blvd"),
("Charles", "Robinson", "1985-08-07", "102 Palm St"),
("Barbara", "Clark", "1991-01-02", "305 Elm St"),
("Christopher", "Lewis", "1989-06-28", "456 Cedar Rd"),
("Sarah", "Walker", "1994-10-31", "789 Maple Ave"),
("Joseph", "Hall", "1988-03-20", "102 Redwood Dr"),
("Karen", "King", "1990-12-08", "305 Birch Blvd"),
("George", "Wright", "1991-07-15", "456 Cypress Rd"),
("Betty", "Lopez", "1993-04-23", "678 Spruce St"),
("Edward", "Hill", "1987-11-30", "789 Pine Blvd"),
("Nancy", "Scott", "1990-09-25", "123 Willow Rd"),
("Daniel", "Adams", "1986-02-13", "456 Maple Dr"),
("Sandra", "Baker", "1992-08-09", "789 Palm St"),
("Matthew", "Gonzalez", "1989-05-27", "102 Birch Rd"),
("Ashley", "Nelson", "1993-12-05", "305 Aspen Ave"),
("Joshua", "Carter", "1991-03-18", "456 Cypress Blvd"),
("Donna", "Mitchell", "1988-07-12", "789 Sycamore St"),
("Brian", "Perez", "1994-06-29", "102 Redwood Blvd"),
("Carol", "Roberts", "1985-10-20", "305 Palm Rd"),
("Eric", "Turner", "1990-01-16", "456 Elm Blvd"),
("Rebecca", "Phillips", "1991-11-03", "789 Birch Ave"),
("Steven", "Campbell", "1988-02-24", "102 Willow St"),
("Emily", "Parker", "1993-07-30", "305 Cedar Blvd"),
("Kevin", "Evans", "1992-12-21", "456 Pine Rd"),
("Sharon", "Edwards", "1986-10-15", "789 Redwood Dr"),
("Mark", "Collins", "1987-05-09", "102 Spruce Blvd"),
("Cynthia", "Stewart", "1994-11-22", "305 Maple St"),
("Jason", "Sanchez", "1992-01-31", "456 Palm Ave"),
("Michelle", "Morris", "1990-04-05", "789 Aspen Rd"),
("Larry", "Rogers", "1989-08-18", "102 Birch Blvd"),
("Deborah", "Reed", "1991-09-29", "305 Sycamore St"),
("Frank", "Cook", "1987-07-14", "456 Cedar Ave"),
("Angela", "Morgan", "1993-02-07", "789 Willow Blvd"),
("Scott", "Bell", "1990-06-24", "102 Redwood Rd"),
("Brenda", "Murphy", "1988-03-13", "305 Palm Blvd"),
("Raymond", "Bailey", "1986-11-01", "456 Maple Ave"),
("Helen", "Rivera", "1992-10-06", "789 Aspen Dr");

-- Chèn dữ liệu mẫu vào bảng `accounts`
INSERT INTO accounts (user_id, username, email, password, avatar_url) VALUES
(1, "johndoe", "johndoe@example.com", "password123", "https://example.com/avatars/johndoe.png"),
(2, "marysmith", "marysmith@example.com", "marypassword", "https://example.com/avatars/marysmith.png"),
(3, "alicejohnson", "alicejohnson@example.com", "alicepassword", "https://example.com/avatars/alicejohnson.png"),
(4, "michaelbrown", "michaelbrown@example.com", "michaelpassword", "https://example.com/avatars/michaelbrown.png"),
(5, "davidwilson", "davidwilson@example.com", "davidpassword", "https://example.com/avatars/davidwilson.png");

-- Chèn dữ liệu mẫu vào bảng `wallets`
INSERT INTO wallets (account_id, name) VALUES
(1, "Checking Account"),
(1, "Savings Account"),
(2, "Credit Card"),
(3, "Investment Fund"),
(4, "Cash Wallet");

-- Chèn dữ liệu mẫu vào bảng `transactions`
INSERT INTO transactions (account_id, transaction_date, amount, note) VALUES
(1, "2024-05-01 12:30:00", -50.00, "Groceries at Supermarket"),
(1, "2024-05-02 08:00:00", 1500.00, "Salary Deposit"),
(2, "2024-05-03 14:00:00", -200.00, "New Shoes Purchase"),
(3, "2024-05-04 09:30:00", 2500.00, "Investment Fund Deposit"),
(4, "2024-05-05 19:00:00", -75.00, "Dinner at Restaurant"),
(5, "2024-05-06 10:00:00", -120.00, "Public Transport Pass");
