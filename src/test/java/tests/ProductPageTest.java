package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.DishwarePage;
import pages.MainPage;
import models.SelectedProduct;

@DisplayName("Страница товара Галамарта")
class ProductPageTest extends TestBase {
    private final MainPage mainPage = new MainPage();

    @Test
    @DisplayName("Карточка товара: название и артикул совпадают с выбранным товаром")
    void productDetailsMatchSelectedProduct() {
        DishwarePage catalog = mainPage.openMainPage()
                .clickDishwareCategory();
        SelectedProduct expected = catalog.firstProduct();
        catalog.openProduct(expected)
                .shouldMatchProductUrl(expected)
                .shouldMatchTitleAndArticleNumber(expected);
    }
}
