package pages;

import com.codeborne.selenide.SelenideElement;

import models.SelectedProduct;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class ProductPage {
    private final SelenideElement productLabel = $("#product_label");

    public ProductPage shouldMatchProductUrl(SelectedProduct expected) {
        webdriver().shouldHave(urlContaining(expected.path()));
        return this;
    }

    public ProductPage shouldMatchTitleAndArticleNumber(SelectedProduct expected) {
        productLabel.shouldBe(visible)
                .shouldHave(exactText(expected.productName() + " (" + expected.articleNumber() + ")"));
        return this;
    }

}
