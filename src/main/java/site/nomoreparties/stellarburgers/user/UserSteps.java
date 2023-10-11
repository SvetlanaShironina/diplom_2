package site.nomoreparties.stellarburgers.user;

import site.nomoreparties.stellarburgers.config.RequestSpec;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static site.nomoreparties.stellarburgers.constantApi.ApiEndpoints.*;

public class UserSteps extends RequestSpec {

    @Step("Регистрация нового юзера /api/auth/register")
    public ValidatableResponse createUser(CreateUserModel createUserModel) {
        return requestSpec()
                .body(createUserModel)
                .when()
                .post(USER_CREATE_POST)
                .then();
    }

    @Step("Логин юзера /api/auth/login")
    public ValidatableResponse loginUser(LoginUserModel loginUserModel) {
        return requestSpec()
                .body(loginUserModel)
                .when()
                .post(USER_LOGIN_POST)
                .then();
    }

    @Step("Изменение данных пользователя с авторизацией /api/auth/user")
    public ValidatableResponse userAuthorizationUpdateData(CreateUserModel createUserModel, String accessToken) {
        return requestSpec()
                .header("Authorization", accessToken)
                .body(createUserModel)
                .when()
                .patch(USER_UPDATES_DATA_PATCH)
                .then();
    }

    @Step("Изменение данных пользователя без авторизации /api/auth/user")
    public ValidatableResponse userWithoutAuthorizationUpdateData(CreateUserModel createUserModel) {
        return requestSpec()
                .body(createUserModel)
                .when()
                .patch(USER_UPDATES_DATA_PATCH)
                .then();
    }

    @Step("Получение данных конкретного пользователя /api/auth/user")
    public ValidatableResponse userReceivingData(CreateUserModel сreateUserModel, String accessToken) {
        return requestSpec()
                .header("Authorization", accessToken)
                .body(сreateUserModel)
                .when()
                .get(USER_RECEIVING_DATA_GET)
                .then();
    }

    @Step("Выхода из системы /api/auth/logout")
    public ValidatableResponse logoutUser(String refreshToken){
        return requestSpec()
                .body(refreshToken)
                .when()
                .post(USER_LOGOUT_POST)
                .then();
    }

    @Step("Удаление юзера /api/auth/user")
    public ValidatableResponse deletedUser(String accessToken){
        return requestSpec()
                .auth().oauth2(accessToken)
                .when()
                .delete(USER_DELETE)
                .then();
    }
}