package site.nomoreparties.stellarburgers.ingredient;

import io.restassured.response.ValidatableResponse;

import java.util.ArrayList;
import java.util.List;

public class IngredientList {
    private static IngredientSteps ingredientSteps;
    private String ingredients;

    public static List<String> ingredients(){
        List<String> ingredients = new ArrayList<>();
        ingredients.add(IngredientList.allListIngredients(1));
        ingredients.add(IngredientList.allListIngredients(2));
        return ingredients;
    }

    public static String allListIngredients(int index) {
        ingredientSteps = new IngredientSteps();

        ValidatableResponse response = ingredientSteps.getAllIngredients();
        String ingredient = response.extract().path(String.format("data._id[%s]", index));
        return ingredient;
    }
}
