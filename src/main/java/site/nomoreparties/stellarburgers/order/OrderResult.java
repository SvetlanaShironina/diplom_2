package site.nomoreparties.stellarburgers.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;

public class OrderResult {

    @Step("Успешное создание заказа")
    public void createOrderOK(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_OK)
                .body("success", is(true));
    }

    @Step("Заказ без ингредиентов")
    public void createOrderWithoutIngredients(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Step("Заказ с невалидный хешом ингредиента")
    public void createOrderInvalidHashIngredients(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @Step("Получение заказа без авторизации")
    public void receiptOrderWithoutAuthorization(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", equalTo("You should be authorised"));
    }

    @Step("Получение заказа c авторизацией")
    public void receiptOrderWithAuthorization(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_OK)
                .body("success", is(true));
    }
}
