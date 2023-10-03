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

public class CreateOrderTest {
    private OrderSteps orderSteps;
    private OrderResult orderResult;
    private UserSteps userSteps;
    private ValidatableResponse response;
    private IngredientList ingredientList;
    String ingredients = IngredientList.allListIngredients();

    @Before
    @Step("Создание тестовых данных юзера")
    public void setUp() {
    orderSteps = new OrderSteps();
    userSteps = new UserSteps();
    orderResult = new OrderResult();
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    @Description("Проверяем, что заказ можно создать")
    public void createOrderWithoutAuthorization() {

        ValidatableResponse createOrderWithoutAuthorization = orderSteps.createOrderWithoutAuthorization(ingredients);
        orderResult.createOrderOK(createOrderWithoutAuthorization);
    }

    @Test
    @DisplayName("Создание заказа c авторизацией и с ингредиентами")
    @Description("Проверяем, что заказ можно создать")
    public void createOrderWithAuthorization() {
        String accessToken = CreateUserModel.userRandom();
        response = userSteps.deletedUser(StringUtils.substringAfter(accessToken, ""));

        ValidatableResponse createOrderWithAuthorization = orderSteps.createOrderWithAuthorization(accessToken, ingredients);
        orderResult.createOrderOK(createOrderWithAuthorization);
    }

    @Test
    @DisplayName("Создание заказа без авторизации и без ингредиентов")
    @Description("Проверяем, что заказ нельзя создать, вернётся ошибка 400")
    public void createOrderWithoutIngredient() {
        ValidatableResponse createOrderWithoutIngredient = orderSteps.createOrderWithoutIngredients();
        orderResult.createOrderWithoutIngredients(createOrderWithoutIngredient);
    }

    @Test
    @DisplayName("Создание заказа без авторизации и с неверным хэшом ингредиента")
    @Description("Проверяем, что заказ нельзя создать, вернётся ошибка 500")
    public void createOrderInvalidHashIngredients() {
        ValidatableResponse createOrderInvalidHashIngredients = orderSteps.createOrderWithoutAuthorization("11111111111");
        orderResult.createOrderInvalidHashIngredients(createOrderInvalidHashIngredients);
    }
}