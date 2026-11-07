package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.CatalogPage;
import pages.MainPage;
import pages.ProductPage;
import models.SelectedProduct;

import static io.qameta.allure.Allure.step;

@DisplayName("Страница товара Галамарта")
class ProductPageTest extends TestBase {
    private final MainPage mainPage = new MainPage();

    @Test
    @DisplayName("Карточка товара: название и артикул совпадают с выбранным товаром")
    void productDetailsMatchSelectedProduct() {
        step("Открыть главную страницу", () -> mainPage.openMainPage());
        CatalogPage catalog = step("Перейти в категорию «Посуда»", () -> mainPage.clickCategory("Посуда"));
        SelectedProduct expected = step("Запомнить данные первого товара в каталоге", () -> catalog.firstProduct());
        ProductPage product = step("Открыть карточку товара с артикулом " + expected.articleNumber(),
                () -> catalog.openProduct(expected));
        step("Проверить адрес карточки выбранного товара", () -> product.shouldMatchProductUrl(expected));
        step("Проверить название и артикул товара в карточке",
                () -> product.shouldMatchTitleAndArticleNumber(expected));
    }
}
