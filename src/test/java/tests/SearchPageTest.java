package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import components.SearchComponent;
import pages.MainPage;
import models.SelectedProduct;

import java.util.UUID;

@DisplayName("Страница результатов поиска Галамарта")
class SearchPageTest extends TestBase {
    private final MainPage mainPage = new MainPage();
    private final SearchComponent search = new SearchComponent();

    @Test
    @DisplayName("Поиск существующего товара по названию находит выбранный товар")
    void existingProductIsFound() {
        SelectedProduct expected = mainPage.openMainPage()
                .clickDishwareCategory()
                .firstProduct();
        search.search(expected.productName())
                .shouldContainProduct(expected);
    }

    @Test
    @DisplayName("Поиск несуществующего товара: пустая выдача и сообщение")
    void nonexistentProductShowsEmptyResults() {
        String query = "test-no-product-" + UUID.randomUUID().toString().replace("-", "");
        mainPage.openMainPage();
        search.search(query)
                .shouldShowNoResults(query);
    }
}
