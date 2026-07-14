CREATE DATABASE IF NOT EXISTS medilabo;
USE medilabo;

CREATE TABLE IF NOT EXISTS patients (
                                        id BIGINT NOT NULL AUTO_INCREMENT,
                                        first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    gender VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(255),
    PRIMARY KEY (id)
    );

INSERT INTO patients (
    first_name,
    last_name,
    birth_date,
    gender,
    address,
    phone
)
SELECT 'Test', 'TestNone', '1966-12-31', 'F',
       '1 Brookside St', '100-222-3333'
    WHERE NOT EXISTS (
    SELECT 1 FROM patients WHERE last_name = 'TestNone'
);

INSERT INTO patients (
    first_name,
    last_name,
    birth_date,
    gender,
    address,
    phone
)
SELECT 'Test', 'TestBorderline', '1945-06-24', 'M',
       '2 High St', '200-333-4444'
    WHERE NOT EXISTS (
    SELECT 1 FROM patients WHERE last_name = 'TestBorderline'
);

INSERT INTO patients (
    first_name,
    last_name,
    birth_date,
    gender,
    address,
    phone
)
SELECT 'Test', 'TestInDanger', '2004-06-18', 'M',
       '3 Club Road', '300-444-5555'
    WHERE NOT EXISTS (
    SELECT 1 FROM patients WHERE last_name = 'TestInDanger'
);

INSERT INTO patients (
    first_name,
    last_name,
    birth_date,
    gender,
    address,
    phone
)
SELECT 'Test', 'TestEarlyOnset', '2002-06-28', 'F',
       '4 Valley Dr', '400-555-6666'
    WHERE NOT EXISTS (
    SELECT 1 FROM patients WHERE last_name = 'TestEarlyOnset'
);