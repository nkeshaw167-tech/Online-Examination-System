# Online Examination System

A desktop-based Online Examination System developed using Java Swing, JDBC and MySQL. The application provides separate interfaces for administrators and students to manage examinations, attempt tests and view examination results.

## Features

### Student Module
- Student registration and login.
- Secure password hashing using BCrypt.
- View available active examinations.
- Attempt multiple-choice examinations.
- Countdown timer with automatic submission.
- Navigate between questions.
- View examination results.
- View previous examination history.
- Update profile password.

### Admin Module
- Administrator login.
- Create and manage examinations.
- Activate and deactivate examinations.
- Add, edit and delete examination questions.
- View student examination results.
- Search and filter student results.

## Technologies Used

| Technology | Purpose |
|---|---|
| Java 17 | Core programming language |
| Java Swing | Graphical User Interface |
| JDBC | Database connectivity |
| MySQL | Database management |
| Maven | Dependency management |
| jBCrypt | Password hashing |
| IntelliJ IDEA | Development environment |

## Database

Database name: `online_examination_system`

The project uses the following tables:

- `users` – Stores student and administrator details.
- `exams` – Stores examination information.
- `questions` – Stores examination questions and options.
- `results` – Stores examination results.

## Installation and Setup

### 1. Clone the Repository

```bash
git clone https://github.com/nkeshaw167-tech/Online-Examination-System.git
```

### 2. Open the Project

Open the cloned project in IntelliJ IDEA as a Maven project.

### 3. Configure MySQL

Create the database:

```sql
CREATE DATABASE online_examination_system;
```

Create the required tables using the SQL schema provided with the project.

### 4. Configure Database Connection

Navigate to:

`src/main/resources/`

Copy `db.properties.example` and rename the copy to `db.properties`.

Update the configuration:

```properties
db.url=jdbc:mysql://localhost:3306/online_examination_system
db.username=your_mysql_username
db.password=your_mysql_password
```

Use your own MySQL credentials.

### 5. Run the Application

- Make sure MySQL is running.
- Reload the Maven project in IntelliJ IDEA.
- Locate the application's main class.
- Run the main method to launch the application.

## Project Structure

```text
OnlineExaminationSystem/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/Keshaw/oes/
│       │       ├── dao/
│       │       ├── model/
│       │       ├── ui/
│       │       ├── util/
│       │       └── Service/
│       │
│       └── resources/
│           └── db.properties.example
│
├── pom.xml
├── .gitignore
└── README.md
```

## Security

- Passwords are hashed using BCrypt.
- Database credentials are excluded from Git tracking.
- Database operations use JDBC.

## Author

**KESHAW NARAYAN**

GitHub: https://github.com/nkeshaw167-tech

---

*This project was developed as a Java application project to demonstrate object-oriented programming, database connectivity, GUI development and CRUD operations.*