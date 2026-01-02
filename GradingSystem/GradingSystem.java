import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

/**
 * Student Management System
 * 
 * A complete system for managing student records including grades, pass/fail
 * status,
 * and fee payment tracking. Demonstrates object oriented programming in Java.
 * 
 * Concepts covered:
 * - Classes and Objects (the Student class)
 * - ArrayList for dynamic collections
 * - File I/O for data persistence
 * - Object Oriented Design principles
 * - Menu driven application structure
 * - Input validation
 */
public class GradingSystem {

    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Student> students = new ArrayList<>();

    // Grading thresholds can be adjusted here
    static final int EXAM_PASS_MARK = 25;
    static final int ASSESSMENT_PASS_MARK = 15;
    static final int FULL_FEE = 100;

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("       Student Management System");
        System.out.println("===========================================");

        // Try to load existing data on startup
        loadStudentsFromFile("students.txt");

        boolean running = true;

        while (running) {
            showMenu();
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addStudent();
                    break;
                case "2":
                    listAllStudents();
                    break;
                case "3":
                    searchStudent();
                    break;
                case "4":
                    showStatistics();
                    break;
                case "5":
                    updateStudent();
                    break;
                case "6":
                    deleteStudent();
                    break;
                case "7":
                    saveStudentsToFile("students.txt");
                    break;
                case "8":
                    gradeExplanation();
                    break;
                case "9":
                    System.out.println("\nGoodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("\nInvalid option. Please try again.");
            }
        }

        scanner.close();
    }

    static void showMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Add new student");
        System.out.println("2. List all students");
        System.out.println("3. Search for student");
        System.out.println("4. Show statistics");
        System.out.println("5. Update student record");
        System.out.println("6. Delete student");
        System.out.println("7. Save to file");
        System.out.println("8. How grading works");
        System.out.println("9. Exit");
        System.out.println("\nTotal students: " + students.size());
    }

    /**
     * Adds a new student by gathering all their information
     */
    static void addStudent() {
        System.out.println("\n--- Add New Student ---");

        System.out.print("Student ID: ");
        String id = scanner.nextLine().trim();

        // Check if ID already exists
        if (findStudentById(id) != null) {
            System.out.println("A student with this ID already exists!");
            return;
        }

        System.out.print("Full Name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        // Get exam score with validation
        int exam = getValidScore("Exam Score (0 to 50): ", 0, 50);

        // Get assessment score with validation
        int assessment = getValidScore("Assessment Score (0 to 50): ", 0, 50);

        // Get fees paid
        int feesPaid = getValidScore("Fees Paid (0 to 100): ", 0, 100);

        // Create and add the student
        Student student = new Student(id, name, exam, assessment, feesPaid);
        students.add(student);

        System.out.println("\nStudent added successfully!");
        System.out.println(student.getSummary());
    }

    /**
     * Helper method to get a valid integer within a range
     */
    static int getValidScore(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Please enter a value between " + min + " and " + max);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    /**
     * Lists all students with their status
     */
    static void listAllStudents() {
        System.out.println("\n--- All Students ---");

        if (students.isEmpty()) {
            System.out.println("No students in the system yet.");
            return;
        }

        // Print header
        System.out.println(String.format("%-10s %-20s %-6s %-6s %-6s %-10s %-12s",
                "ID", "Name", "Exam", "Assess", "Fees", "Status", "Certificate"));
        System.out.println("-".repeat(75));

        for (Student s : students) {
            System.out.println(s.toTableRow());
        }
    }

    /**
     * Search for a student by ID or name
     */
    static void searchStudent() {
        System.out.println("\n--- Search Student ---");
        System.out.print("Enter ID or name to search: ");
        String query = scanner.nextLine().trim().toLowerCase();

        if (query.isEmpty()) {
            System.out.println("Search term cannot be empty.");
            return;
        }

        ArrayList<Student> results = new ArrayList<>();

        for (Student s : students) {
            // Search by ID or name (case insensitive)
            if (s.id.toLowerCase().contains(query) ||
                    s.name.toLowerCase().contains(query)) {
                results.add(s);
            }
        }

        if (results.isEmpty()) {
            System.out.println("No students found matching '" + query + "'");
        } else {
            System.out.println("\nFound " + results.size() + " student(s):\n");
            for (Student s : results) {
                System.out.println(s.getSummary());
                System.out.println();
            }
        }
    }

    /**
     * Shows class statistics like averages, pass rates, etc.
     */
    static void showStatistics() {
        System.out.println("\n--- Class Statistics ---");

        if (students.isEmpty()) {
            System.out.println("No students to analyze.");
            return;
        }

        int totalExam = 0;
        int totalAssessment = 0;
        int totalFees = 0;
        int passCount = 0;
        int failCount = 0;
        int certificateCount = 0;
        int highestTotal = 0;
        int lowestTotal = 100;
        Student topStudent = null;
        Student lowestStudent = null;

        for (Student s : students) {
            int total = s.examScore + s.assessmentScore;
            totalExam += s.examScore;
            totalAssessment += s.assessmentScore;
            totalFees += s.feesPaid;

            if (s.hasPassed()) {
                passCount++;
                if (s.getCertificate()) {
                    certificateCount++;
                }
            } else {
                failCount++;
            }

            if (total > highestTotal) {
                highestTotal = total;
                topStudent = s;
            }
            if (total < lowestTotal) {
                lowestTotal = total;
                lowestStudent = s;
            }
        }

        int count = students.size();
        double avgExam = (double) totalExam / count;
        double avgAssessment = (double) totalAssessment / count;
        double passRate = (double) passCount / count * 100;

        System.out.println("\nTotal Students: " + count);
        System.out.println();
        System.out.println("Average Exam Score: " + String.format("%.1f", avgExam) + " / 50");
        System.out.println("Average Assessment Score: " + String.format("%.1f", avgAssessment) + " / 50");
        System.out.println("Average Total: " + String.format("%.1f", avgExam + avgAssessment) + " / 100");
        System.out.println();
        System.out.println("Passed: " + passCount + " | Failed: " + failCount);
        System.out.println("Pass Rate: " + String.format("%.1f", passRate) + "%");
        System.out.println("Certificates Issued: " + certificateCount);
        System.out.println();

        if (topStudent != null) {
            System.out.println("Top Student: " + topStudent.name + " (" + highestTotal + "/100)");
        }
        if (lowestStudent != null) {
            System.out.println("Needs Help: " + lowestStudent.name + " (" + lowestTotal + "/100)");
        }
    }

    /**
     * Update an existing students record
     */
    static void updateStudent() {
        System.out.println("\n--- Update Student ---");
        System.out.print("Enter student ID to update: ");
        String id = scanner.nextLine().trim();

        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("\nCurrent record:");
        System.out.println(student.getSummary());

        System.out.println("\nWhat would you like to update?");
        System.out.println("1. Name");
        System.out.println("2. Exam Score");
        System.out.println("3. Assessment Score");
        System.out.println("4. Fees Paid");
        System.out.println("5. Cancel");

        System.out.print("Choice: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                System.out.print("New name: ");
                student.name = scanner.nextLine().trim();
                break;
            case "2":
                student.examScore = getValidScore("New exam score (0 to 50): ", 0, 50);
                break;
            case "3":
                student.assessmentScore = getValidScore("New assessment score (0 to 50): ", 0, 50);
                break;
            case "4":
                student.feesPaid = getValidScore("New fees paid (0 to 100): ", 0, 100);
                break;
            case "5":
                System.out.println("Update cancelled.");
                return;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        System.out.println("\nRecord updated!");
        System.out.println(student.getSummary());
    }

    /**
     * Delete a student from the system
     */
    static void deleteStudent() {
        System.out.println("\n--- Delete Student ---");
        System.out.print("Enter student ID to delete: ");
        String id = scanner.nextLine().trim();

        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("\nAbout to delete: " + student.name);
        System.out.print("Are you sure? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("yes") || confirm.equals("y")) {
            students.remove(student);
            System.out.println("Student deleted.");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    /**
     * Explains the grading rules
     */
    static void gradeExplanation() {
        System.out.println("\n===========================================");
        System.out.println("         How The Grading Works");
        System.out.println("===========================================");

        System.out.println("\nPASSING REQUIREMENTS:");
        System.out.println("  Exam Score:       At least " + EXAM_PASS_MARK + " out of 50");
        System.out.println("  Assessment Score: At least " + ASSESSMENT_PASS_MARK + " out of 50");
        System.out.println("  Must pass BOTH to be considered passed overall");

        System.out.println("\nCONDONED PASS:");
        System.out.println("  Students can still pass if they barely miss one component:");
        System.out.println("  - Exam = 25, Assessment = 14 (missed by 1 point)");
        System.out.println("  - Exam = 24, Assessment = 15 (missed by 1 point)");

        System.out.println("\nCERTIFICATE:");
        System.out.println("  You must pass AND pay fees in full (" + FULL_FEE + "%)");
        System.out.println("  If you pass but havent paid, no certificate is issued");

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Helper to find a student by their ID
     */
    static Student findStudentById(String id) {
        for (Student s : students) {
            if (s.id.equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Saves all students to a text file
     * Format: ID|Name|Exam|Assessment|Fees
     */
    static void saveStudentsToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Student s : students) {
                // Use pipe character as delimiter since names might have commas
                writer.write(s.id + "|" + s.name + "|" + s.examScore + "|" +
                        s.assessmentScore + "|" + s.feesPaid + "\n");
            }
            System.out.println("\nSaved " + students.size() + " students to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    /**
     * Loads students from a text file
     */
    static void loadStudentsFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            return; // No saved data yet, thats fine
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int count = 0;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    try {
                        String id = parts[0];
                        String name = parts[1];
                        int exam = Integer.parseInt(parts[2]);
                        int assessment = Integer.parseInt(parts[3]);
                        int fees = Integer.parseInt(parts[4]);

                        students.add(new Student(id, name, exam, assessment, fees));
                        count++;
                    } catch (NumberFormatException e) {
                        // Skip malformed lines
                    }
                }
            }

            if (count > 0) {
                System.out.println("Loaded " + count + " students from " + filename);
            }
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }
}

/**
 * Student class represents a single student record
 * 
 * This is a simple example of Object Oriented Programming (OOP).
 * We put all the data about a student together with methods that
 * work on that data. This is called encapsulation.
 */
class Student {
    // Student properties (fields)
    String id;
    String name;
    int examScore;
    int assessmentScore;
    int feesPaid;

    // Constructor to create a new student with all info at once
    public Student(String id, String name, int examScore, int assessmentScore, int feesPaid) {
        this.id = id;
        this.name = name;
        this.examScore = examScore;
        this.assessmentScore = assessmentScore;
        this.feesPaid = feesPaid;
    }

    /**
     * Checks if the student passed the exam component
     */
    public boolean passedExam() {
        return examScore >= GradingSystem.EXAM_PASS_MARK;
    }

    /**
     * Checks if the student passed the assessment component
     */
    public boolean passedAssessment() {
        return assessmentScore >= GradingSystem.ASSESSMENT_PASS_MARK;
    }

    /**
     * Checks if the student qualifies for condoned pass
     * This is when they barely miss one component
     */
    public boolean isCondoned() {
        boolean almostPassedAssessment = examScore == 25 && assessmentScore == 14;
        boolean almostPassedExam = examScore == 24 && assessmentScore == 15;
        return almostPassedAssessment || almostPassedExam;
    }

    /**
     * Returns true if student has passed overall
     */
    public boolean hasPassed() {
        boolean passedBoth = passedExam() && passedAssessment();
        return passedBoth || isCondoned();
    }

    /**
     * Returns true if student gets a certificate
     * Must pass AND pay fees in full
     */
    public boolean getCertificate() {
        return hasPassed() && feesPaid >= GradingSystem.FULL_FEE;
    }

    /**
     * Returns the students overall status as a string
     */
    public String getStatus() {
        if (hasPassed()) {
            if (isCondoned()) {
                return "CONDONED";
            }
            return "PASSED";
        }
        return "FAILED";
    }

    /**
     * Returns a detailed summary of the student
     */
    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append("\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Exam: ").append(examScore).append("/50 - ");
        sb.append(passedExam() ? "PASSED" : "FAILED").append("\n");
        sb.append("Assessment: ").append(assessmentScore).append("/50 - ");
        sb.append(passedAssessment() ? "PASSED" : "FAILED").append("\n");
        sb.append("Fees: ").append(feesPaid).append("%\n");
        sb.append("Status: ").append(getStatus()).append("\n");
        sb.append("Certificate: ").append(getCertificate() ? "YES" : "NO");

        if (hasPassed() && !getCertificate()) {
            sb.append(" (pay fees to receive)");
        }

        return sb.toString();
    }

    /**
     * Returns a single line for table display
     */
    public String toTableRow() {
        String shortName = name.length() > 18 ? name.substring(0, 15) + "..." : name;
        return String.format("%-10s %-20s %-6d %-6d %-6d %-10s %-12s",
                id, shortName, examScore, assessmentScore, feesPaid,
                getStatus(), getCertificate() ? "YES" : "NO");
    }
}
