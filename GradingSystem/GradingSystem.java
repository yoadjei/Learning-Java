import java.util.Scanner;

public class GradingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get student inputs
        System.out.print("Enter Exam Score: ");
        int exam = scanner.nextInt();

        System.out.print("Enter Assessment Score: ");
        int assessment = scanner.nextInt();

        System.out.print("Enter Fees Paid (100 for full payment): ");
        int feesPaid = scanner.nextInt();

        boolean passedExam = exam >= 25;
        boolean passedAssessment = assessment >= 15;
        boolean passedOverall = passedExam && passedAssessment;
        boolean condoned = (exam == 25 && assessment == 14) || (exam == 24 && assessment == 15);
        boolean feesPaidInFull = (feesPaid == 100);

        // Display which component the student passed/failed
        System.out.println("\nPerformance Summary:");
        if (passedExam) {
            System.out.println("Passed Exam");
        } else {
            System.out.println("Failed Exam");
        }

        if (passedAssessment) {
            System.out.println("Passed Assessment");
        } else {
            System.out.println("Failed Assessment");
        }

        // Determine final outcome
        if (passedOverall || condoned) {
            if (feesPaidInFull) {
                System.out.println("\n Congratulations! You have received a Certificate.");
            } else {
                System.out.println("\n You met the academic requirements, but fees are unpaid. No Certificate issued.");
            }
        } else {
            System.out.println("\n You have failed.");
            if (!passedExam && !passedAssessment) {
                System.out.println("You must repeat the course.");
            }
        }

        scanner.close();
    }
}
