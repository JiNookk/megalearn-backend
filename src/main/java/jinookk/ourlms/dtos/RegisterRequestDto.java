package jinookk.ourlms.dtos;

public class RegisterRequestDto {
    private String userName;
    private String password;
    private String name;
    private String phoneNumber;

    public RegisterRequestDto() {
    }

    public RegisterRequestDto(String userName, String password, String name, String phoneNumber) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
