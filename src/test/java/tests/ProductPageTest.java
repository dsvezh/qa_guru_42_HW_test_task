package tests;

import models.SelectedProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.CatalogPage;
import pages.MainPage;
import pages.ProductPage;

@DisplayName("Страница товара Галамарта")
class ProductPageTest extends TestBase {
    private final MainPage mainPage = new MainPage();

    @Test
    @DisplayName("Карточка товара: название и артикул совпадают с выбранным товаром")
    void productDetailsMatchSelectedProduct() {
        mainPage.openMainPage();
        CatalogPage catalog = mainPage.clickCategory("Посуда");
        SelectedProduct expected = catalog.firstProduct();

        ProductPage product = catalog.openProduct(expected);

        product.shouldMatchProductUrl(expected);
        product.shouldMatchTitleAndArticleNumber(expected);
    }
}
