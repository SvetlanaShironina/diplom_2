package userTest;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.user.*;

import java.util.Locale;

import static org.junit.Assert.assertNotEquals;

public class EditeUserDataTest {
private UserSteps userSteps;
    private UserResult userResult;
    private ValidatableResponse response;
    private CreateUserModel createUserModel;

    @Before
    @Step("Создание тестовых данных юзера")
    public void setUp() {
        userSteps = new UserSteps();
        userResult = new UserResult();
        createUserModel = UserRandom.getUserRandom();
    }

    @Test
    @DisplayName("Проверяем изменение данных пользователем без авторизации (Email)")
    @Description("Изменение данных пользователя: без авторизации, должно выдавать ошибку")
    public void userWithoutAuthorizationUpdateEmail() {
        ValidatableResponse createUser = userSteps.createUser(createUserModel);
        String accessToken = createUser.extract().path("accessToken");
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService());
        createUserModel.setEmail(fakeValuesService.bothify("????###@gmail.com"));

        ValidatableResponse userWithoutAuthorizationUpdateEmail = userSteps.userWithoutAuthorizationUpdateData(createUserModel);
        userResult.updateUserWithoutAuthorization(userWithoutAuthorizationUpdateEmail);

        response = userSteps.deletedUser(StringUtils.substringAfter(accessToken, ""));
    }

    @Test
    @DisplayName("Проверяем изменение данных пользователем без авторизации (Password)")
    @Description("Изменение данных пользователя: без авторизации, должно выдавать ошибку")
    public void userWithoutAuthorizationUpdatePassword() {
        ValidatableResponse createUser = userSteps.createUser(createUserModel);
        String accessToken = createUser.extract().path("accessToken");
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService());
        createUserModel.setPassword(fakeValuesService.bothify("????###"));

        ValidatableResponse userWithoutAuthorizationUpdatePassword = userSteps.userWithoutAuthorizationUpdateData(createUserModel);
        userResult.updateUserWithoutAuthorization(userWithoutAuthorizationUpdatePassword);

        response = userSteps.deletedUser(StringUtils.substringAfter(accessToken, ""));
    }

    @Test
    @DisplayName("Проверяем изменение данных пользователем без авторизации (Name)")
    @Description("Изменение данных пользователя: без авторизации, должно выдавать ошибку")
    public void userWithoutAuthorizationUpdateName() {
        ValidatableResponse createUser = userSteps.createUser(createUserModel);
        String accessToken = createUser.extract().path("accessToken");
        Faker faker = new Faker();
        createUserModel.setName(faker.name().firstName());

        ValidatableResponse userWithoutAuthorizationUpdateName = userSteps.userWithoutAuthorizationUpdateData(createUserModel);
        userResult.updateUserWithoutAuthorization(userWithoutAuthorizationUpdateName);

        response = userSteps.deletedUser(StringUtils.substringAfter(accessToken, ""));
    }

    @Test
    @DisplayName("Проверяем изменение данных пользователем с авторизацией (Email)")
    @Description("Изменение email пользователя: с авторизацией")
    public void userAuthorizationUpdateEmail() {
        ValidatableResponse createUser = userSteps.createUser(createUserModel);
        String accessToken = createUser.extract().path("accessToken");
        String emailExpected = createUser.extract().path("user.email");
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService());
        createUserModel.setEmail(fakeValuesService.bothify("????###@gmail.com"));
        ValidatableResponse userAuthorizationUpdateEmail = userSteps.userAuthorizationUpdateData(createUserModel, accessToken);
        String emailActual = userAuthorizationUpdateEmail.extract().path("user.email");

        userResult.updateUserAuthorization(userAuthorizationUpdateEmail);
        assertNotEquals("Email не изменен", emailExpected, emailActual);

        response = userSteps.deletedUser(StringUtils.substringAfter(accessToken, ""));
    }

    @Test
    @DisplayName("Проверяем изменение данных пользователем с авторизацией (Password)")
    @Description("Изменение passwor пользователя: с авторизацией")
    public void userAuthorizationUpdatePassword() {
        ValidatableResponse createUser = userSteps.createUser(createUserModel);
        String accessToken = createUser.extract().path("accessToken");
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService());
        createUserModel.setPassword(fakeValuesService.bothify("????###"));

        ValidatableResponse userAuthorizationUpdatePassword = userSteps.userAuthorizationUpdateData(createUserModel, accessToken);
        userResult.updateUserAuthorization(userAuthorizationUpdatePassword);

        response = userSteps.deletedUser(StringUtils.substringAfter(accessToken, ""));
    }

    @Test
    @DisplayName("Проверяем изменение данных пользователем с авторизацией (Email)")
    @Description("Изменение email пользователя: с авторизацией")
    public void userAuthorizationUpdateName() {
        ValidatableResponse createUser = userSteps.createUser(createUserModel);
        String accessToken = createUser.extract().path("accessToken");
        String nameExpected = createUser.extract().path("user.name");
        Faker faker = new Faker();
        createUserModel.setName(faker.name().firstName());
        ValidatableResponse userAuthorizationUpdateName = userSteps.userAuthorizationUpdateData(createUserModel, accessToken);
        String nameActual = userAuthorizationUpdateName.extract().path("user.name");

        userResult.updateUserAuthorization(userAuthorizationUpdateName);
        assertNotEquals("Name не изменен", nameExpected, nameActual);

        response = userSteps.deletedUser(StringUtils.substringAfter(accessToken, ""));
    }
}
