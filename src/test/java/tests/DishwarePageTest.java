package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.DishwarePage;

import java.math.BigDecimal;

import static io.qameta.allure.Allure.step;

@DisplayName("Страница «Посуда»")
class DishwarePageTest extends TestBase {
    private final MainPage mainPage = new MainPage();

    @Test
    @DisplayName("Переход в «Посуду» открывает категорию с соответствующим заголовком")
    void dishwareCategoryIsOpened() {
        step("Открыть главную страницу", () -> mainPage.openMainPage());
        DishwarePage catalog = step("Перейти в категорию «Посуда»", () -> mainPage.clickDishwareCategory());
        step("Проверить адрес категории «Посуда»", () -> catalog.shouldHaveDishwareUrl());
        step("Проверить заголовок «Посуда»", () -> catalog.shouldHaveDishwareHeading());
        step("Проверить, что в категории есть товары", () -> catalog.shouldHaveProducts());
    }

    @Test
    @DisplayName("Фильтр цены: цены всех загруженных товаров входят в заданный диапазон")
    void pricesAreWithinSelectedRange() {
        BigDecimal min = new BigDecimal("100");
        BigDecimal max = new BigDecimal("500");
        step("Открыть главную страницу", () -> mainPage.openMainPage());
        DishwarePage catalog = step("Перейти в категорию «Посуда»", () -> mainPage.clickDishwareCategory());
        step("Установить фильтр цены от " + min + " до " + max + " ₽ и проверить его применение",
                () -> catalog.setFilterByPrice(min, max));
        step("Проверить цены всех загруженных товаров в диапазоне от "
                + min + " до " + max + " ₽", () -> catalog.shouldHavePricesBetween(min, max));
    }
}
