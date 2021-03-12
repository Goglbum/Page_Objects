package ru.netology.web.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.alfabank.alfatest.cucumber.annotations.Name;
import ru.alfabank.alfatest.cucumber.api.AkitaPage;

@Name("Дашбоард")
public class DashboardPage extends AkitaPage {
  @FindBy (css = "h1")
  public SelenideElement heading;

  @FindBy (css ="[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']")
  @Name("информация о карте 1")
  public SelenideElement card1;

  @FindBy (css ="[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']")
  @Name("информация о карте 2")
  public SelenideElement card2;

  @FindBy (css = "[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] button")
  @Name("кнопка перевода на карту 1")
  public SelenideElement card1Button;

  @FindBy (css = "[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] button")
  @Name("кнопка перевода на карту 2")
  public SelenideElement card2Button;

  public CardReplenishment replenishmentCard1() {
    card1Button.click();
    return Selenide.page(CardReplenishment.class);
  }

  public CardReplenishment replenishmentCard2() {
    card2Button.click();
    return Selenide.page(CardReplenishment.class);
  }

  public String getBalance(SelenideElement element) {
    String text = element.getText();
    int a = text.indexOf(":") + 2;
    int b = text.indexOf("р.") - 1;
    return text.substring(a, b);
  }

  public String getBalanceCard1() {
    return getBalance(card1);
  }

  public String getBalanceCard2() {
    return getBalance(card2);
  }
}
