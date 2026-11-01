package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import components.SearchComponent;

import java.time.Duration;

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
    private final SelenideElement changeInitialCityButton = $(".btn.btn_primary.btn_outline");
    private final SelenideElement saveCityButton = buttons.findBy(exactText("Сохранить"));
    private final ElementsCollection cityOptions = $$(".cities-list label");
    private final ElementsCollection sectionHeadings = $$("h2");
    private final SelenideElement dishwareCategory = $$(".categories-row-slider__item").findBy(exactText("Посуда"));


    public String getCity() {
        return city.shouldBe(visible).getText();
    }

    public MainPage openMainPage() {
        open("/");
        return this;
    }

    public MainPage openCitySelectionFromInitialDialog() {
        changeInitialCityButton.shouldBe(visible).click();
        return this;
    }

    public MainPage selectCity(String nameCity) {
        citySearchInput.shouldBe(visible).setValue(nameCity);
        cityOptions.findBy(exactText(nameCity)).shouldBe(visible).click();
        saveCityButton.shouldBe(visible).click();
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

    public MainPage shouldShowCatalogButton() {
        catalogButton.shouldBe(visible, enabled).shouldHave(text("Каталог"));
        return this;
    }

    public MainPage shouldShowMainSections() {
        for (String section : new String[]{"Акции", "Новинки", "ТОП-100 товаров"}) {
            sectionHeadings.findBy(exactText(section)).shouldBe(visible);
        }
        dishwareCategory.shouldBe(visible);
        return this;
    }

    public DishwarePage clickDishwareCategory() {
        dishwareCategory.shouldBe(visible).click();
        return new DishwarePage();
    }
}
