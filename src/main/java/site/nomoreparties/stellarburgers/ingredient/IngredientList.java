package site.nomoreparties.stellarburgers.ingredient;

import io.restassured.response.ValidatableResponse;

public class IngredientList {
    private static IngredientSteps ingredientSteps;
    public static String allListIngredients() {
        ingredientSteps = new IngredientSteps();

        ValidatableResponse response = ingredientSteps.getAllIngredients();
        String ingredients = response.extract().path("data._id[1]");
        return ingredients;
    }
}
