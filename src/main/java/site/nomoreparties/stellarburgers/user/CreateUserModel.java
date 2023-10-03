package site.nomoreparties.stellarburgers.user;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import io.restassured.response.ValidatableResponse;

import java.util.Locale;

public class CreateUserModel {
    private String email;
    private String password;
    private String name;

    public CreateUserModel(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public static String userRandom(){
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService());
        String emailRandom = fakeValuesService.bothify("????###@gmail.com");
        String passwordRandom = fakeValuesService.bothify("????###");

        Faker faker = new Faker();
        String nameRandom = faker.name().firstName();
        CreateUserModel createUserModel = new CreateUserModel(emailRandom, passwordRandom, nameRandom);
        UserSteps userSteps = new UserSteps();
        ValidatableResponse response = userSteps.createUser(createUserModel);
        String accessToken = response.extract().path("accessToken");
        return accessToken;
    }
}
