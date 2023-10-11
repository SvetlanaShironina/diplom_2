package userTest;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.user.CreateUserModel;
import site.nomoreparties.stellarburgers.user.UserRandom;
import site.nomoreparties.stellarburgers.user.UserResult;
import site.nomoreparties.stellarburgers.user.UserSteps;

public class RegisterUserTest {
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
    @DisplayName("Проверяем создание уникального пользователя")
    @Description("Проверяем, что уникальный пользователь создается")
    public void createUniqueUser() {
        ValidatableResponse createUniqueUser = userSteps.createUser(createUserModel);
        userResult.createUserOk(createUniqueUser);

        String accessToken = createUniqueUser.extract().path("accessToken");
        response = userSteps.deletedUser(StringUtils.substringAfter(accessToken, ""));
    }

    @Test
    @DisplayName("Проверяем повторное создание пользователя с теми же данными")
    @Description("Проверяем, что нельзя зарегистрироваться, если пользователь уже существует")
    public void createUserExistingData() {
        ValidatableResponse createUser1 = userSteps.createUser(createUserModel);
        ValidatableResponse createUser2 = userSteps.createUser(createUserModel);
        userResult.createUserExistingData(createUser2);

        String accessToken = createUser1.extract().path("accessToken");
        response = userSteps.deletedUser(StringUtils.substringAfter(accessToken, ""));
    }

    @Test
    @DisplayName("Проверяем создание пользователя при незаполненном обязательном поле email")
    @Description("Проверяем, что нельзя зарегистрироваться, если пользователь не указал email")
    public void createUserWithoutEmailError() {
        createUserModel.setEmail(null);
        ValidatableResponse createUser = userSteps.createUser(createUserModel);
        userResult.createUserError(createUser);
    }

    @Test
    @DisplayName("Проверяем создание пользователя при незаполненном обязательном поле password")
    @Description("Проверяем, что нельзя зарегистрироваться, если пользователь не указал password")
    public void createUserWithoutPasswordError() {
        createUserModel.setPassword(null);
        ValidatableResponse createUser = userSteps.createUser(createUserModel);
        userResult.createUserError(createUser);
    }

    @Test
    @DisplayName("Проверяем создание пользователя при незаполненном обязательном поле name")
    @Description("Проверяем, что нельзя зарегистрироваться, если пользователь не указал name")
    public void createUserWithoutNameError() {
        createUserModel.setName(null);
        ValidatableResponse createUser = userSteps.createUser(createUserModel);
        userResult.createUserError(createUser);
    }
}
