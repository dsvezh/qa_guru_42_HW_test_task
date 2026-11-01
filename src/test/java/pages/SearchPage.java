package pages;

import com.codeborne.selenide.SelenideElement;
import components.ProductListComponent;
import models.SelectedProduct;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class SearchPage {
    private final ProductListComponent productList = new ProductListComponent();
    private final SelenideElement notFoundText = $(".notfound__text");
    private final SelenideElement notFoundRequest = $(".notfound__request");

    public SearchPage shouldContainProduct(SelectedProduct expected) {
        productList.shouldContainProduct(expected);
        return this;
    }

    public SearchPage shouldShowNoResults(String query) {
        notFoundText.shouldBe(visible)
                .shouldHave(exactText("По такому запросу ничего не найдено"));
        notFoundRequest.shouldHave(text(query));
        productList.shouldBeEmpty();
        return this;
    }
}
