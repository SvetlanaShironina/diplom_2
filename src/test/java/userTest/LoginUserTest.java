package userTest;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.user.*;

public class LoginUserTest {
    private UserSteps userSteps;
    private UserResult userResult;
    private ValidatableResponse response;
    private CreateUserModel createUserModel;
    private LoginUserModel loginUserModel;

    @Before
    @Step("Создание тестовых данных юзера")
    public void setUp() {
        userSteps = new UserSteps();
        userResult = new UserResult();
        createUserModel = UserRandom.getUserRandom();
    }

    @Test
    @DisplayName("Проверяем вход под существующим пользователем")
    @Description("Проверяем, что существующий пользователь может залогиниться")
    public void loginUser() {
        userSteps.createUser(createUserModel);
        loginUserModel = LoginUserModel.from(createUserModel);

        ValidatableResponse loginUser = userSteps.loginUser(loginUserModel);
        userResult.loginUserOk(loginUser);

        String accessToken = loginUser.extract().path("accessToken");
        response = userSteps.deletedUser(StringUtils.substringAfter(accessToken, ""));
    }

    @Test
    @DisplayName("Проверяем вход с неверным логином (Email)")
    @Description("Проверяем, что пользователь не может залогиниться с неверным логином (Email)")
    public void loginUserIncorrectEmail() {
        ValidatableResponse createUser = userSteps.createUser(createUserModel);
        loginUserModel = LoginUserModel.from(createUserModel);
        loginUserModel.setEmail("1");

        ValidatableResponse loginUser = userSteps.loginUser(loginUserModel);
        userResult.loginUserIncorrectData(loginUser);

        String accessToken = createUser.extract().path("accessToken");
        response = userSteps.deletedUser(StringUtils.substringAfter(accessToken, ""));
    }

    @Test
    @DisplayName("Проверяем вход с неверным паролем")
    @Description("Проверяем, что пользователь не может залогиниться с неверным паролем")
    public void loginUserIncorrectPassword() {
        ValidatableResponse createUser = userSteps.createUser(createUserModel);
        loginUserModel = LoginUserModel.from(createUserModel);
        loginUserModel.setPassword("1");

        ValidatableResponse loginUser = userSteps.loginUser(loginUserModel);
        userResult.loginUserIncorrectData(loginUser);

        String accessToken = createUser.extract().path("accessToken");
        response = userSteps.deletedUser(StringUtils.substringAfter(accessToken, ""));
    }

    @Test
    @DisplayName("Проверяем вход с пустыми данными")
    @Description("Проверяем, что пользователь не может залогиниться не заполняя обязательные поля")
    public void loginUserWithoutData() {
        ValidatableResponse createUser = userSteps.createUser(createUserModel);
        loginUserModel = LoginUserModel.from(createUserModel);
        loginUserModel.setEmail(null);
        loginUserModel.setPassword(null);

        ValidatableResponse loginUser = userSteps.loginUser(loginUserModel);
        userResult.loginUserIncorrectData(loginUser);

        String accessToken = createUser.extract().path("accessToken");
        response = userSteps.deletedUser(StringUtils.substringAfter(accessToken, ""));
    }
}
