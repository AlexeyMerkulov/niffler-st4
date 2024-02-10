package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private final SelenideElement
            usernameInput = $("input[name='username']"),
            passwordInput = $("input[name='password']"),
            signInButton = $("button[type='submit']");

    public LoginPage checkLoginPageIsOpened() {
        $("h1[class='form__header']").shouldHave(text("Welcome to Niffler. The coin keeper"));
        return this;
    }

    public LoginPage setUsername(String userName) {
        usernameInput.setValue(userName);
        return this;
    }

    public LoginPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    public MainPage clickSignInButton() {
        signInButton.click();
        return new MainPage();
    }
}
