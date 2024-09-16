import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class QuizApp extends JFrame {
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private JLabel questionLabel;
    private JRadioButton[] options;
    private ButtonGroup optionsGroup;
    private JButton nextButton;
    private Timer timer;
    private int timeRemaining;

    public QuizApp(List<Question> questions, int examDuration) {
        this.questions = questions;
        this.timeRemaining = examDuration * 60; // Convert minutes to seconds

        setTitle("MCQ Quiz App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        questionLabel = new JLabel();
        add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(4, 1));
        options = new JRadioButton[4];
        optionsGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            optionsGroup.add(options[i]);
            optionsPanel.add(options[i]);
        }
        add(optionsPanel, BorderLayout.CENTER);

        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    displayQuestion();
                } else {
                    endQuiz();
                }
            }
        });
        add(nextButton, BorderLayout.SOUTH);

        displayQuestion();
        startTimer();
    }

    private void displayQuestion() {
        Question question = questions.get(currentQuestionIndex);
        questionLabel.setText(question.getQuestionText());
        List<String> optionsList = question.getOptions();
        for (int i = 0; i < options.length; i++) {
            options[i].setText(optionsList.get(i));
        }
    }

    private void checkAnswer() {
        Question question = questions.get(currentQuestionIndex);
        for (int i = 0; i < options.length; i++) {
            if (options[i].isSelected() && options[i].getText().equals(question.getCorrectAnswer())) {
                score++;
            }
        }
    }

    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                if (timeRemaining <= 0) {
                    endQuiz();
                }
            }
        });
        timer.start();
    }

    private void endQuiz() {
        timer.stop();
        JOptionPane.showMessageDialog(this, "Quiz over! Your score: " + score);
        System.exit(0);
    }

    public static void main(String[] args) {
        List<Question> questions = List.of(
            new Question("What is the size of byte variable?", List.of("8 bit", "16 bit", "32 bit", "64 bit"), "8 bit"),
            new Question("Which loop construct in Java is best suited when the number of iterations is known?", List.of("for loop", "while loop", "do-while loop", "break statement"), "for loop")
        );
        int examDuration = 5; // Duration in minutes
        new QuizApp(questions, examDuration).setVisible(true);
    }
}
