package HospitalManagmentSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagmentSystem {


    private static final  String url ="jdbc:mysql://127.0.0.1:3306/hospitals";
    private static final String username="root";
    private static final String password="@aA0852@";

    public static void main (String ...args)
    {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

        }
        catch(ClassNotFoundException e)

        {

            e.printStackTrace();
        }

        Scanner scanner=new Scanner(System.in);
        try{
            Connection connection= DriverManager.getConnection(url, username, password);
//            the patitent class constructor will take two value first is connection and second is scanner

            Patient patient=new Patient(connection,scanner);
            Doctor doctor=new Doctor(connection);
            while (true){
                System.out.println("HOSPITAL MANAGMENT SYSTEM");
                System.out.println("1.Add Patient");
                System.out.println("2. Add views");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointement");
                System.out.println("Exit");
                System.out.println("Enter your choice:");
                int choice = scanner.nextInt();
                switch (choice)
                {
                    case 1 :
//                        //Add patient
                    patient.addPatient();
                    break ;
                    case 2 :
                        //View Patient
                        patient.viewPatients();
                        break;


                    case 3:
                        //view Doctors
                        doctor.viewDoctors();
                        break ;

                    case 4 :
//                        Book Appointment
                        bookAppointment(patient, doctor, connection, scanner);
                        break ;


                    case 5:
//                        Exit


                    default :
                        System.out.println("Enter valid choice");

                }
            }


        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }


//    function for booking of appoinment

    public static void bookAppointment( Patient patient, Doctor doctor, Connection connection , Scanner scanner)
    {
//            System.out.println("4 Id");
            System.out.println("Enter patient Id");
            int patientId=scanner.nextInt();

            System.out.println("Enter Doctor Id");
            int doctorId=scanner.nextInt();
            System.out.println("Enter appoinment date (yyy-mmm-dd)");
            String appointmentDate=scanner.next();


            if (patient.getPatientById(patientId) && doctor.getDoctorById(doctorId))

            {

                if (checkDoctorAvailability(doctorId, appointmentDate , connection ))
                {

String appointmentQuery="Insert into appointments(patient_id, doctor_id, appointment_date) Values (?,?,?) ";
//        Here we execute the query

                    try{

                    PreparedStatement preparedStatement=connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1,patientId);
                    preparedStatement.setInt(2,doctorId);
                    preparedStatement.setString(3,appointmentDate);
                    int rowsAffected=preparedStatement.executeUpdate();
                    if(rowsAffected>0)
                    {
                            System.out.println("Appointment Book");
                    }
                    else {
                        System.out.println("Failed to book appointment ");
                    }
                    }
                    catch(SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
                 else {
                     System.out.println("Doctor not available on this date");
                }

            }
            else {
                System.out.println("Either doctor or patient does not exist");
            }
    }

//    check doctor availability on particular date

    public static  boolean checkDoctorAvailability(int doctorId, String appointmentDate,Connection connection )
    {

//        count(*) means it tell about how many rows we have
        String query ="Select Count (*)  from appointments where doctor_id = ? And appointment_date=?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement((query));
            preparedStatement.setInt(1,doctorId);
            preparedStatement.setString(2,appointmentDate);
            ResultSet resultset=preparedStatement.executeQuery();
            if(resultset.next())
            {
//                1 tell us the index that we take e
                int count=resultset.getInt(1);
                if(count==0)
                {
                    return true ;
                }
                else {
                    return false ;
                }
            }

        }
        catch(SQLException e){
                e.printStackTrace();

        }
        return false ;
    }

}
