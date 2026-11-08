package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import models.SelectedProduct;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class ProductPage {
    private final SelenideElement productLabel = $("#product_label");

    @Step("Проверить адрес карточки выбранного товара")
    public ProductPage shouldMatchProductUrl(SelectedProduct expected) {
        webdriver().shouldHave(urlContaining(expected.path()));
        return this;
    }

    @Step("Проверить название и артикул выбранного товара в карточке")
    public ProductPage shouldMatchTitleAndArticleNumber(SelectedProduct expected) {
        productLabel.shouldBe(visible)
                .shouldHave(exactText(expected.titleWithArticleNumber()));
        return this;
    }

}
