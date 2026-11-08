package tests;

import models.SelectedProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.CatalogPage;
import pages.MainPage;
import pages.SearchPage;

import java.util.UUID;

@DisplayName("Страница результатов поиска Галамарта")
class SearchPageTest extends TestBase {
    private final MainPage mainPage = new MainPage();

    @Test
    @DisplayName("Поиск существующего товара по артикулу находит выбранный товар")
    void existingProductIsFound() {
        mainPage.openMainPage();
        CatalogPage catalog = mainPage.clickCategory("Посуда");
        SelectedProduct expected = catalog.firstProduct();

        SearchPage results = catalog.searchByArticleNumber(expected.articleNumber());

        results.shouldShowResults();
        results.shouldContainProduct(expected);
    }

    @Test
    @DisplayName("Поиск несуществующего товара: пустая выдача и сообщение")
    void nonexistentProductShowsEmptyResults() {
        String query = "test-no-product-" + UUID.randomUUID().toString().replace("-", "");

        mainPage.openMainPage();
        SearchPage results = mainPage.search(query);

        results.shouldShowNoResults(query);
    }
}
