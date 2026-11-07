package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.CatalogPage;
import pages.MainPage;

import java.math.BigDecimal;

import static io.qameta.allure.Allure.step;

@DisplayName("Страница «Посуда»")
class CatalogPageTest extends TestBase {
    private final MainPage mainPage = new MainPage();

    @Test
    @DisplayName("Переход в «Посуду» открывает категорию с соответствующим заголовком")
    void dishwareCategoryIsOpened() {
        step("Открыть главную страницу", () -> mainPage.openMainPage());
        CatalogPage catalog = step("Перейти в категорию «Посуда»", () -> mainPage.clickCategory("Посуда"));
        step("Проверить адрес категории «Посуда»", () -> catalog.shouldHaveUrl("/catalog/posuda/"));
        step("Проверить заголовок «Посуда»", () -> catalog.shouldHaveHeading("Посуда"));
        step("Проверить, что в категории есть товары", () -> catalog.shouldHaveProducts());
    }

    @Test
    @DisplayName("Фильтр цены: цены всех загруженных товаров входят в заданный диапазон")
    void pricesAreWithinSelectedRange() {
        BigDecimal min = new BigDecimal("100");
        BigDecimal max = new BigDecimal("500");
        step("Открыть главную страницу", () -> mainPage.openMainPage());
        CatalogPage catalog = step("Перейти в категорию «Посуда»", () -> mainPage.clickCategory("Посуда"));
        step("Установить фильтр цены от " + min + " до " + max + " ₽ и проверить его применение",
                () -> catalog.setFilterByPrice(min, max));
        step("Проверить цены всех загруженных товаров в диапазоне от "
                + min + " до " + max + " ₽", () -> catalog.shouldHavePricesBetween(min, max));
    }
}
