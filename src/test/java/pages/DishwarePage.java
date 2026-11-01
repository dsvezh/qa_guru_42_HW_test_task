package pages;

import com.codeborne.selenide.SelenideElement;
import components.ProductListComponent;
import models.SelectedProduct;

import java.math.BigDecimal;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class DishwarePage {
    private final ProductListComponent productList = new ProductListComponent();
    private final SelenideElement categoryHeading = $("h1.catalogue__heading");
    private final SelenideElement minPriceInput = $("input[name='gte']");
    private final SelenideElement maxPriceInput = $("input[name='lte']");

    public DishwarePage shouldHaveDishwareUrl() {
        webdriver().shouldHave(urlContaining("/catalog/posuda/"));
        return this;
    }

    public DishwarePage shouldHaveDishwareHeading() {
        categoryHeading.shouldBe(visible).shouldHave(exactText("Посуда"));
        return this;
    }

    public DishwarePage shouldHaveProducts() {
        productList.shouldHaveProducts();
        return this;
    }

    public SelectedProduct firstProduct() {
        return productList.firstProduct();
    }

    public ProductPage openProduct(SelectedProduct product) {
        return productList.openProduct(product);
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
        productList.shouldHavePricesBetween(min, max);
        return this;
    }
}
