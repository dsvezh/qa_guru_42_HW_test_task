package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import models.SelectedProduct;
import pages.components.ProductListComponent;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class SearchPage {
    private final ProductListComponent productList = new ProductListComponent();
    private final SelenideElement notFoundText = $(".notfound__text");
    private final SelenideElement notFoundRequest = $(".notfound__request");

    @Step("Проверить переход на страницу результатов поиска")
    public SearchPage shouldShowResults() {
        webdriver().shouldHave(urlContaining("/search/?query="));
        return this;
    }

    @Step("Проверить название и ссылку выбранного товара в результатах поиска")
    public SearchPage shouldContainProduct(SelectedProduct expected) {
        productList.shouldContainProduct(expected);
        return this;
    }

    @Step("Проверить, что поиск по запросу «{query}» показывает сообщение об отсутствии товаров и пустую выдачу")
    public SearchPage shouldShowNoResults(String query) {
        notFoundText.shouldBe(visible)
                .shouldHave(exactText("По такому запросу ничего не найдено"));
        notFoundRequest.shouldHave(text(query));
        productList.shouldBeEmpty();
        return this;
    }
}
