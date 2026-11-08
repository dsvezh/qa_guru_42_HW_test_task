package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;

import java.util.List;

@DisplayName("Главная страница Галамарта")
class MainPageTest extends TestBase {
    private final MainPage mainPage = new MainPage();

    @Test
    @DisplayName("Главная страница: видны поиск и основные разделы")
    void homePageContainsSearchAndMainSections() {
        String catalogButtonText = "Каталог";
        List<String> expectedSections = List.of("Акции", "Новинки", "ТОП-100 товаров");

        mainPage.openMainPage();
        mainPage.shouldShowSearch();
        mainPage.shouldShowCatalogButton(catalogButtonText);
        mainPage.shouldShowMainSections(expectedSections);
    }

    @Test
    @DisplayName("Выбранный город отображается в шапке и сохраняется после обновления")
    void selectedCityIsDisplayedInHeader() {
        mainPage.openMainPage();
        String currentCity = mainPage.getCity();
        String anotherCity = currentCity.equals("Москва") ? "Екатеринбург" : "Москва";

        mainPage.openCitySelectionFromInitialDialog();
        mainPage.selectCity(anotherCity);
        mainPage.shouldShowCity(anotherCity);

        mainPage.refreshPage();
        mainPage.shouldShowCity(anotherCity);
    }
}
