package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import models.SelectedProduct;
import pages.components.ProductListComponent;
import pages.components.SearchComponent;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class CatalogPage {
    private final ProductListComponent productList = new ProductListComponent();
    private final SearchComponent search = new SearchComponent();
    private final SelenideElement categoryHeading = $("h1.catalogue__heading");
    private final SelenideElement minPriceInput = $("input[name='gte']");
    private final SelenideElement maxPriceInput = $("input[name='lte']");
    private final ElementsCollection filterBadges = $$(".filters__badges-item");

    @Step("Проверить, что адрес страницы содержит «{expectedUrlPart}»")
    public CatalogPage shouldHaveUrl(String expectedUrlPart) {
        webdriver().shouldHave(urlContaining(expectedUrlPart));
        return this;
    }

    @Step("Проверить заголовок категории «{expectedTitle}»")
    public CatalogPage shouldHaveHeading(String expectedTitle) {
        categoryHeading.shouldBe(visible).shouldHave(exactText(expectedTitle));
        return this;
    }

    @Step("Проверить, что в категории есть товары")
    public CatalogPage shouldHaveProducts() {
        productList.shouldHaveProducts();
        return this;
    }

    @Step("Запомнить данные первого товара в каталоге")
    public SelectedProduct firstProduct() {
        return productList.firstProduct();
    }

    @Step("Открыть карточку выбранного товара")
    public ProductPage openProduct(SelectedProduct product) {
        return productList.openProduct(product);
    }

    @Step("Установить минимальную цену {min} ₽")
    public CatalogPage setMinPriceFilter(BigDecimal min) {
        minPriceInput.setValue(min.toPlainString()).pressTab();
        return this;
    }

    @Step("Установить максимальную цену {max} ₽")
    public CatalogPage setMaxPriceFilter(BigDecimal max) {
        maxPriceInput.setValue(max.toPlainString()).pressTab();
        return this;
    }

    @Step("Проверить, что поля и бейджи показывают диапазон от {min} до {max} ₽")
    public CatalogPage shouldHaveAppliedPriceFilter(BigDecimal min, BigDecimal max) {
        minPriceInput.shouldHave(value(min.toPlainString()));
        maxPriceInput.shouldHave(value(max.toPlainString()));
        filterBadges.findBy(matchText("^Минимальная Цена:\\s*" + Pattern.quote(min.toPlainString()) + "\\s*₽$"))
                .shouldBe(visible);
        filterBadges.findBy(matchText("^Максимальная Цена:\\s*" + Pattern.quote(max.toPlainString()) + "\\s*₽$"))
                .shouldBe(visible);
        return this;
    }

    @Step("Проверить, что цены всех загруженных товаров находятся в диапазоне от {min} до {max} ₽")
    public CatalogPage shouldHavePricesBetween(BigDecimal min, BigDecimal max) {
        productList.shouldHavePricesBetween(min, max);
        return this;
    }

    @Step("Найти товар по артикулу {articleNumber}")
    public SearchPage searchByArticleNumber(String articleNumber) {
        return search.search(articleNumber);
    }
}
