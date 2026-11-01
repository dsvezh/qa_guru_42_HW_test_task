package models;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record SelectedProduct(String productName, String productUrl, String articleNumber) {
    public static SelectedProduct from(String productName, String productUrl) {
        Matcher matcher = Pattern.compile("_([0-9]+-[0-9]+)/?$").matcher(URI.create(productUrl).getPath());
        if (!matcher.find()) {
            throw new AssertionError("Не удалось прочитать артикул из ссылки: " + productUrl);
        }
        return new SelectedProduct(productName, productUrl, matcher.group(1));
    }

    public String path() {
        return URI.create(productUrl).getPath();
    }
}
