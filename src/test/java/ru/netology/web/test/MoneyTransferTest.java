package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.*;

import static com.codeborne.selenide.Selenide.open;

class MoneyTransferTest {

  @BeforeAll
  static void setUpAll() {
    Configuration.headless = true;
  }

  @Test
  void transferMoneyCardToCardAndCheckBalance() {
    open("http://localhost:9999");
    val loginPage = new LoginPage();
    val authInfo = DataHelper.getAuthInfo();
    val verificationPage = loginPage.validLogin(authInfo);
    val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    verificationPage.validVerify(verificationCode);
    CardReplenishment.transferMoneyCardToCardAndCheckBalance(authInfo);
  }

  @Test
  void failedNumberCard() {
    open("http://localhost:9999");
    val loginPage = new LoginPage();
    val authInfo = DataHelper.getAuthInfo();
    val verificationPage = loginPage.validLogin(authInfo);
    val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    verificationPage.validVerify(verificationCode);
    CardReplenishment.failedNumberCard(authInfo);
  }

//  @Test
//  void goingBeyondAmount() {
//    open("http://localhost:9999");
//    val loginPage = new LoginPage();
//    val authInfo = DataHelper.getAuthInfo();
//    val verificationPage = loginPage.validLogin(authInfo);
//    val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
//    verificationPage.validVerify(verificationCode);
//    CardReplenishment.goingBeyondAmount(authInfo);
//  }
}

