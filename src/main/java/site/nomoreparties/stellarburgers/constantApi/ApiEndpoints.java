package site.nomoreparties.stellarburgers.constantApi;

public class ApiEndpoints {
    public static String BASE_URL = "https://stellarburgers.nomoreparties.site";
    //Создание заказа
    public static String ORDER_CREATE_POST = "/api/orders/";
    //Создание и регистрация пользователя
    public static String USER_CREATE_POST = "/api/auth/register";
    //Логин пользователя
    public static String USER_LOGIN_POST = "/api/auth/login";
    //Изменение данных пользователя
    public static String USER_UPDATES_DATA_PATCH = "/api/auth/user";
    //Получение данных конкретного пользователя
    public static String USER_RECEIVING_DATA_GET = "/api/auth/user";
    //Получение заказов конкретного пользователя
    public static String USER_RECEIPT_ORDERS_GET = "/api/orders";
    //Выхода из системы
    public static String USER_LOGOUT_POST = "/api/auth/logout";
    //Удаление пользователя
    public static String USER_DELETE = "/api/auth/user";
    //Получение списка ингредиентов
    public static String INGREDIENTS_GET = "/api/ingredients";
}
