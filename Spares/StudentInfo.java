import java.time.LocalDate;

public class Student { 
    private double dateOfBirth;  
    private String idNumber; 
    private String address;  
    private String phoneNumber; 
    private String email; 
    
    protected Student(){}
    protected Student(double dateOfBirth, String idNumber, String address, String phoneNumber, String email) {
        this.dateOfBirth = dateOfBirth;
        this.idNumber = idNumber;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
  
    //dateOfBirth
    public void setDateOfBirth() {
        this.dateOfBirth = dateOfBirth;
    }
    public double getDateOfBirth() {
        return dateOfBirth;
    }
    //idNumber
    public void setIdNumber() {
        this.idNumber = idNumber;
    }
    public String getIdNumber() {
        return idNumber;
    }
    //address
    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return address;
    }
    //phoneNumber
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    //email
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    //Returning visually the inputted details
    public String toString() {
        String studentInfo = "Date of Birth: " + dateOfBirth + "Student ID Number: " + idNumber +  "Address: " 
                              + address + "Phone Number: " + phoneNumber + "Email: "+ email;
        return studentInfo;
    }
}

