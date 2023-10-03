package site.nomoreparties.stellarburgers.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import site.nomoreparties.stellarburgers.config.RequestSpec;
import static site.nomoreparties.stellarburgers.constantApi.ApiEndpoints.*;

public class OrderSteps extends RequestSpec {

    @Step("Создание заказа без авторизации /api/orders")
    public ValidatableResponse createOrderWithoutAuthorization(String ingredients) {
        return requestSpec()
                .body("{\"ingredients\" : [\"" + ingredients + "\"]}")
                .when()
                .post(ORDER_CREATE_POST)
                .then();
    }

    @Step("Создание заказа без ингредиентов /api/orders")
    public ValidatableResponse createOrderWithoutIngredients() {
        return requestSpec()
                .body("{\"ingredients\" : []}")
                .when()
                .post(ORDER_CREATE_POST)
                .then();
    }

    @Step("Создание заказа c авторизацией /api/orders")
    public ValidatableResponse createOrderWithAuthorization(String accessToken, String hashIngredient) {
        return requestSpec()
                .header("Authorization", accessToken)
                .body("{\"ingredients\" : [\"" + hashIngredient + "\"]}")
                .when()
                .post(ORDER_CREATE_POST)
                .then();
    }

    @Step("Получение заказа без авторизации /api/orders")
    public ValidatableResponse receiptOrderWithoutAuthorization() {
        return requestSpec()
                .when()
                .get(USER_RECEIPT_ORDERS_GET)
                .then();
    }

    @Step("Получение заказа c авторизацией /api/orders")
    public ValidatableResponse receiptOrderWithAuthorization(String accessToken) {
        return requestSpec()
                .header("Authorization", accessToken)
                .when()
                .get(USER_RECEIPT_ORDERS_GET)
                .then();
    }
}
