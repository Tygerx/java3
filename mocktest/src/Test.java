import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {
    private List<Question> questions;

    public Test() {
        questions = new ArrayList<>();
        loadQuestions();
    }

    private void loadQuestions() {
        questions.add(new Question("What does CPU stand for?", List.of("Central Processing Unit", "Central Programming Unit", "Central Performance Unit", "Central Power Unit"), 0));
        questions.add(new Question("Which language is primarily used for web development?", List.of("Python", "Java", "HTML", "C++"), 2));
        questions.add(new Question("What is the main function of an operating system?", List.of("To manage hardware and software resources", "To compile programs", "To design websites", "To perform calculations"), 0));
        questions.add(new Question("Which of the following is a non-volatile memory?", List.of("RAM", "ROM", "Cache", "Register"), 1));
        questions.add(new Question("What does HTTP stand for?", List.of("HyperText Transfer Protocol", "HyperText Transmission Protocol", "HyperText Transfer Program", "HyperText Transmission Program"), 0));
        questions.add(new Question("Which company developed the Java programming language?", List.of("Microsoft", "Apple", "Sun Microsystems", "IBM"), 2));
        questions.add(new Question("What is the purpose of an IP address?", List.of("To identify a device on a network", "To encrypt data", "To store data", "To compile code"), 0));
        questions.add(new Question("Which of the following is an example of an operating system?", List.of("Microsoft Word", "Linux", "Google Chrome", "Java"), 1));
        questions.add(new Question("What does GUI stand for?", List.of("Graphical User Interface", "General User Interface", "Global User Interface", "Graphical Universal Interface"), 0));
        questions.add(new Question("Which protocol is used to send emails?", List.of("FTP", "HTTP", "SMTP", "SNMP"), 2));
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        int score = 0;

        for (Question question : questions) {
            System.out.println(question.getQuestionText());
            List<String> options = question.getOptions();
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ". " + options.get(i));
            }

            int answerIndex = scanner.nextInt() - 1;
            scanner.nextLine(); // Consume newline

            if (question.isCorrect(answerIndex)) {
                score++;
            }
        }

        System.out.println("Test completed! Your score: " + score + "/" + questions.size());
        scanner.close();
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.start();
    }
}
