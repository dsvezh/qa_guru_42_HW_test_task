package components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import models.SelectedProduct;
import pages.ProductPage;

import java.math.BigDecimal;
import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.allMatch;
import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class ProductListComponent {
    private final ElementsCollection cards = $$(".cards-list .card");
    private final ElementsCollection productTitles = $$(".cards-list a.card__title");
    private final SelenideElement firstProductTitle = cards.first().$("a.card__title");
    private final ElementsCollection productPrices = $$(".cards-list .card [itemprop='price']");

    public void shouldHaveProducts() {
        cards.shouldHave(sizeGreaterThan(0), Duration.ofSeconds(10));
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

    public void shouldContainProduct(SelectedProduct expected) {
        productTitles.findBy(attribute("href", expected.productUrl()))
                .shouldBe(visible).shouldHave(exactText(expected.productName()));
    }

    public void shouldBeEmpty() {
        cards.shouldHave(size(0));
    }

    public void shouldHavePricesBetween(BigDecimal min, BigDecimal max) {
        cards.shouldHave(sizeGreaterThan(0));
        productPrices.shouldHave(size(cards.size()))
                .shouldHave(allMatch("цена от " + min + " до " + max, element -> {
                    BigDecimal price = new BigDecimal(element.getText()
                            .replaceAll("[^0-9,.]", "").replace(',', '.'));
                    return price.compareTo(min) >= 0 && price.compareTo(max) <= 0;
                }));
    }
}
