
package Task03;

import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

class Student {

    private final String regNo;
    private final String name;

    Student(String regNo, String name) {
        this.regNo = regNo;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;

        Student student = (Student) o;
        return Objects.equals(regNo, student.regNo);
    }

    // TODO 1: Uncomment this method after the first execution.

    /*
    @Override
    public int hashCode() {
        return Objects.hash(regNo);
    }
    */

    @Override
    public String toString() {
        return regNo + " - " + name;
    }
}

// Class to demonstrate changing a hashCode field

class MutableStudent {

    String regNo;

    MutableStudent(String regNo) {
        this.regNo = regNo;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MutableStudent)) return false;

        MutableStudent other = (MutableStudent) o;
        return Objects.equals(regNo, other.regNo);
    }

  @Override
public int hashCode() {
    return Objects.hash(regNo);
}
}

public class StudentHashSetDemo {

    public static void main(String[] args) {

        System.out.println("HASHCODE AND EQUALS DEMONSTRATION");
        System.out.println("----------------------------------");

        // Test 1: Duplicate student registrations

        Set<Student> students = new HashSet<>();

        students.add(new Student("192425383", "Sameer"));
        students.add(new Student("192425383", "Sameer"));
        students.add(new Student("192425384", "Rahul"));

        System.out.println("\nTest 1: Duplicate Registration Numbers");
        System.out.println("Unique students: " + students.size());

        // Test 2: Different registration numbers

        Set<Student> test2 = new HashSet<>();

        test2.add(new Student("192425383", "Sameer"));
        test2.add(new Student("192425384", "Rahul"));
        test2.add(new Student("192425385", "Arun"));

        System.out.println("\nTest 2: Different Students");
        System.out.println("Unique students: " + test2.size());

        // Test 3: Adding the same object twice

        Set<Student> test3 = new HashSet<>();

        Student s = new Student("192425386", "Kiran");

        test3.add(s);
        test3.add(s);

        System.out.println("\nTest 3: Same Object Added Twice");
        System.out.println("Unique students: " + test3.size());

        // Test 4: Modifying a field used in hashCode

        Set<MutableStudent> mutableSet = new HashSet<>();

        MutableStudent ms = new MutableStudent("STU101");

        mutableSet.add(ms);

        System.out.println("\nTest 4: Modifying HashCode Field");
        System.out.println("Before modification: "
                + mutableSet.contains(ms));

        ms.regNo = "STU202";

        System.out.println("After modification: "
                + mutableSet.contains(ms));

        System.out.println("Set size: " + mutableSet.size());

        System.out.println("\nProgram completed successfully.");
    }
}
