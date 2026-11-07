package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;

import static com.codeborne.selenide.Selenide.refresh;
import static io.qameta.allure.Allure.step;

@DisplayName("Главная страница Галамарта")
class MainPageTest extends TestBase {
    private final MainPage mainPage = new MainPage();

    @Test
    @DisplayName("Главная страница: видны поиск и основные разделы")
    void homePageContainsSearchAndMainSections() {
        step("Открыть главную страницу",
                () -> mainPage.openMainPage());

        step("Проверить доступность поиска",
                () -> mainPage.shouldShowSearch());
        step("Проверить кнопку «Каталог»",
                () -> mainPage.shouldShowCatalogButton());
        step("Проверить разделы «Акции», «Новинки», «ТОП-100 товаров» и ссылку «Посуда»",
                () -> mainPage.shouldShowMainSections());
    }

    @Test
    @DisplayName("Выбранный город отображается в шапке и сохраняется после обновления")
    void selectedCityIsDisplayedInHeader() {
        step("Открыть главную страницу",
                () -> mainPage.openMainPage());
        String currentCity = step("Определить текущий город в шапке",
                () -> mainPage.getCity());
        String anotherCity = currentCity.equals("Москва") ? "Екатеринбург" : "Москва";

        step("Открыть выбор города",
                () -> mainPage.openCitySelectionFromInitialDialog());
        step("Выбрать и сохранить город «" + anotherCity + "»",
                () -> mainPage.selectCity(anotherCity));

        step("Проверить город «" + anotherCity + "» в шапке",
                () -> mainPage.shouldShowCity(anotherCity));

        step("Обновить страницу", () -> refresh());
        step("Проверить, что после обновления сохранён город «" + anotherCity + "»",
                () -> mainPage.shouldShowCity(anotherCity));
    }
}
