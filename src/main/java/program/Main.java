package program;

import models.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (var context = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            // insertProductCategory();
            // insertProductToBasket();
            // insertUserRole();
        }
    }

    private static void insertProductCategory() {
        try (var context = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = context.beginTransaction();
            Category category = new Category("iPhone", "iPhone.jpg", new Date(), false);
            context.save(category);

            Product product = new Product("iPhone 13 Pro Max", "For kozaks", new Date(), false, category);
            context.save(product);
            ProductImage productImage = new ProductImage("1.jpg", 1, new Date(), false, product);
            ProductImage productImage2 = new ProductImage("2.jpg", 1, new Date(), false, product);
            context.save(productImage);
            context.save(productImage2);
            transaction.commit();
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    private static void insertProductToBasket() {
        try (var context = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = context.beginTransaction();

            var user = new User();
            var product = new Product();
            var basket = new Basket();

            product.setId(1);
            user.setId(1);
            basket.setUser(user);
            basket.setProduct(product);
            basket.setCount(2);

            context.save(basket);
            transaction.commit();
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    private static void insertUserRole() {
        try (var context = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = context.beginTransaction();

            var role = new Role();
            UserRole userRole = new UserRole();
            var user = new User("Mark", "Janialy", "qwert@gmail.com", "39204432423", "123456");

            role.setId(1);
            context.save(user);
            userRole.setUser(user);
            userRole.setRole(role);
            context.save(userRole);
            transaction.commit();
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }
    private static void menu() {
        while (true) {
            int index = 0;
            Scanner input = new Scanner(System.in);

            System.out.println("[" + ++index + "] INSERT Role");
            System.out.println("[" + ++index + "] SHOW all roles");
            System.out.println("[" + ++index + "] INSERT question");
            System.out.println("[" + ++index + "] UPDATE question by id");
            System.out.println("[" + ++index + "] DELETE question from test by id");
            System.out.print(Color.GREEN);
            System.out.println("[" + ++index + "] To pass a test");
            System.out.print(Color.RED);
            System.out.println("[" + ++index + "] Exit");
            System.out.print(Color.RESET);

            System.out.print("Enter index to select action:");
            int selectedIndex = input.nextInt();

            switch (selectedIndex) {
                case 1: // inserting new role
                    insertRole();
                    stop(); // shows massage DONE ! and program stops until user click on some key
                    break;

                case 2: // showing all roles from db
                    showRoles();
                    stop();
                    break;

                case 3: // inserting new question in test
                    insertQuestion();
                    break;

                case 4:  // updating question by id
                    System.out.print("Enter id of question:");
                    Scanner scanId = new Scanner(System.in);
                    int id = scanId.nextInt();

                    System.out.print("Enter new question: ");
                    Scanner scanQuestion = new Scanner(System.in);
                    String newQuestion = scanQuestion.nextLine();

                    // will be soon
                    stop();
                    break;

                case 5: // deleting question by id
                    System.out.print("Enter id:");
                    int deleteId = input.nextInt();
                    deleteQuestion(deleteId);
                    stop();
                    break;

                case 6: // taking the test
                    passTest(); // user taking the test, after which he sees the result of test
                    stop();
                    break;

                case 7: // exit from the program
                    System.exit(0);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + selectedIndex);
            }
        }
    }

    private static void insertRole() {
        Role role = new Role();
        Scanner in = new Scanner(System.in);
        Session context = HibernateSessionFactoryUtil.getSessionFactory().openSession();

        System.out.print("Enter name:");
        String name = in.nextLine();
        role.setName(name);
        context.save(role);
        context.close();
    }

    private static void insertQuestion() {
        Question question = new Question();
        Scanner in = new Scanner(System.in);

        Session context = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = context.beginTransaction();

        System.out.print("Enter an question: ");
        String questionText = in.nextLine();

        question.setName(questionText);
        context.save(question);

        String action = "";
        do {
            System.out.print("Enter an answer:");
            String text = in.nextLine();

            System.out.print("True answer\n" +
                    "[0] False\n" +
                    "[1] True: ");
            boolean isTrue = Byte.parseByte(in.nextLine()) == 1 ? true : false;
            QuestionItem questionItem = new QuestionItem();
            questionItem.setText(text);
            questionItem.setTrue(isTrue);
            questionItem.setQuestion(question);
            context.save(questionItem);

            System.out.print("Add next an answer ?\n" +
                    "[0] Exit\n" +
                    "[1] Continue add: ");
            action = in.nextLine();

        } while (!action.equals("0"));
        transaction.commit();
        context.close();
    }

    private static void passTest() {
        int rating = 0, gradeOf12 = 0, countCorrect = 0, countAllQuestions = 0;
        Scanner in = new Scanner(System.in);

        Session context = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = context.createQuery("FROM Question");
        List<Question> questions = query.list(); // getting all questions from database
        countAllQuestions = questions.size(); // save the size, so as not to find out several times later

        for (Question question : questions) {
            System.out.println(question); // showing one question
            System.out.print("Enter your answer: ");

            int answer = Integer.parseInt(in.nextLine());
            List<QuestionItem> questionItems = question.getQuestionItems();
            // if the user's answer matches the saved one, the number of correct answers increases
            if (questionItems.get(answer - 1).isTrue()) countCorrect++;
        }

        rating = (100 * countCorrect) / countAllQuestions; // calculation of accrued percents, in proportion

        if (rating >= 95) {
            gradeOf12 = 12;
        } else if (rating >= 90) {
            gradeOf12 = 11;
        } else if (rating >= 85) {
            gradeOf12 = 10;
        } else if (rating >= 70) {
            gradeOf12 = 9;
        } else if (rating >= 60) {
            gradeOf12 = 8;
        } else if (rating >= 50) {
            gradeOf12 = 7;
        } else if (rating >= 40) {
            gradeOf12 = 6;
        } else if (rating >= 30) {
            gradeOf12 = 5;
        } else if (rating >= 20) {
            gradeOf12 = 4;
        } else if (rating >= 10) {
            gradeOf12 = 3;
        } else if (rating >= 5) {
            gradeOf12 = 2;
        } else gradeOf12 = 1;

        // Showing all got results
        System.out.println("All: " + countAllQuestions);
        System.out.println("12-point scale: " + gradeOf12);
        System.out.println("Correct answers: " + countCorrect + "/" + countAllQuestions);
        System.out.println("Wrong answers: " + (countAllQuestions - countCorrect));
        System.out.println("Rating: " + rating + " %");
        context.close();
    }

    private static void showRoles() {
        Session context = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = context.createQuery("FROM Role");
        List<Role> roles = query.list();
        for (Role role : roles) System.out.println(role);
        context.close();
    }

    private static void deleteQuestion(int id) {
        Question question = new Question();
        Session context = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = context.beginTransaction();
        question.setId(id);
        context.delete(question);
        transaction.commit();
        context.close();
    }

    private static void stop() {
        System.out.print(Color.GREEN);
        System.out.println("Done !");
        System.out.print(Color.RESET); // shows a message that the operation was successfully processed

        Scanner stopper = new Scanner(System.in);
        String tmp = stopper.nextLine(); // waiting until user click on some key
    }

    enum Color { // enum for beautiful colors in console
        //Color end string, color reset
        RESET("\033[0m"),

        // Regular Colors. Normal color, no bold, background color etc.
        BLACK("\033[0;30m"),    // BLACK
        RED("\033[0;31m"),      // RED
        GREEN("\033[0;32m"),    // GREEN
        YELLOW("\033[0;33m"),   // YELLOW
        BLUE("\033[0;34m"),     // BLUE
        MAGENTA("\033[0;35m"),  // MAGENTA
        CYAN("\033[0;36m"),     // CYAN
        WHITE("\033[0;37m");   // WHITE

        private final String code;

        Color(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }
}
