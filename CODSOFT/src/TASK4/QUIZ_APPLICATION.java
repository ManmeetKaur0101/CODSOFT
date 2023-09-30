package TASK4;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;

class QuizQuestion {
    private String question;
    private List<String> options;
    private int correctOption;

    public QuizQuestion(String question, List<String> options, int correctOption) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectOption() {
        return correctOption;
    }
}

class Quiz {
    private List<QuizQuestion> questions;
    private int score;
    private int currentQuestionIndex;

    public Quiz(List<QuizQuestion> questions) {
        this.questions = questions;
        this.score = 0;
        this.currentQuestionIndex = 0;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (currentQuestionIndex < questions.size()) {
            QuizQuestion question = questions.get(currentQuestionIndex);
            System.out.println("Question " + (currentQuestionIndex + 1) + ": " + question.getQuestion());

            List<String> options = question.getOptions();
            for (int j = 0; j < options.size(); j++) {
                System.out.println((j + 1) + ". " + options.get(j));
            }

            Timer timer = new Timer();

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    System.out.println("\nTime's up! The correct answer was option " + question.getCorrectOption() + ".\n");
                    goToNextQuestion();
                }
            };

            // Start a timer task to limit the time for this question
            timer.schedule(task, 10 * 1000); // 10-second timer (in milliseconds)

            int userChoice = -1;

            // Wait for the user's input
            try {
                System.out.print("Enter your choice (1-" + options.size() + "): ");
                userChoice = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Consume invalid input
                System.out.println("Invalid input. Please enter a number.");
            }

            // Cancel the timer if the user provided input
            task.cancel();

            if (userChoice >= 1 && userChoice <= options.size()) {
                if (userChoice == question.getCorrectOption()) {
                    System.out.println("Correct!\n");
                    score++;
                } else {
                    System.out.println("Incorrect. The correct answer was option " + question.getCorrectOption() + ".\n");
                }
            } else {
                System.out.println("Invalid choice. The correct answer was option " + question.getCorrectOption() + ".\n");
            }

            // Move to the next question
            currentQuestionIndex++;

            // If it's not the last question, ask the user if they want to continue
            if (currentQuestionIndex < questions.size()) {
                System.out.print("Do you want to continue to the next question? (yes/no): ");
                String continueChoice = scanner.next().toLowerCase();
                if (!continueChoice.equals("yes")) {
                    break; // Show the result if the user chooses not to continue
                }
            }
        }

        // Display the total score and a summary of correct/incorrect answers
        System.out.println("Quiz Completed! Your score is: " + score + "/" + questions.size());
        scanner.close();
    }

    private void goToNextQuestion() {
        currentQuestionIndex++;
    }
}

public class QUIZ_APPLICATION {
    public static void main(String[] args) {
        List<QuizQuestion> questions = new ArrayList<>();
        questions.add(new QuizQuestion("What is the capital of France?",
                Arrays.asList("London", "Berlin", "Paris", "Madrid"), 3));
        questions.add(new QuizQuestion("Which planet is known as the Red Planet?",
                Arrays.asList("Earth", "Mars", "Jupiter", "Venus"), 2));
        questions.add(new QuizQuestion("What is the largest mammal?",
                Arrays.asList("Elephant", "Whale Shark", "Giraffe", "Blue Whale"), 4));

        Quiz quiz = new Quiz(questions);
        quiz.start();
    }
}
