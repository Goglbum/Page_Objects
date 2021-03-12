package ru.netology.web.step;

import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Пусть;
import cucumber.api.java.ru.Тогда;
import lombok.val;
import ru.alfabank.alfatest.cucumber.api.AkitaScenario;
import ru.netology.web.data.DataHelper;
import ru.netology.web.pages.CardReplenishment;
import ru.netology.web.pages.DashboardPage;
import ru.netology.web.pages.LoginPage;
import ru.netology.web.pages.VerificationPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.alfabank.tests.core.helpers.PropertyLoader.loadProperty;


public class TemplateSteps {
  private final AkitaScenario scenario = AkitaScenario.getInstance();
  private DataHelper.DataHelperRepository repository = DataHelper.getRepository();


  @Пусть("^пользователь залогинен с именем \"([^\"]*)\" и паролем \"([^\"]*)\"$")
  public void loginWithNameAndPassword(String login, String password) {
    val loginUrl = loadProperty("loginUrl");
    open(loginUrl);

    scenario.setCurrentPage(page(LoginPage.class));
    val loginPage = (LoginPage) scenario.getCurrentPage().appeared();
    val authInfo = new DataHelper.AuthInfo(login, password);
    scenario.setCurrentPage(loginPage.validLogin(authInfo));

    val verificationPage = (VerificationPage) scenario.getCurrentPage().appeared();
    val verificationCode = DataHelper.getVerificationCodeFor();
    scenario.setCurrentPage(verificationPage.validVerify(verificationCode));
  }

  @Когда("^пользователь переводит случайную сумму \\(от \"([^\"]*)\" до \"([^\"]*)\"\\) с карты 1 на карту 2$")
  public void transferMoneyCardToCardRandomAmount(int min, int max) {
    val authInfo = new DataHelper.AuthInfo(min, max);
    val dashboardPage = (DashboardPage) scenario.getCurrentPage().appeared();
    repository = DataHelper.setStartingBalance(authInfo, dashboardPage.getBalanceCard1(), dashboardPage.getBalanceCard2());
    scenario.setCurrentPage(dashboardPage.replenishmentCard2());

    val cardReplenishment = (CardReplenishment) scenario.getCurrentPage().appeared();
    scenario.setCurrentPage(cardReplenishment.transferMoneyCard1ToCard2(authInfo));
    repository = DataHelper.setResultBalance(repository, dashboardPage.getBalanceCard1(), dashboardPage.getBalanceCard2());
    scenario.getCurrentPage().appeared();
  }

  @Тогда("^пользователь проверяет баланс после перевода с карты \"([^\"]*)\" на карту \"([^\"]*)\"$")
  public void calculationAndChekBalanceCard(int card, int card2) {
    if (card == 1) {
      assertEquals(repository.getResultBalanceCard1(),
              DataHelper.calculationMinusBalance(repository.getStartingBalanceCard1(), repository.getTransferAmount()));

      assertEquals(repository.getResultBalanceCard2(),
              DataHelper.calculationPlusBalance(repository.getStartingBalanceCard2(), repository.getTransferAmount()));
    }
    if (card == 2 ) {
      assertEquals(repository.getResultBalanceCard1(),
              DataHelper.calculationPlusBalance(repository.getStartingBalanceCard1(), repository.getTransferAmount()));

      assertEquals(repository.getResultBalanceCard2(),
              DataHelper.calculationMinusBalance(repository.getStartingBalanceCard2(), repository.getTransferAmount()));
    }
  }

  @Когда("^пользователь переводит \"([^\"]*)\" рублей с карты \"([^\"]*)\" на карту \"([^\"]*)\"$")
  public void transferMoneyCardToCard(String amount, int card, int card2) {
    val authInfo = new DataHelper.AuthInfo(amount);
    val dashboardPage = (DashboardPage) scenario.getCurrentPage().appeared();
    if (card == 1) {
      scenario.setCurrentPage(dashboardPage.replenishmentCard2());
    }
    else {
      scenario.setCurrentPage(dashboardPage.replenishmentCard1());
    }

    val cardReplenishment = (CardReplenishment) scenario.getCurrentPage().appeared();

    if (card == 1 & card !=card2 || card == 2 & card == card2 ) {
      scenario.setCurrentPage(cardReplenishment.transferMoneyCard1ToCard2(authInfo));
    }
    else {
      scenario.setCurrentPage(cardReplenishment.transferMoneyCard2ToCard1(authInfo));
    }
  }

  @Когда("пользователь переводит с карты 2 на карту 1 туже сумму")
  public void transferMoneyCardToCardRandomAmount() {
    transferMoneyCardToCard(repository.getTransferAmount(), 2, 1);
  }

  @Тогда("^баланс \"([^\"]*)\" карты должен стать \"([^\"]*)\" рублей$")
  public void chekBalanceCard1(int card, String balance) {
    val dashboardPage = (DashboardPage) scenario.getCurrentPage().appeared();
    if (card == 1) {
      assertEquals(balance, dashboardPage.getBalanceCard1());
    }
    if (card == 2) {
      assertEquals(balance, dashboardPage.getBalanceCard2());
    }
  }
}


