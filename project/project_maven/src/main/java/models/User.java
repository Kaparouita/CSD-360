package models;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String driverLicense;
    private String creditCardNumber;
    private int[]  rentIds;
    

    public User(String username, String password, String email, String firstName, String lastName, String address, String phoneNumber, String driverLicense, String creditCardNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.driverLicense = driverLicense;
        this.creditCardNumber = creditCardNumber;

    }

    public void addRentId(int rentId){
        int[] temp = new int[rentIds.length+1];
        for(int i=0;i<rentIds.length;i++){
            temp[i]=rentIds[i];
        }
        temp[rentIds.length]=rentId;
        rentIds=temp;
    }

    public int[] getRentIds() {
        return rentIds;
    }

    public void removeRentId(int id){
        int[] temp = new int[rentIds.length-1];
        int j=0;
        for(int i=0;i<rentIds.length;i++){
            if(rentIds[i]!=id){
                temp[j]=rentIds[i];
                j++;
            }
        }
        rentIds=temp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public User(String driverLicense){
        this.driverLicense = driverLicense;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }


}

    
