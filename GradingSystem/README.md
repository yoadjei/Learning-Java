# Student Management System

A complete Java application for managing student records with grades, pass/fail tracking, and certificate management.

## What You'll Learn

This project demonstrates Object Oriented Programming (OOP) in Java:

- **Classes and Objects** using the Student class
- **ArrayList** for managing collections of students
- **Encapsulation** keeping data and methods together
- **File I/O** for persistent storage
- **Input Validation** for robust user input
- **Static vs Instance** understanding the difference

## How to Run

```bash
javac GradingSystem.java
java GradingSystem
```

## Features

### Core Functionality
- Add new students with ID, name, and scores
- List all students in a formatted table
- Search by ID or name
- Update any student field
- Delete students

### Grading Logic
- Automatic pass/fail calculation
- Condoned pass support (barely missing one component)
- Certificate eligibility based on fees

### Statistics
- Average scores for exam and assessment
- Pass rate percentage
- Top student and student needing help
- Certificate count

### Persistence
- Auto loads data on startup
- Save to file when you want
- Simple text format that you can read

## Grading Rules

| Component | Pass Threshold |
|-----------|---------------|
| Exam | 25 out of 50 |
| Assessment | 15 out of 50 |
| Fees | 100% for certificate |

### Condoned Pass
Students who barely miss one component can still pass:
- Exam = 25, Assessment = 14 (missed by 1)
- Exam = 24, Assessment = 15 (missed by 1)

## Sample Output

```
--- All Students ---
ID         Name                 Exam   Assess Fees   Status     Certificate 
---------------------------------------------------------------------------
STU001     John Doe             35     28     100    PASSED     YES         
STU002     Jane Smith           20     18     100    FAILED     NO          
STU003     Bob Wilson           25     14     80     CONDONED   NO          
```

## File Format

Students are saved in a simple text format using pipe delimiters:

```
STU001|John Doe|35|28|100
STU002|Jane Smith|20|18|100
STU003|Bob Wilson|25|14|80
```

## The Student Class

This is a great example of OOP. The Student class:
- Holds all data about one student
- Has methods to check pass status
- Knows how to display itself
- Keeps related code together

```java
class Student {
    String id;
    String name;
    int examScore;
    int assessmentScore;
    int feesPaid;
    
    public boolean hasPassed() { ... }
    public boolean getCertificate() { ... }
}
```

## Try These Modifications

1. Add more subjects (not just exam and assessment)
2. Implement grade letters (A, B, C, D, F)
3. Add sorting options (by name, score, status)
4. Export reports to HTML
5. Add student photos using file paths
6. Implement undo functionality for deletes
