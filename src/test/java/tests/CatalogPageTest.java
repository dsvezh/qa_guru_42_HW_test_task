package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.CatalogPage;
import pages.MainPage;

import java.math.BigDecimal;

@DisplayName("Страница «Посуда»")
class CatalogPageTest extends TestBase {
    private final MainPage mainPage = new MainPage();

    @Test
    @DisplayName("Переход в «Посуду» открывает категорию с соответствующим заголовком")
    void dishwareCategoryIsOpened() {
        mainPage.openMainPage();
        CatalogPage catalog = mainPage.clickCategory("Посуда");

        catalog.shouldHaveUrl("/catalog/posuda/");
        catalog.shouldHaveHeading("Посуда");
        catalog.shouldHaveProducts();
    }

    @Test
    @DisplayName("Фильтр цены: цены всех загруженных товаров входят в заданный диапазон")
    void pricesAreWithinSelectedRange() {
        BigDecimal min = new BigDecimal("100");
        BigDecimal max = new BigDecimal("500");

        mainPage.openMainPage();
        CatalogPage catalog = mainPage.clickCategory("Посуда");

        catalog.setMinPriceFilter(min);
        catalog.shouldHaveUrl("gte%3D" + min.toPlainString());

        catalog.setMaxPriceFilter(max);

        catalog.shouldHaveUrl("gte%3D" + min.toPlainString());
        catalog.shouldHaveUrl("lte%3D" + max.toPlainString());
        catalog.shouldHaveAppliedPriceFilter(min, max);
        catalog.shouldHavePricesBetween(min, max);
    }
}
