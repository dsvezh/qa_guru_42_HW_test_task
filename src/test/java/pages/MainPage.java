package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import components.SearchComponent;

import java.util.List;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class MainPage {
    private final SearchComponent search = new SearchComponent();
    private final SelenideElement city = $(".location__dropdown span");
    private final SelenideElement citySearchInput = $("#search-input");
    private final SelenideElement catalogButton = $(".header__catalog-button");
    private final ElementsCollection buttons = $$("button");
    private final ElementsCollection cityOptions = $$(".cities-list label");
    private final ElementsCollection sectionHeadings = $$("h2");
    private final ElementsCollection categories = $$(".categories-row-slider__item");


    public String getCity() {
        return city.shouldBe(visible).getText();
    }

    public MainPage openMainPage() {
        open("/");
        return this;
    }

    public MainPage openCitySelectionFromInitialDialog() {
        city.shouldBe(visible).click();
        return this;
    }

    public MainPage selectCity(String nameCity) {
        citySearchInput.shouldBe(visible).setValue(nameCity);
        cityOptions.findBy(exactText(nameCity)).shouldBe(visible).click();
        buttons.findBy(exactText("Сохранить")).shouldBe(visible).click();
        citySearchInput.should(disappear);
        return this;
    }

    public MainPage shouldShowCity(String nameCity) {
        city.shouldBe(visible).shouldHave(exactText(nameCity));
        return this;
    }

    public MainPage shouldShowSearch() {
        search.shouldBeAvailable();
        return this;
    }

    public MainPage shouldShowCatalogButton(String expectedText) {
        catalogButton.shouldBe(visible, enabled).shouldHave(text(expectedText));
        return this;
    }

    public MainPage shouldShowMainSections(List<String> expectedSections) {
        for (String section : expectedSections) {
            sectionHeadings.findBy(exactText(section)).shouldBe(visible);
        }
        return this;
    }

    public CatalogPage clickCategory(String categoryTitle) {
        categories.findBy(exactText(categoryTitle)).shouldBe(visible).click();
        return new CatalogPage();
    }
}
