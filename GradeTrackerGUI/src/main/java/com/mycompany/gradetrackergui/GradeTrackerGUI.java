/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.gradetrackergui;

/**
 *
 * @author zohas
 */
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Student {
    String name;
    ArrayList<Integer> grades = new ArrayList<>();

    Student(String name) {
        this.name = name;
    }

    void addGrade(int grade) {
        grades.add(grade);
    }

    double getAverage() {
        int sum = 0;
        for (int g : grades) sum += g;
        return grades.isEmpty() ? 0 : (double) sum / grades.size();
    }

    int getHighest() {
        int max = Integer.MIN_VALUE;
        for (int g : grades) max = Math.max(max, g);
        return grades.isEmpty() ? 0 : max;
    }

    int getLowest() {
        int min = Integer.MAX_VALUE;
        for (int g : grades) min = Math.min(min, g);
        return grades.isEmpty() ? 0 : min;
    }
}

public class GradeTrackerGUI extends JFrame {
    private JTextField nameField, gradeField;
    private JTextArea reportArea;
    private ArrayList<Student> students = new ArrayList<>();
    private Student currentStudent;

    public GradeTrackerGUI() {
        setTitle("Student Grade Tracker");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel input = new JPanel(new GridLayout(3, 2, 5, 5));
        input.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        input.add(nameField);

        input.add(new JLabel("Enter Grade:"));
        gradeField = new JTextField();
        input.add(gradeField);

        JButton addStudentBtn = new JButton("Add Student");
        JButton addGradeBtn = new JButton("Add Grade");
        input.add(addStudentBtn);
        input.add(addGradeBtn);

        add(input, BorderLayout.NORTH);

        // Report Area
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        add(new JScrollPane(reportArea), BorderLayout.CENTER);

        JButton reportBtn = new JButton("Show Report");
        add(reportBtn, BorderLayout.SOUTH);

        // Add Student Action
        addStudentBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Student name cannot be empty!");
                return;
            }
            currentStudent = new Student(name);
            students.add(currentStudent);
            reportArea.append("Added student: " + name + "\n");
            nameField.setText("");
        });

        // Add Grade Action
        addGradeBtn.addActionListener(e -> {
            if (currentStudent == null) {
                JOptionPane.showMessageDialog(this, "Add a student first!");
                return;
            }
            String gradeText = gradeField.getText().trim();
            if (gradeText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Grade cannot be empty!");
                return;
            }
            try {
                int grade = Integer.parseInt(gradeText);
                if (grade < 0 || grade > 100) {
                    JOptionPane.showMessageDialog(this, "Grade must be between 0 and 100!");
                    return;
                }
                currentStudent.addGrade(grade);
                reportArea.append("Added grade " + grade + " for " + currentStudent.name + "\n");
                gradeField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid number for grade!");
            }
        });

        // Show Report Action
        reportBtn.addActionListener(e -> {
            reportArea.setText("===== Summary Report =====\n");
            for (Student s : students) {
                reportArea.append("\nStudent: " + s.name + "\n");
                reportArea.append("Grades: " + s.grades + "\n");
                reportArea.append(String.format("Average: %.2f\n", s.getAverage()));
                reportArea.append("Highest: " + s.getHighest() + "\n");
                reportArea.append("Lowest: " + s.getLowest() + "\n");
                reportArea.append("----------------------------\n");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GradeTrackerGUI().setVisible(true));
    }
}

