package site.nomoreparties.stellarburgers.ingredient;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import site.nomoreparties.stellarburgers.config.RequestSpec;
import static site.nomoreparties.stellarburgers.constantApi.ApiEndpoints.INGREDIENTS_GET;

public class IngredientSteps extends RequestSpec {

    @Step("Получение всего списка ингредиентов /api/ingredients")
    public ValidatableResponse getAllIngredients() {
        return requestSpec()
                .get(INGREDIENTS_GET)
                .then();
    }
}
