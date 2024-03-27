package site.nomoreparties.stellarburgers.user;

public class LoginUserModel {
    private String email;
    private String password;

    public LoginUserModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static LoginUserModel from(CreateUserModel createUserModel){
        return new LoginUserModel(createUserModel.getEmail(), createUserModel.getPassword());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void getPassword(String password) {
        this.password = password;
    }
}

