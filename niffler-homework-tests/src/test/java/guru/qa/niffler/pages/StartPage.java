package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class StartPage {

    private final SelenideElement loginButton = $("a[href*='redirect']");

    public StartPage checkStartPageIsOpened() {
        $("h1[class='main__header']").shouldHave(text("Welcome to magic journey with Niffler. The coin keeper"));
        return this;
    }

    public LoginPage clickLoginButton() {
        loginButton.click();
        return new LoginPage();
    }
}
