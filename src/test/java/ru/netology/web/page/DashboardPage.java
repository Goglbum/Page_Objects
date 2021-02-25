package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
  private SelenideElement heading = $("h1");
  private static SelenideElement card1 = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']");
  private static SelenideElement card2 = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']");
  private static SelenideElement card1Button = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] [data-test-id='action-deposit']");
  private static SelenideElement card2Button = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] [data-test-id='action-deposit']");

  public DashboardPage() {
    assert heading.getText().equalsIgnoreCase("Ваши карты");
  }

  public static CardReplenishment replenishmentCard1() {
    card1Button.click();
    return new CardReplenishment();
  }

  public static CardReplenishment replenishmentCard2() {
    card2Button.click();
    return new CardReplenishment();
  }

  public static String checkBalance(SelenideElement element) {
    String text = element.getText();
    int a = text.indexOf(":") + 2;
    int b = text.indexOf("р.") - 1;
    return text.substring(a, b);
  }

  public static String checkBalanceCard1() {
    return checkBalance(card1);
  }

  public static String checkBalanceCard2() {
    return checkBalance(card2);
  }
}
