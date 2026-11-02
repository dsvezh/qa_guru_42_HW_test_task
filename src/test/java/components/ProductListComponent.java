package components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import models.SelectedProduct;
import org.openqa.selenium.By;
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
    private final String productPriceSelector = "[itemprop='price']";

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
        cards.shouldHave(sizeGreaterThan(0), Duration.ofSeconds(10));

        cards.shouldHave(allMatch(
                "каждая карточка содержит цену от " + min + " до " + max,
                card -> {
                    var prices = card.findElements(
                            By.cssSelector(productPriceSelector));

                    if (prices.size() != 1) {
                        return false;
                    }

                    String priceText = prices.get(0).getText()
                            .replaceAll("[^0-9,.]", "")
                            .replace(',', '.');

                    if (priceText.isEmpty()) {
                        return false;
                    }

                    BigDecimal price = new BigDecimal(priceText);
                    return price.compareTo(min) >= 0
                            && price.compareTo(max) <= 0;
                }
        ), Duration.ofSeconds(10));
    }
}
