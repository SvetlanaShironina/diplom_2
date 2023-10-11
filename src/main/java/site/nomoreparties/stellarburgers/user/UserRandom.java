package site.nomoreparties.stellarburgers.user;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import io.qameta.allure.Allure;
import io.restassured.response.ValidatableResponse;

import java.util.Locale;

public class UserRandom {

    public static CreateUserModel getUserRandom(){
        Faker faker = new Faker();
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService());
        String email = fakeValuesService.bothify("????###@gmail.com");
        String password = fakeValuesService.bothify("?.??##");
        String name = faker.name().firstName();

        Allure.addAttachment("Email : ", email);
        Allure.addAttachment("Password : ", password);
        Allure.addAttachment("Name : ", name);

        return new CreateUserModel(email,password,name);
    }

    public static String getUserAccessToken(){
        CreateUserModel createUserModel = UserRandom.getUserRandom();
        UserSteps userSteps = new UserSteps();
        ValidatableResponse response = userSteps.createUser(createUserModel);
        String accessToken = response.extract().path("accessToken");
        return accessToken;
    }
}