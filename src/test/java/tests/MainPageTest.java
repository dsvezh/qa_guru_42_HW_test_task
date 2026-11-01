package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;

import static com.codeborne.selenide.Selenide.refresh;

@DisplayName("Главная страница Галамарта")
class MainPageTest extends TestBase {
    private final MainPage mainPage = new MainPage();

    @Test
    @DisplayName("Главная страница: видны поиск и основные разделы")
    void homePageContainsSearchAndMainSections() {
        mainPage.openConfiguredPage()
                .shouldShowSearch()
                .shouldShowCatalogButton()
                .shouldShowMainSections();
    }

    @Test
    @DisplayName("Выбранный город отображается в шапке и сохраняется после обновления")
    void selectedCityIsDisplayedInHeader() {
        mainPage.openMainPage();
        String currentCity = mainPage.getCity();
        String anotherCity = currentCity.equals("Москва") ? "Екатеринбург" : "Москва";
        mainPage.chooseCityFromInitialDialog(anotherCity)
                .shouldShowCity(anotherCity);
        refresh();
        mainPage.shouldShowCity(anotherCity);
    }
}
