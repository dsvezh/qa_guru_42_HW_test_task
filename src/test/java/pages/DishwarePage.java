package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import models.SelectedProduct;

import java.math.BigDecimal;
import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.allMatch;
import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class DishwarePage {
    private final ElementsCollection cards = $$(".cards-list .card");
    private final SelenideElement firstProductTitle = cards.first().$("a.card__title");
    private final ElementsCollection productTitles = $$(".cards-list a.card__title");
    private final SelenideElement categoryHeading = $("h1.catalogue__heading");
    private final SelenideElement minPriceInput = $("input[name='gte']");
    private final SelenideElement maxPriceInput = $("input[name='lte']");
    private final ElementsCollection productPrices = $$(".cards-list .card [itemprop='price']");

    public DishwarePage shouldHaveDishwareUrl() {
        webdriver().shouldHave(urlContaining("/catalog/posuda/"));
        return this;
    }

    public DishwarePage shouldHaveDishwareHeading() {
        categoryHeading.shouldBe(visible).shouldHave(exactText("Посуда"));
        return this;
    }

    public DishwarePage shouldHaveProducts() {
        cards.shouldHave(sizeGreaterThan(0), Duration.ofSeconds(10));
        return this;
    }

    public SelectedProduct firstProduct() {
        cards.shouldHave(sizeGreaterThan(0));
        firstProductTitle.shouldBe(visible);
        return SelectedProduct.from(firstProductTitle.getText(), firstProductTitle.getAttribute("href"));
    }

    public ProductPage openProduct(SelectedProduct product) {
        productTitles.findBy(attribute("href", product.productUrl()))
                .shouldBe(visible).click();
        return new ProductPage();
    }

    public DishwarePage setFilterByPrice(BigDecimal min, BigDecimal max) {
        minPriceInput.shouldBe(visible)
                .setValue(min.toPlainString())
                .pressTab();
        webdriver().shouldHave(urlContaining("gte%3D" + min.toPlainString()));
        maxPriceInput.shouldBe(visible)
                .setValue(max.toPlainString())
                .pressTab();
        webdriver().shouldHave(urlContaining("gte%3D" + min.toPlainString()));
        webdriver().shouldHave(urlContaining("lte%3D" + max.toPlainString()));
        minPriceInput.shouldHave(value(min.toPlainString()));
        maxPriceInput.shouldHave(value(max.toPlainString()));
        return this;
    }

    public DishwarePage shouldHavePricesBetween(BigDecimal min, BigDecimal max) {
        cards.shouldHave(sizeGreaterThan(0));
        productPrices.shouldHave(size(cards.size()))
                .shouldHave(allMatch("цена от " + min + " до " + max, element -> {
                    BigDecimal price = new BigDecimal(element.getText()
                            .replaceAll("[^0-9,.]", "").replace(',', '.'));
                    return price.compareTo(min) >= 0 && price.compareTo(max) <= 0;
                }));
        return this;
    }
}
