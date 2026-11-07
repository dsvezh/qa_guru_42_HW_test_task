package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import components.SearchComponent;
import pages.MainPage;
import pages.CatalogPage;
import pages.SearchPage;
import models.SelectedProduct;

import java.util.UUID;

import static io.qameta.allure.Allure.step;

@DisplayName("Страница результатов поиска Галамарта")
class SearchPageTest extends TestBase {
    private final MainPage mainPage = new MainPage();
    private final SearchComponent search = new SearchComponent();

    @Test
    @DisplayName("Поиск существующего товара по артикулу находит выбранный товар")
    void existingProductIsFound() {
        step("Открыть главную страницу", () -> mainPage.openMainPage());
        CatalogPage catalog = step("Перейти в категорию «Посуда»", () -> mainPage.clickCategory("Посуда"));
        SelectedProduct expected = step("Запомнить данные первого товара в каталоге", () -> catalog.firstProduct());
        SearchPage results = step("Найти товар по артикулу " + expected.articleNumber(),
                () -> search.search(expected.articleNumber()));
        step("Проверить переход на страницу результатов поиска", () -> results.shouldShowResults());
        step("Проверить название и ссылку выбранного товара в результатах поиска",
                () -> results.shouldContainProduct(expected));
    }

    @Test
    @DisplayName("Поиск несуществующего товара: пустая выдача и сообщение")
    void nonexistentProductShowsEmptyResults() {
        String query = "test-no-product-" + UUID.randomUUID().toString().replace("-", "");
        step("Открыть главную страницу", () -> mainPage.openMainPage());
        SearchPage results = step("Выполнить поиск по несуществующему запросу «" + query + "»",
                () -> search.search(query));
        step("Проверить сообщение об отсутствии товаров, поисковый запрос и пустую выдачу",
                () -> results.shouldShowNoResults(query));
    }
}
