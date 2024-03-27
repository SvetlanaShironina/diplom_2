package site.nomoreparties.stellarburgers.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import site.nomoreparties.stellarburgers.config.RequestSpec;
import site.nomoreparties.stellarburgers.ingredient.IngredientList;

import java.util.Map;

import static site.nomoreparties.stellarburgers.constantApi.ApiEndpoints.*;

public class OrderSteps extends RequestSpec {
private IngredientList ingredientList;


    @Step("Создание заказа без авторизации /api/orders")
    public ValidatableResponse createOrderWithoutAuthorization() {
        return requestSpec()
                .body(Map.of("ingredients", ingredientList.ingredients()))
                .when()
                .post(ORDER_CREATE_POST)
                .then();
    }

    @Step("Создание заказа без авторизации /api/orders")
    public ValidatableResponse createOrderWithoutAuthorizationInvalidHash() {
        return requestSpec()
                .body(Map.of("ingredients", "11111111111111"))
                .when()
                .post(ORDER_CREATE_POST)
                .then();
    }

    @Step("Создание заказа без ингредиентов /api/orders")
    public ValidatableResponse createOrderWithoutIngredients() {
        return requestSpec()
                .body(Map.of("ingredients", ""))
                .when()
                .post(ORDER_CREATE_POST)
                .then();
    }

    @Step("Создание заказа c авторизацией /api/orders")
    public ValidatableResponse createOrderWithAuthorization(String accessToken) {
        return requestSpec()
                .header("Authorization", accessToken)
                .body(Map.of("ingredients", ingredientList.ingredients()))
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
