/*package com.Keshaw;

public class Main {

    public static void main(String[] args) {

        System.out.println("================================");
        System.out.println("  ONLINE EXAMINATION SYSTEM");
        System.out.println("================================");
        System.out.println("Project setup successful!");
    }
}



package com.Keshaw.oes.util;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        try (Connection connection = DBconnection.getConnection()) {

            System.out.println("MySQL connection successful!");
            System.out.println("Database: "
                    + connection.getCatalog());

        } catch (Exception e) {
            System.out.println("MySQL connection failed.");
            e.printStackTrace();
        }
    }
}




package com.Keshaw.oes;

import com.Keshaw.oes.dao.UserDAO;
import com.Keshaw.oes.model.User;

public class Main {

    public static void main(String[] args) {

        UserDAO userDAO = new UserDAO();

        // Test registration
        User newUser = new User();
        newUser.setFullName("Test Student");
        newUser.setEmail("student.test@example.com");
        newUser.setPasswordHash("TestPassword123");

        try {
            boolean registered = userDAO.registerUser(newUser);

            if (registered) {
                System.out.println("Registration successful!");
            } else {
                System.out.println("Registration failed.");
            }

        } catch (Exception e) {
            System.out.println("Registration error: " + e.getMessage());
        }

        // Test login with the correct password
        try {
            User loggedInUser = userDAO.loginUser(
                    "student.test@example.com",
                    "TestPassword123"
            );

            if (loggedInUser != null) {
                System.out.println("Login successful!");
                System.out.println("Welcome, "
                        + loggedInUser.getFullName());
            } else {
                System.out.println("Login failed.");
            }

        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
        }

        // Test login with an incorrect password
        try {
            User loggedInUser = userDAO.loginUser(
                    "student.test@example.com",
                    "WrongPassword"
            );

            if (loggedInUser == null) {
                System.out.println(
                        "Incorrect password rejected successfully!"
                );
            } else {
                System.out.println(
                        "Error: incorrect password was accepted."
                );
            }

        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
        }
    }
}



package com.Keshaw.oes;

import com.Keshaw.oes.dao.QuestionDAO;
import com.Keshaw.oes.model.Question;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        QuestionDAO questionDAO = new QuestionDAO();

        try {
            List<Question> questions =
                    questionDAO.getQuestionsByExamId(1);

            System.out.println("Total questions: "
                    + questions.size());

            for (Question question : questions) {
                System.out.println(
                        question.getQuestionId() + ". "
                                + question.getQuestionText()
                );
            }

        } catch (Exception e) {
            System.out.println("Error retrieving questions.");
            e.printStackTrace();
        }
    }
}


package com.Keshaw.oes;

import com.Keshaw.oes.Service.ExamEvaluator;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        ExamEvaluator evaluator = new ExamEvaluator();

        // Replace 1 with an existing student user_id.
        int userId = 1;

        // Replace 1 with the actual exam_id.
        int examId = 1;

        Map<Integer, String> answers = new HashMap<>();

        // Question IDs may differ in your database.
        // These answers are sample answers.
        answers.put(1, "B");
        answers.put(2, "C");
        answers.put(3, "D");
        answers.put(4, "C");
        answers.put(5, "B");

        try {
            boolean saved = evaluator.evaluateAndSaveExam(
                    userId, examId, answers);

            if (saved) {
                System.out.println(
                        "Exam evaluated and result saved successfully!");
            } else {
                System.out.println("Result could not be saved.");
            }

        } catch (Exception e) {
            System.out.println("Error processing exam result.");
            e.printStackTrace();
        }
    }
}

 */

package com.Keshaw.oes;

import com.Keshaw.oes.ui.AddExamFrame;
import com.Keshaw.oes.ui.LoginFrame;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new LoginFrame();
        });
    }
}


/*
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddExamFrame::new);
    }
}

 */
