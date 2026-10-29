package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;

import java.math.BigDecimal;

@DisplayName("Страница «Посуда»")
class DishwarePageTest extends TestBase {
    private final MainPage mainPage = new MainPage();

    @Test
    @DisplayName("Переход в «Посуду» открывает категорию с соответствующим заголовком")
    void dishwareCategoryIsOpened() {
        mainPage.openMainPage()
                .clickDishwareCategory()
                .shouldHaveDishwareUrl()
                .shouldHaveDishwareHeading()
                .shouldHaveProducts();
    }

    @Test
    @DisplayName("Фильтр цены: цены всех загруженных товаров входят в заданный диапазон")
    void pricesAreWithinSelectedRange() {
        BigDecimal min = new BigDecimal("100");
        BigDecimal max = new BigDecimal("500");
        mainPage.openMainPage()
                .clickDishwareCategory()
                .setFilterByPrice(min, max)
                .shouldHavePricesBetween(min, max);
    }
}
