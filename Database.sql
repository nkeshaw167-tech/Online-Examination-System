-- Online Examination System
-- Database schema for MySQL
-- Run this script in MySQL Workbench or the MySQL command line.

CREATE DATABASE IF NOT EXISTS online_examination_system
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE online_examination_system;

-- 1. Users
CREATE TABLE IF NOT EXISTS users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('STUDENT', 'ADMIN') NOT NULL DEFAULT 'STUDENT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    ) ENGINE=InnoDB;

-- 2. Examinations
CREATE TABLE IF NOT EXISTS exams (
    exam_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(150) NOT NULL,
    description VARCHAR(500),
    duration_minutes INT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    ) ENGINE=InnoDB;

-- 3. Examination questions
CREATE TABLE IF NOT EXISTS questions (
    question_id INT PRIMARY KEY AUTO_INCREMENT,
    exam_id INT NOT NULL,
    question_text TEXT NOT NULL,
    option_a VARCHAR(500) NOT NULL,
    option_b VARCHAR(500) NOT NULL,
    option_c VARCHAR(500) NOT NULL,
    option_d VARCHAR(500) NOT NULL,
    correct_option ENUM('A', 'B', 'C', 'D') NOT NULL,
    marks INT NOT NULL DEFAULT 1,
    CONSTRAINT fk_questions_exam
    FOREIGN KEY (exam_id)
    REFERENCES exams(exam_id)
    ON DELETE CASCADE
    ) ENGINE=InnoDB;

-- 4. Examination results
CREATE TABLE IF NOT EXISTS results (
    result_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    exam_id INT NOT NULL,
    score INT NOT NULL,
    total_questions INT NOT NULL,
    correct_answers INT NOT NULL,
    wrong_answers INT NOT NULL,
    attempted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_results_user
    FOREIGN KEY (user_id)
    REFERENCES users(user_id),
    CONSTRAINT fk_results_exam
    FOREIGN KEY (exam_id)
    REFERENCES exams(exam_id)
    ) ENGINE=InnoDB;

-- Notes:
-- - Deleting an examination also deletes its questions.
-- - An examination with existing results cannot be deleted until those
--   result records are handled, because the results foreign key restricts it.
-- - User accounts referenced by results cannot be deleted until those
--   result records are handled.
-- - Passwords stored in users.password_hash should be BCrypt hashes,
--   never plain-text passwords.
