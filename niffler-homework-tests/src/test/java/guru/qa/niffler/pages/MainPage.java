package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class MainPage {

    private final SelenideElement
            spendingTable = $(".spendings-table tbody"),
            deleteSelectedButton = $(byText("Delete selected"));

    public MainPage checkMainPageIsOpened() {
        $("nav[class='header__navigation']").shouldBe(visible);
        return this;
    }

    public MainPage removeFooter() {
        executeJavaScript("document.querySelector('footer').remove()");
        return this;
    }

    public MainPage selectSpendingRow(String spendingDescription) {
        spendingTable
                .$$("tr")
                .find(text(spendingDescription))
                .$$("td")
                .first()
                .click();
        return this;
    }

    public MainPage clickDeleteSelectedButton() {
        deleteSelectedButton.click();
        return this;
    }

    public MainPage checkSpendingTableIsEmpty() {
        spendingTable
                .$$("tr")
                .shouldHave(size(0));
        return this;
    }
}
