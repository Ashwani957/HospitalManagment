package HospitalManagmentSystem;

import java.sql.*;
import java.util.Scanner;
import java.sql.SQLException;

public class Patient {

    private Connection connection ;
    private Scanner scanner ;


//    Here we make a constructor
    public Patient(Connection connection  , Scanner scanner)
    {
        this.connection=connection;
        this.scanner=scanner;
    }

//    for input purpose

    public void addPatient()
    {
        System.out.print("Enter Patient name:");
        String name = scanner.next();
        System.out.println("Enter Patient age");
        int age = scanner.nextInt();
        System.out.println("Enter Patient Gender");
        String gender=scanner.next();
        try{
            String query = "Insert into patients(name, age, gender) values(? , ? , ? ) ";
            //Helps to execute the exception
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);
            int affectedRows=preparedStatement.executeUpdate();
            if(affectedRows>0)
            {
                System.out.println("Patient Added successfully ");
            }
            else {
                System.out.println("Failed to add patient");
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();

        }
    }


    //view  the patients

    public void viewPatients(){
        String query = "select * from patients";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("Patients");
            System.out.println("+------------+-----------------------------+--------------+------------+------------+");
            System.out.println("| Patient ID | Name                         | Age          | Gender                 |");
            System.out.println("+------------+-----------------------------+--------------+------------+------------+");
            while (resultSet.next())
            {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age=resultSet.getInt("age");
                String gender=resultSet.getString("gender");
                System.out.printf("|%-13s|%-25s|%-25s|%-30s|\n", id , name , age , gender);
                System.out.println("+------------+-----------------------------+--------------+------------+------------+");

            }

        }


        catch(SQLException e){
            e.printStackTrace();
        }
    }


    public boolean getPatientById(int id )
    {
        String query = "Select * from patients where id = ?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();

            if(resultSet.next())
            {
                return true;

            }
            else {
                return false ;
            }

        }
        catch (SQLException e){
e.printStackTrace();
        }
        return false;
    }
    }
