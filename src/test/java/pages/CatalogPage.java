package pages;

import com.codeborne.selenide.SelenideElement;
import components.ProductListComponent;
import models.SelectedProduct;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class CatalogPage {
    private final ProductListComponent productList = new ProductListComponent();
    private final SelenideElement categoryHeading = $("h1.catalogue__heading");
    private final SelenideElement minPriceInput = $("input[name='gte']");
    private final SelenideElement maxPriceInput = $("input[name='lte']");
    private final SelenideElement minPriceBadge = $$(".filters__badges-item").findBy(text("Минимальная Цена"));
    private final SelenideElement maxPriceBadge = $$(".filters__badges-item").findBy(text("Максимальная Цена"));

    public CatalogPage shouldHaveUrl(String expectedUrlPart) {
        webdriver().shouldHave(urlContaining(expectedUrlPart));
        return this;
    }

    public CatalogPage shouldHaveHeading(String expectedTitle) {
        categoryHeading.shouldBe(visible).shouldHave(exactText(expectedTitle));
        return this;
    }

    public CatalogPage shouldHaveProducts() {
        productList.shouldHaveProducts();
        return this;
    }

    public SelectedProduct firstProduct() {
        return productList.firstProduct();
    }

    public ProductPage openProduct(SelectedProduct product) {
        return productList.openProduct(product);
    }

    public CatalogPage setMinPriceFilter(BigDecimal min) {
        minPriceInput.setValue(min.toPlainString()).pressTab();
        return this;
    }

    public CatalogPage setMaxPriceFilter(BigDecimal max) {
        maxPriceInput.setValue(max.toPlainString()).pressTab();
        return this;
    }

    public CatalogPage shouldHaveAppliedPriceFilter(BigDecimal min, BigDecimal max) {
        minPriceInput.shouldHave(value(min.toPlainString()));
        maxPriceInput.shouldHave(value(max.toPlainString()));
        minPriceBadge.shouldBe(visible).shouldHave(matchText(
                "^Минимальная Цена:\\s*" + Pattern.quote(min.toPlainString()) + "\\s*₽$"));
        maxPriceBadge.shouldBe(visible).shouldHave(matchText(
                "^Максимальная Цена:\\s*" + Pattern.quote(max.toPlainString()) + "\\s*₽$"));
        return this;
    }

    public CatalogPage shouldHavePricesBetween(BigDecimal min, BigDecimal max) {
        productList.shouldHavePricesBetween(min, max);
        return this;
    }
}
