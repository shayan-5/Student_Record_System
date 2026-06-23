# STUDENT_RECORD_MANAGEMENT_SYSTEM

A console-based Java JDBC application for managing student records using Java, JDBC, MySQL, and Maven.

## Features

* Add Student
* View All Students
* Update Student Details
* Delete Student
* Input Validation
* Student ID Generation using Auto Increment
* Formatted Student Record Display
* Exception Handling

## Tech Stack

* Java 26
* JDBC
* MySQL
* Maven

## Prerequisites

Ensure the following are installed on your system:

* Java JDK 26
* Apache Maven
* MySQL Server
* MySQL Workbench (Recommended)

Verify installation:

```bash
java -version
mvn -version
mysql --version
```

## How to Run the Project

### Step 1: Clone the Repository

```bash
git clone <repository-url>
cd STUDENT_RECORD_MANAGEMENT_SYSTEM
```

### Step 2: Create the Database

1. Open MySQL Workbench.
2. Connect to your MySQL Server.
3. Open:

```text
database/studentdb.sql
```

4. Execute the script.

This will create:

* studentdb database
* students table

### Step 3: Configure Database Credentials

Open:

```text
src/main/java/org/example/App.java
```

Update the following values according to your local MySQL configuration:

```java
String URL = "jdbc:mysql://localhost:3306/studentdb";
String USER = "your_username";
String PASS = "your_password";
```

### Step 4: Build the Project

Open a terminal in the project root directory and run:

```bash
mvn clean install
```

Maven will automatically download all required dependencies.

### Step 5: Run the Application

```bash
mvn exec:java
```

The Student Record Management System menu will appear in the console.

## Optional Database Reset

To remove all student records for testing purposes:

1. Open MySQL Workbench.
2. Execute:

```text
database/reset_database.sql
```

## Maven Dependencies

* MySQL Connector/J
* JUnit

## Project Structure

```text
STUDENT_RECORD_MANAGEMENT_SYSTEM
│
├── src/
├── database/
│   ├── studentdb.sql
│   └── reset_database.sql
├── pom.xml
├── README.md
└── .gitignore
```

## Author

Shayan Shaikh
