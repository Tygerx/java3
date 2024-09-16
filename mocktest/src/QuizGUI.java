import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class QuizGUI {
    private JFrame frame;
    private JLabel questionLabel;
    private JComboBox<String> optionsComboBox;
    private JButton nextButton;
    private JButton previousButton;
    private JLabel timerLabel;
    private JLabel questionCountLabel; // Declare questionCountLabel here
    private Timer timer;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private String userName;
    private int timeRemaining = 300; // Example time in seconds

    public QuizGUI() {
        questions = new ArrayList<>();
        loadQuestions();
        askUserName();
        createGUI();
        startTimer();
    }

    private void askUserName() {
        userName = JOptionPane.showInputDialog(frame, "Enter your name:");
        if (userName == null || userName.trim().isEmpty()) {
            userName = "User";
        }
    }

    private void createGUI() {
        frame = new JFrame("Quiz Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        // Create a panel for the clock icon and timer label
        JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        // Load the clock icon
        ImageIcon clockIcon = new ImageIcon("C:\\Users\\TYGER\\Desktop\\clockicon.png");
        JLabel clockLabel = new JLabel(clockIcon);
        timerPanel.add(clockLabel);

        timerLabel = new JLabel("Time remaining: " + formatTime(timeRemaining));
        timerPanel.add(timerLabel);

        topPanel.add(timerPanel, BorderLayout.EAST);

        // Add question count label
        questionCountLabel = new JLabel();
        updateQuestionCount();
        topPanel.add(questionCountLabel, BorderLayout.WEST);

        frame.add(topPanel, BorderLayout.NORTH);

        // Create a panel for the question label and options combo box
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Make question text bolder and bigger
        questionLabel.setPreferredSize(new Dimension(350, 50)); // Set preferred size
        JScrollPane scrollPane = new JScrollPane(questionLabel); // Add questionLabel to JScrollPane
        questionPanel.add(scrollPane);

        optionsComboBox = new JComboBox<>();
        optionsComboBox.setPreferredSize(new Dimension(150, 20)); // Set smaller preferred size
        optionsComboBox.setRenderer(new CustomComboBoxRenderer()); // Set custom renderer
        questionPanel.add(optionsComboBox);

        frame.add(questionPanel, BorderLayout.CENTER);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        previousButton = new JButton("<- Previous");
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentQuestionIndex > 0) {
                    currentQuestionIndex--;
                    displayQuestion();
                }
            }
        });
        buttonPanel.add(previousButton);

        nextButton = new JButton("Next ->");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    displayQuestion();
                } else {
                    showResult();
                }
            }
        });
        buttonPanel.add(nextButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        displayQuestion();
        frame.setVisible(true);
    }

    private void displayQuestion() {
        Question question = questions.get(currentQuestionIndex);
        questionLabel.setText("<html>" + question.getQuestionText() + "</html>"); // Use HTML to wrap text
        optionsComboBox.removeAllItems();
        for (String option : question.getOptions()) {
            optionsComboBox.addItem(option);
        }
        updateQuestionCount(); // Update question count
    }

    private void updateQuestionCount() {
        questionCountLabel.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.size());
    }

    private void checkAnswer() {
        Question question = questions.get(currentQuestionIndex);
        int selectedIndex = optionsComboBox.getSelectedIndex();
        if (question.isCorrect(selectedIndex)) {
            score++;
        }
    }

    private void showResult() {
        double percentage = ((double) score / questions.size()) * 100;
        String grade;
        if (percentage >= 85) {
            grade = "A";
        } else if (percentage >= 60) {
            grade = "B";
        } else if (percentage >= 50) {
            grade = "C";
        } else if (percentage >= 40) {
            grade = "D";
        } else {
            grade = "E";
        }

        JOptionPane.showMessageDialog(frame, userName + ", your test is completed! Your score: " + score + "/" + questions.size() + "\nPercentage: " + String.format("%.2f", percentage) + "%\nGrade: " + grade);
        frame.dispose();
    }

    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                timerLabel.setText("Time remaining: " + formatTime(timeRemaining));
                if (timeRemaining <= 0) {
                    ((Timer) e.getSource()).stop();
                    showResult();
                }
            }
        });
        timer.start();
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
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


public class CustomComboBoxRenderer extends JLabel implements ListCellRenderer<Object> {
    public CustomComboBoxRenderer() {
        setOpaque(true);
        setFont(new Font("Arial", Font.PLAIN, 14));
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.toString());
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        return this;
    }
}


    public static void main(String[] args) {
        new QuizGUI();
    }
}


































// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.util.ArrayList;
// import java.util.List;



//     private JFrame frame;
//     private JLabel questionLabel;
//     private JComboBox<String> optionsComboBox;
//     private JButton nextButton;
//     private JButton previousButton; // Declare the Previous button
//     private JLabel timerLabel;
//     private Timer timer;


//     public  QuizGUI() {
//         questions = new ArrayList<>();
//         loadQuestions();
//         askUserName();
//         createGUI();
//         startTimer();
//     }

//     private void askUserName() {
//         userName = JOptionPane.showInputDialog(frame, "Enter your name:");
//         if (userName == null || userName.trim().isEmpty()) {
//             userName = "User";
//         }
//     }

//      private void loadQuestions() {
//         questions.add(new Question("What does CPU stand for?", List.of("Central Processing Unit", "Central Programming Unit", "Central Performance Unit", "Central Power Unit"), 0));
//         questions.add(new Question("Which language is primarily used for web development?", List.of("Python", "Java", "HTML", "C++"), 2));
//         questions.add(new Question("What is the main function of an operating system?", List.of("To manage hardware and software resources", "To compile programs", "To design websites", "To perform calculations"), 0));
//         questions.add(new Question("Which of the following is a non-volatile memory?", List.of("RAM", "ROM", "Cache", "Register"), 1));
//         questions.add(new Question("What does HTTP stand for?", List.of("HyperText Transfer Protocol", "HyperText Transmission Protocol", "HyperText Transfer Program", "HyperText Transmission Program"), 0));
//         questions.add(new Question("Which company developed the Java programming language?", List.of("Microsoft", "Apple", "Sun Microsystems", "IBM"), 2));
//         questions.add(new Question("What is the purpose of an IP address?", List.of("To identify a device on a network", "To encrypt data", "To store data", "To compile code"), 0));
//         questions.add(new Question("Which of the following is an example of an operating system?", List.of("Microsoft Word", "Linux", "Google Chrome", "Java"), 1));
//         questions.add(new Question("What does GUI stand for?", List.of("Graphical User Interface", "General User Interface", "Global User Interface", "Graphical Universal Interface"), 0));
//         questions.add(new Question("Which protocol is used to send emails?", List.of("FTP", "HTTP", "SMTP", "SNMP"), 2));
//     }
    
  
// // private JLabel questionCountLabel;
// // private JLabel timerLabel;
// // private Timer timer;
// // private int timeRemaining = 300; // Example time in seconds

// private JLabel questionCountLabel;
// private JLabel timerLabel;
// private Timer timer;
// private int timeRemaining = 300; // Example time in seconds

// private void createGUI() {
//     frame = new JFrame("Quiz Application");
//     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//     frame.setSize(400, 300);
//     frame.setLayout(new BorderLayout());

//     JPanel topPanel = new JPanel(new BorderLayout());

//     // Create a panel for the clock icon and timer label
//     JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    
//     // Load the clock icon
//     ImageIcon clockIcon = new ImageIcon("C:\\Users\\TYGER\\Desktop\\clockicon.png");
//     JLabel clockLabel = new JLabel(clockIcon);
//     timerPanel.add(clockLabel);

//     timerLabel = new JLabel("Time remaining: " + formatTime(timeRemaining));
//     timerPanel.add(timerLabel);

//     topPanel.add(timerPanel, BorderLayout.EAST);

//     // Add question count label
//     questionCountLabel = new JLabel();
//     updateQuestionCount();
//     topPanel.add(questionCountLabel, BorderLayout.WEST);

//     frame.add(topPanel, BorderLayout.NORTH);

//     // Create a panel for the question label and options combo box
//     JPanel questionPanel = new JPanel();
//     questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
    
//     questionLabel = new JLabel();
//     questionLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Make question text bolder and bigger
//     questionLabel.setPreferredSize(new Dimension(350, 50)); // Set preferred size
//     JScrollPane scrollPane = new JScrollPane(questionLabel); // Add questionLabel to JScrollPane
//     questionPanel.add(scrollPane);

//     optionsComboBox = new JComboBox<>();
//     optionsComboBox.setPreferredSize(new Dimension(150, 20)); // Set smaller preferred size
//     optionsComboBox.setRenderer(new CustomComboBoxRenderer()); // Set custom renderer
//     questionPanel.add(optionsComboBox);

//     frame.add(questionPanel, BorderLayout.CENTER);

//     // Create a panel for the buttons
//     JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//     previousButton = new JButton("<- Previous");
//     previousButton.addActionListener(new ActionListener() {
//         @Override
//         public void actionPerformed(ActionEvent e) {
//             if (currentQuestionIndex > 0) {
//                 currentQuestionIndex--;
//                 displayQuestion();
//             }
//         }
//     });
//     buttonPanel.add(previousButton);

//     nextButton = new JButton("Next ->");
//     nextButton.addActionListener(new ActionListener() {
//         @Override
//         public void actionPerformed(ActionEvent e) {
//             checkAnswer();
//             currentQuestionIndex++;
//             if (currentQuestionIndex < questions.size()) {
//                 displayQuestion();
//             } else {
//                 showResult();
//             }
//         }
//     });
//     buttonPanel.add(nextButton);

//     frame.add(buttonPanel, BorderLayout.SOUTH);

//     displayQuestion();
//     frame.setVisible(true);

//     startTimer(); // Start the timer
// }


// private void updateQuestionCount() {
//     questionCountLabel.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.size());
// }

// private void startTimer() {
//     timer = new Timer(1000, new ActionListener() {
//         @Override
//         public void actionPerformed(ActionEvent e) {
//             timeRemaining--;
//             timerLabel.setText("Time remaining: " + formatTime(timeRemaining));
//             if (timeRemaining <= 0) {
//                 ((Timer) e.getSource()).stop();
//                 showResult();
//             }
//         }
//     });
//     timer.start();
// }

// private String formatTime(int seconds) {
//     int minutes = seconds / 60;
//     int secs = seconds % 60;
//     return String.format("%02d:%02d", minutes, secs);
// }

// private void displayQuestion() {
//     Question question = questions.get(currentQuestionIndex);
//     questionLabel.setText("<html>" + question.getQuestionText() + "</html>"); // Use HTML to wrap text
//     optionsComboBox.removeAllItems();
//     for (String option : question.getOptions()) {
//         optionsComboBox.addItem(option);
//     }
//     updateQuestionCount(); // Update question count
// }

// private void updateQuestionCount() {
//     questionCountLabel.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.size());
// }

// private void startTimer() {
//     timer = new Timer(1000, new ActionListener() {
//         @Override
//         public void actionPerformed(ActionEvent e) {
//             timeRemaining--;
//             timerLabel.setText("Time remaining: " + formatTime(timeRemaining));
//             if (timeRemaining <= 0) {
//                 ((Timer) e.getSource()).stop();
//                 showResult();
//             }
//         }
//     });
//     timer.start();
// }



//     private String formatTime(int seconds) {
//         int minutes = seconds / 60;
//         int remainingSeconds = seconds % 60;
//         return String.format("%02d:%02d", minutes, remainingSeconds);
//     }

    

// public class CustomComboBoxRenderer extends JLabel implements ListCellRenderer<String> {
//     public CustomComboBoxRenderer() {
//         setOpaque(true);
//         setFont(new Font("Arial", Font.BOLD, 14));
//         setBackground(Color.LIGHT_GRAY);
//         setForeground(Color.BLUE);
//     }

//     @Override
//     public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
//         setText(value);
//         if (isSelected) {
//             setBackground(Color.BLUE);
//             setForeground(Color.WHITE);
//         } else {
//             setBackground(Color.LIGHT_GRAY);
//             setForeground(Color.BLUE);
//         }
//         return this;
//     }
// }


// private void checkAnswer() {
//     Question question = questions.get(currentQuestionIndex);
//     int selectedIndex = optionsComboBox.getSelectedIndex();
//     if (question.isCorrect(selectedIndex)) {
//         score++;
//     }
// }

// private void showResult() {
//     double percentage = ((double) score / questions.size()) * 100;
//     String grade;
//     if (percentage >= 85) {
//         grade = "A";
//     } else if (percentage >= 60) {
//         grade = "B";
//     } else if (percentage >= 50) {
//         grade = "C";
//     } else if (percentage >= 40) {
//         grade = "D";
//     } else {
//         grade = "E";
//     }

//     JOptionPane.showMessageDialog(frame, userName + ", your test is completed! Your score: " + score + "/" + questions.size() + "\nPercentage: " + String.format("%.2f", percentage) + "%\nGrade: " + grade);
//     frame.dispose();
// }


// public static void main(String[] args) {
//     new QuizGUI();
// }


    
//     // public class CustomComboBoxRenderer extends JLabel implements ListCellRenderer<String> {
//     //     public CustomComboBoxRenderer() {
//     //         setOpaque(true);
//     //         setFont(new Font("Arial", Font.BOLD, 14));
//     //         setBackground(Color.LIGHT_GRAY);
//     //         setForeground(Color.BLUE);
//     //     }
    
//     //     @Override
//     //     public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
//     //         setText(value);
//     //         if (isSelected) {
//     //             setBackground(Color.BLUE);
//     //             setForeground(Color.WHITE);
//     //         } else {
//     //             setBackground(Color.LIGHT_GRAY);
//     //             setForeground(Color.BLUE);
//     //         }
//     //         return this;
//     //     }
//     // }

    
// //     private void createGUI() {
// //         frame = new JFrame("Quiz Application");
// //         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// //         frame.setSize(400, 300);
// //         frame.setLayout(new BorderLayout());
    
// //         JPanel topPanel = new JPanel(new BorderLayout());
    
// //         // Create a panel for the clock icon and timer label
// //         JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
// //         // Load the clock icon
// //         ImageIcon clockIcon = new ImageIcon("C:\\Users\\TYGER\\Desktop\\clockicon.png");
// //         JLabel clockLabel = new JLabel(clockIcon);
// //         timerPanel.add(clockLabel);
    
// //         timerLabel = new JLabel("Time remaining: " + formatTime(timeRemaining));
// //         timerPanel.add(timerLabel);
    
// //         topPanel.add(timerPanel, BorderLayout.EAST);
// //         frame.add(topPanel, BorderLayout.NORTH);
    
// //         // Create a panel for the question label and options combo box
// //         JPanel questionPanel = new JPanel();
// //         questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        
// //         questionLabel = new JLabel();
// //         questionLabel.setPreferredSize(new Dimension(350, 50)); // Set preferred size
// //         JScrollPane scrollPane = new JScrollPane(questionLabel); // Add questionLabel to JScrollPane
// //         questionPanel.add(scrollPane);
    
// //         optionsComboBox = new JComboBox<>();
// //         optionsComboBox.setPreferredSize(new Dimension(150, 20)); // Set smaller preferred size
// //         optionsComboBox.setRenderer(new CustomComboBoxRenderer()); // Set custom renderer
// //         questionPanel.add(optionsComboBox);
    
// //         frame.add(questionPanel, BorderLayout.CENTER);
    
// //         // Create a panel for the buttons
// //         JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
// //         previousButton = new JButton("<- Previous");
// //         previousButton.addActionListener(new ActionListener() {
// //             @Override
// //             public void actionPerformed(ActionEvent e) {
// //                 if (currentQuestionIndex > 0) {
// //                     currentQuestionIndex--;
// //                     displayQuestion();
// //                 }
// //             }
// //         });
// //         buttonPanel.add(previousButton);
    
// //         nextButton = new JButton("Next ->");
// //         nextButton.addActionListener(new ActionListener() {
// //             @Override
// //             public void actionPerformed(ActionEvent e) {
// //                 checkAnswer();
// //                 currentQuestionIndex++;
// //                 if (currentQuestionIndex < questions.size()) {
// //                     displayQuestion();
// //                 } else {
// //                     showResult();
// //                 }
// //             }
// //         });
// //         buttonPanel.add(nextButton);
    
// //         frame.add(buttonPanel, BorderLayout.SOUTH);
    
// //         displayQuestion();
// //         frame.setVisible(true);
// //     }
    
// //     private void displayQuestion() {
// //         Question question = questions.get(currentQuestionIndex);
// //         questionLabel.setText("<html>" + question.getQuestionText() + "</html>"); // Use HTML to wrap text
// //         optionsComboBox.removeAllItems();
// //         for (String option : question.getOptions()) {
// //             optionsComboBox.addItem(option);
// //         }
// //     }
    
//     // private void checkAnswer() {
//     //     Question question = questions.get(currentQuestionIndex);
//     //     int selectedIndex = optionsComboBox.getSelectedIndex();
//     //     if (question.isCorrect(selectedIndex)) {
//     //         score++;
//     //     }
//     // }
    
//     // private void showResult() {
//     //     double percentage = ((double) score / questions.size()) * 100;
//     //     String grade;
//     //     if (percentage >= 85) {
//     //         grade = "A";
//     //     } else if (percentage >= 60) {
//     //         grade = "B";
//     //     } else if (percentage >= 50) {
//     //         grade = "C";
//     //     } else if (percentage >= 40) {
//     //         grade = "D";
//     //     } else {
//     //         grade = "E";
//     //     }
    
//     //     JOptionPane.showMessageDialog(frame, userName + ", your test is completed! Your score: " + score + "/" + questions.size() + "\nPercentage: " + String.format("%.2f", percentage) + "%\nGrade: " + grade);
//     //     frame.dispose();
//     // }
    
// //     private void startTimer() {
// //         timer = new Timer(1000, new ActionListener() {
// //             @Override
// //             public void actionPerformed(ActionEvent e) {
// //                 // Timer logic here
// //             }
// //         });
// //         timer.start();
// //     }
    
// //     private String formatTime(int seconds) {
// //         int minutes = seconds / 60;
// //         int remainingSeconds = seconds % 60;
// //         return String.format("%02d:%02d", minutes, remainingSeconds);
// //     }

    

// public class CustomComboBoxRenderer extends JLabel implements ListCellRenderer<String> {
//     public CustomComboBoxRenderer() {
//         setOpaque(true);
//         setFont(new Font("Arial", Font.BOLD, 14));
//         setBackground(Color.LIGHT_GRAY);
//         setForeground(Color.BLUE);
//     }

// //     @Override
// //     public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
// //         setText(value);
// //         if (isSelected) {
// //             setBackground(Color.BLUE);
// //             setForeground(Color.WHITE);
// //         } else {
// //             setBackground(Color.LIGHT_GRAY);
// //             setForeground(Color.BLUE);
// //         }
// //         return this;
// //     }
// // }