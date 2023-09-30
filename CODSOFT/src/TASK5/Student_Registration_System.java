package TASK5;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private List<Student> registeredStudents;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.registeredStudents = new ArrayList<>();
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public List<Student> getRegisteredStudents() {
        return registeredStudents;
    }

    public void registerStudent(Student student) {
        if (registeredStudents.size() < capacity) {
            registeredStudents.add(student);
        } else {
            System.out.println("Course is full. Cannot register.");
        }
    }

    public void dropStudent(Student student) {
        registeredStudents.remove(student);
    }

    @Override
    public String toString() {
        return "Course Code: " + courseCode + "\nTitle: " + title + "\nDescription: " + description +
               "\nCapacity: " + capacity + "\nSchedule: " + schedule + "\nAvailable Slots: " +
               (capacity - registeredStudents.size()) + "\n";
    }
}

class Student {
    private String studentID;
    private String name;
    private List<Course> registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(Course course) {
        if (registeredCourses.size() < 3) {
            registeredCourses.add(course);
            course.registerStudent(this);
        } else {
            System.out.println("You have already registered for 3 courses. Cannot register for more.");
        }
    }

    public void dropCourse(Course course) {
        registeredCourses.remove(course);
        course.dropStudent(this);
    }
}

public class Student_Registration_System {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Course> courseDatabase = new ArrayList<>();
        List<Student> studentDatabase = new ArrayList<>();

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Add a course");
            System.out.println("2. Add a student");
            System.out.println("3. Display available courses");
            System.out.println("4. Register a student for a course");
            System.out.println("5. Drop a course for a student");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter course code: ");
                    String courseCode = scanner.nextLine();
                    System.out.print("Enter course title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter course description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter course capacity: ");
                    int capacity = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter course schedule: ");
                    String schedule = scanner.nextLine();
                    Course course = new Course(courseCode, title, description, capacity, schedule);
                    courseDatabase.add(course);
                    System.out.println("Course added successfully!");
                    break;
                case 2:
                    System.out.print("Enter student ID: ");
                    String studentID = scanner.nextLine();
                    System.out.print("Enter student name: ");
                    String studentName = scanner.nextLine();
                    Student student = new Student(studentID, studentName);
                    studentDatabase.add(student);
                    System.out.println("Student added successfully!");
                    break;
                case 3:
                    System.out.println("\nAvailable Courses:");
                    for (Course c : courseDatabase) {
                        System.out.println(c.toString());
                    }
                    break;
                case 4:
                    System.out.print("Enter student ID: ");
                    String studentIDToRegister = scanner.nextLine();
                    System.out.print("Enter course code to register: ");
                    String courseCodeToRegister = scanner.nextLine();

                    Student studentToRegister = null;
                    for (Student s : studentDatabase) {
                        if (s.getStudentID().equals(studentIDToRegister)) {
                            studentToRegister = s;
                            break;
                        }
                    }

                    Course courseToRegister = null;
                    for (Course c : courseDatabase) {
                        if (c.getCourseCode().equals(courseCodeToRegister)) {
                            courseToRegister = c;
                            break;
                        }
                    }

                    if (studentToRegister != null && courseToRegister != null) {
                        studentToRegister.registerCourse(courseToRegister);
                        System.out.println("Registration successful!");
                    } else {
                        System.out.println("Student or course not found.");
                    }
                    break;
                case 5:
                    System.out.print("Enter student ID: ");
                    String studentIDToDrop = scanner.nextLine();
                    System.out.print("Enter course code to drop: ");
                    String courseCodeToDrop = scanner.nextLine();

                    Student studentToDrop = null;
                    for (Student s : studentDatabase) {
                        if (s.getStudentID().equals(studentIDToDrop)) {
                            studentToDrop = s;
                            break;
                        }
                    }

                    Course courseToDrop = null;
                    for (Course c : courseDatabase) {
                        if (c.getCourseCode().equals(courseCodeToDrop)) {
                            courseToDrop = c;
                            break;
                        }
                    }

                    if (studentToDrop != null && courseToDrop != null) {
                        studentToDrop.dropCourse(courseToDrop);
                        System.out.println("Course dropped successfully!");
                    } else {
                        System.out.println("Student or course not found.");
                    }
                    break;
                case 6:
                    System.out.println("Exiting the program.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}
