package com.jspider.prepared_crud_operation.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;
import com.jspider.prepared_crud_operation.connection.StudentConnection;

public class StudentController {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Connection connection = StudentConnection.getStudentConnection();
        
        while (true) {
        	System.out.println();
            System.out.println("1: INSERT \n2: DISPLAY THE TABLE \n3: DISPLAY STUDENT BY ID \n4: UPDATE STUDENT DETAIL \n5: DELETE RECORD \n6: EXIT");
            System.out.println("Enter Your Choice: ");
            int option = sc.nextInt();
            
            if (option == 6) {
                System.out.println("Exiting the application...");
                break;
            }
            
            switch (option) {
                case 1: {
                    String insertStudentQuery = "INSERT INTO Student(id, name, email, address, dob) VALUES(?,?,?,?,?)";
                    try {
                        PreparedStatement ps = connection.prepareStatement(insertStudentQuery);

                        System.out.println("Enter The Student Id: ");
                        int id = sc.nextInt();
                        sc.nextLine(); // Consume newline
                        System.out.println("Enter The Student Name: ");
                        String name = sc.nextLine();
                        System.out.println("Enter The Student Email: ");
                        String email = sc.next();
                        System.out.println("Enter The Student Address: ");
                        sc.nextLine(); // Consume newline
                        String address = sc.nextLine();
                        System.out.println("Enter The Student Dob (YYYY-MM-DD): ");
                        String dob = sc.next();
                        LocalDate date = LocalDate.parse(dob);

                        ps.setInt(1, id);
                        ps.setString(2, name);
                        ps.setString(3, email);
                        ps.setString(4, address);
                        ps.setObject(5, date);

                        int a = ps.executeUpdate();
                        System.out.println(a != 0 ? "Data Stored Successfully" : "Data Not Stored");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case 2: {
                    String query = "SELECT * FROM student";
                    try {
                        PreparedStatement ps = connection.prepareStatement(query);
                        ResultSet rset = ps.executeQuery();
                        while (rset.next()) {
                            int id = rset.getInt("id");
                            String name = rset.getString("name");
                            String email = rset.getString("email");
                            String address = rset.getString("address");
                            LocalDate dob = rset.getDate("dob").toLocalDate();
                            System.out.println("----------------- Student Details ----------------");
                            System.out.println("ID: " + id + " | Name: " + name + " | Email: " + email + " | Address: " + address + " | DOB: " + dob);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case 3: {
                    System.out.println("Enter The Student ID: ");
                    int stid = sc.nextInt();
                    String query = "SELECT * FROM student WHERE id=?";
                    try {
                        PreparedStatement ps = connection.prepareStatement(query);
                        ps.setInt(1, stid);
                        ResultSet rset = ps.executeQuery();
                        if (rset.next()) {
                            int id = rset.getInt("id");
                            String name = rset.getString("name");
                            String email = rset.getString("email");
                            String address = rset.getString("address");
                            LocalDate dob = rset.getDate("dob").toLocalDate();
                            System.out.println("----------------- Student Details ----------------");
                            System.out.println("ID: " + id + " | Name: " + name + " | Email: " + email + " | Address: " + address + " | DOB: " + dob);
                        } else {
                            System.out.println("ID not found.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case 4: {
                    System.out.println("Enter the Student ID to update: ");
                    int stid = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    System.out.println("Enter the field to update (name, email, address, dob): ");
                    String field = sc.next();
                    sc.nextLine(); // Consume newline
                    System.out.println("Enter the new value: ");
                    String newValue = sc.nextLine();
                    String query = "UPDATE student SET " + field + "=? WHERE id=?";
                    try {
                        PreparedStatement pstmt = connection.prepareStatement(query);
                        pstmt.setString(1, newValue);
                        pstmt.setInt(2, stid);
                        int row = pstmt.executeUpdate();
                        System.out.println(row > 0 ? "Update Successful" : "Update Failed");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case 5: {
                    System.out.println("Enter the Student ID to delete: ");
                    int id = sc.nextInt();
                    String deleteQuery = "DELETE FROM student WHERE id = ?";
                    try {
                        PreparedStatement pstmt = connection.prepareStatement(deleteQuery);
                        pstmt.setInt(1, id);
                        int rowsAffected = pstmt.executeUpdate();
                        System.out.println(rowsAffected > 0 ? "Deletion Successful" : "Deletion Failed");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                break;
                default:
                    System.out.println("Invalid Choice. Please try again.");
            }
        }
        try {
            connection.close();
            sc.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
