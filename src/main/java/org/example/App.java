package org.example;
import java.sql.*;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        try {
            String URL = "jdbc:mysql://localhost:3306/studentdb";
            String USER = "student_user";
            String PASS = "StudentPass123";
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            Scanner sc = new Scanner(System.in);
            int choice = 0;

            while (choice != 5) {
                System.out.println(" ");
                System.out.println("Student Entry System");
                System.out.println("1. Add Student");
                System.out.println("2. View All Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                choice = sc.nextInt();
                sc.nextLine(); // consume newline

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
                        break;
                    default:
                        System.out.println("Invalid choice!");
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
            System.out.print("Enter age: ");
            int age = sc.nextInt();
            sc.nextLine(); // consume newline
            System.out.print("Enter course: ");
            String course = sc.nextLine();

            String sql = "INSERT INTO students (name, age, course) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, course);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Student added successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewStudents(Connection conn) {
        try {
            String sql = "SELECT * FROM students";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            System.out.println("\nID\t\tName\t\tAge\t\tCourse");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" + "\t"
                        + rs.getString("name") + "\t" + "\t"
                        + rs.getInt("age") + "\t" + "\t"
                        + rs.getString("course"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateStudent(Scanner sc, Connection conn) {
        try {
            System.out.print("Enter student ID to update: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter new name: ");
            String name = sc.nextLine();
            System.out.print("Enter new age: ");
            int age = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter new course: ");
            String course = sc.nextLine();

            String sql = "UPDATE students SET name=?, age=?, course=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, course);
            ps.setInt(4, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("Student ID not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteStudent(Scanner sc, Connection conn) {
        try {
            System.out.print("Enter student ID to delete: ");
            int id = sc.nextInt();
            sc.nextLine();

            String sql = "DELETE FROM students WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Student ID not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
