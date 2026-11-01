package components;

import com.codeborne.selenide.SelenideElement;
import pages.SearchPage;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class SearchComponent {
    private final SelenideElement searchInput = $("#search_input");

    public void shouldBeAvailable() {
        searchInput.shouldBe(visible, enabled);
    }

    public SearchPage search(String query) {
        searchInput.shouldBe(visible).setValue(query).pressEnter();
        searchInput.shouldHave(value(query));
        return new SearchPage();
    }
}
