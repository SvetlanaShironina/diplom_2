package orderTest;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.ingredient.IngredientList;
import site.nomoreparties.stellarburgers.order.OrderResult;
import site.nomoreparties.stellarburgers.order.OrderSteps;
import site.nomoreparties.stellarburgers.user.CreateUserModel;
import site.nomoreparties.stellarburgers.user.UserSteps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ReceiptOrderTest {
    private OrderSteps orderSteps;
    private OrderResult orderResult;
    private UserSteps userSteps;
    private ValidatableResponse response;
    String ingredients = IngredientList.allListIngredients();

    @Before
    @Step("Создание тестовых данных юзера")
    public void setUp() {
        orderSteps = new OrderSteps();
        userSteps = new UserSteps();
        orderResult = new OrderResult();
    }

    @Test
    @DisplayName("Проверяем получение заказа без авторизации")
    @Description("Проверяем, что заказ нельзя получить без авторизации")
    public void receiptOrderWithoutAuthorization() {
        ValidatableResponse receiptOrderWithoutAuthorization = orderSteps.receiptOrderWithoutAuthorization();
        orderResult.receiptOrderWithoutAuthorization(receiptOrderWithoutAuthorization);
    }

    @Test
    @DisplayName("Проверяем получение заказа c авторизацией")
    @Description("Проверяем, что авторизированный пользователь может получить заказ")
    public void receiptOrderWithAuthorization() {
        String accessToken = CreateUserModel.userRandom();
        ValidatableResponse orderNumber = orderSteps.createOrderWithAuthorization(accessToken, ingredients);
        int orderNumberExpected = orderNumber.extract().path("order.number");
        ValidatableResponse receiptOrderWithAuthorization = orderSteps.receiptOrderWithAuthorization(accessToken);
        int orderNumberActual = receiptOrderWithAuthorization.extract().path("orders[0].number");

        orderResult.receiptOrderWithAuthorization(receiptOrderWithAuthorization);
        assertEquals("Номер заказа не совпадает", orderNumberExpected, orderNumberActual);

        response = userSteps.deletedUser(StringUtils.substringAfter(accessToken, ""));
    }
}
