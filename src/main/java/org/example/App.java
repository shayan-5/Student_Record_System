package org.example;
import java.sql.*;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        try {
            String URL = "jdbc:mysql://localhost:3306/studentdb";
            String USER = "your_username";
            String PASS = "your_password";
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            Scanner sc = new Scanner(System.in);
            int choice = 0;
            System.out.println();

//            IMPORTANT NOTE :-
//            #Please enter valid names of students or courses whenever you are performing query operations!
//            #If you dont enter valid data in the input, the menu will keep looping back!
//            #Also students of ages between 18 to 25 are only allowed to register.
//            #Enter valid and authorised courses name!
//            #Please follow above rules!


            while (choice != 5) {
                System.out.println(" ");
                System.out.println("Student Entry System");
                System.out.println();
                System.out.println("1. Add Student");
                System.out.println();
                System.out.println("2. View All Students");
                System.out.println();
                System.out.println("3. Update Student");
                System.out.println();
                System.out.println("4. Delete Student");
                System.out.println();
                System.out.println("5. Exit");
                System.out.println();
                System.out.print("Enter choice: ");
                if (sc.hasNextInt()) {
                    choice = sc.nextInt();
                    sc.nextLine(); // consume newline
                } else {
                    System.out.println();
                    System.out.println("\nError: Invalid choice! Please enter a number between 1 and 5.");
                    sc.nextLine(); // clear the invalid text from scanner memory
                    continue;      // forcefully skip to the next loop iteration safely
                }

                switch (choice) {
                    case 1:
                        addStudent(sc, conn);
                        break;
                    case 2:
                        viewStudents(conn);
                        break;
                    case 3:
                        updateStudent(sc, conn);
                        break;
                    case 4:
                        deleteStudent(sc, conn);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice!Please enter a number between 1 and 5.");
                        break;
                }
            }

            sc.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addStudent(Scanner sc, Connection conn) {
        try {
            System.out.print("Enter name: ");
            String name = sc.nextLine();
            //INPUT VALIDATION
            if (!name.matches("^[a-zA-Z\\s]+$")) {
                System.out.println();
                System.out.println("\nPlease enter valid Name and it must strictly contain only alphabets and spaces!");
                return; // Safely exits the function back to the main loop menu
            }

            System.out.print("Enter age: ");
            int age;
            if(sc.hasNextInt()) {
                age = sc.nextInt();
                if(age<18 || age>25) {
                    System.out.println();
                    System.out.println("Students of age must be between 18 and 25!");
                    sc.nextLine();
                    return;
                }
            }
            else{
                System.out.println();
                System.out.println("Enter valid age!");
                sc.nextLine();
                return;
            }
            sc.nextLine(); // consume newline
            System.out.print("Enter course: ");
            String course = sc.nextLine();
            if (!course.matches("^[a-zA-Z\\s]+$")) {
                System.out.println();
                System.out.println("\nValidation Error: Names must strictly contain only alphabets and spaces!");
                return; // Safely exits the function back to the main loop menu
            }

            String sql = "INSERT INTO students (name, age, course) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, course);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();

                if (generatedKeys.next()) {
                    int studentId =generatedKeys.getInt(1);
                    System.out.println();
                    System.out.println("--------------------------------");
                    System.out.println("Student added successfully!");
                    System.out.println("Your Student ID is : "+ studentId);
                    System.out.println("--------------------------------");
                }
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewStudents(Connection conn) {
        try {
            String sql = "SELECT * FROM students";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            System.out.println();
            System.out.println( "------------------------------------------------------------");
            System.out.printf(
                    "%-10s %-20s %-10s %-20s%n",
                    "ID",
                    "NAME",
                    "AGE",
                    "COURSE"
            );

            System.out.println( "------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf(
                        "%-10d %-20s %-10d %-20s%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("course")
                );
            }
            System.out.println( "------------------------------------------------------------");

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateStudent(Scanner sc, Connection conn) {
        try {
            System.out.print("Enter student ID to update: ");
            int id = 0;

            if(sc.hasNextInt()) {
                id = sc.nextInt();
            }
            else {
                System.out.println();
                System.out.println("Enter valid student ID!");
                sc.nextLine();
                return;
            }

            String v="Select name from students where id=?";
            PreparedStatement p = conn.prepareStatement(v);
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            if (!rs.next()) {
                System.out.println();
                System.out.println("\nStudent ID doesn't exists in table!");
                return;
            }
            sc.nextLine();
            System.out.print("Enter new name: ");
            String name = sc.nextLine();
            if (!name.matches("^[a-zA-Z\\s]+$")) {
                System.out.println();
                System.out.println("\nValidation Error: Names must strictly contain only alphabets and spaces!");
                return; // Safely exits the function back to the main loop menu
            }
            System.out.print("Enter new age: ");
            int age;
            if(sc.hasNextInt()) {
                age = sc.nextInt();
                if(age<18 || age>25) {
                    System.out.println();
                    System.out.println("Students of age must be between 18 and 25!");
                    sc.nextLine();
                    return;
                }
            }
            else{
                System.out.println();
                System.out.println("Enter valid age!");
                sc.nextLine();
                return;
            }
            sc.nextLine();
            System.out.print("Enter new course: ");
            String course = sc.nextLine();
            if (!course.matches("^[a-zA-Z\\s]+$")) {
                System.out.println();
                System.out.println("\nValidation Error: Names must strictly contain only alphabets and spaces!");
                return; // Safely exits the function back to the main loop menu
            }

            String sql = "UPDATE students SET name=?, age=?, course=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, course);
            ps.setInt(4, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println();
                System.out.println("Student updated successfully!");
            } else {
                System.out.println();
                System.out.println("Student ID not found.");
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteStudent(Scanner sc, Connection conn) {
        try {
            System.out.print("Enter student ID to delete: ");
            int id=0 ;
            if(sc.hasNextInt()) {
                id = sc.nextInt();
            }
            else{
                System.out.println();
                System.out.println("Enter valid id!");
                sc.nextLine();
                return;
            }
            String v="Select * from students where id=? ";
            PreparedStatement p = conn.prepareStatement(v);
            p.setInt(1,id);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                String sql = "DELETE FROM students WHERE id=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    System.out.println();
                    System.out.println("Student deleted successfully!");
                }
            }
            else{
                System.out.println();
                System.out.println("Student ID not found.");
                return;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
