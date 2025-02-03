package HospitalManagmentSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Doctor {


    private Connection connection ;



    //    Here we make a constructor
    public Doctor(Connection connection  )
    {
        this.connection=connection;

    }




    //view  the patients

    public void viewDoctors(){
        String query = "select * from Doctors";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("Patients");
            System.out.println("+------------+-----------------------------+--------------+");
            System.out.println("| Doctor ID | Name                    |  Specilization|");
            System.out.println("+------------+-----------------------------+--------------+");
            System.out.println("above doctor loop");
            while (resultSet.next())
            {
                System.out.println("Inside doctors loop");

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                String specilization=resultSet.getString("specialization");
                System.out.printf("|%-13s|%-20s|%-15s|\n",id, name , specilization);
//

           ;
                System.out.println("+------------+-----------------------------+--------------+");

            }

        }


        catch(SQLException e){
            e.printStackTrace();
        }
    }


    public boolean getDoctorById(int id )
    {
        String query = "Select * from Doctors where id = ?";
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
            System.out.println("Error in Doctro java ");
        }
        return false;
    }
}
