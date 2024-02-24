package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import guru.qa.niffler.jupiter.GenerateCategory;
import guru.qa.niffler.jupiter.GenerateSpend;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.pages.MainPage;
import guru.qa.niffler.pages.StartPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

public class SpendingTest {

  static {
    Configuration.browserSize = "1980x1024";
  }

  @BeforeEach
  void doLogin() {
    open("http://127.0.0.1:3000/main");
    new StartPage()
            .checkStartPageIsOpened()
            .clickLoginButton()
            .checkLoginPageIsOpened()
            .setUsername("duck")
            .setPassword("12345")
            .clickSignInButton();
  }

  @GenerateCategory(
          username = "duck",
          category = "Обучение"
  )
  @GenerateSpend(
          username = "duck",
          description = "QA.GURU Advanced 4",
          amount = 72500.00,
          currency = CurrencyValues.RUB
  )
  @Test
  void spendingShouldBeDeletedByButtonDeleteSpending(SpendJson spend) {
    new MainPage()
            .checkMainPageIsOpened()
            .removeFooter()
            .selectSpendingRow(spend.description())
            .clickDeleteSelectedButton()
            .checkSpendingTableIsEmpty();
  }
}
