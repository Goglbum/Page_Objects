package ru.netology.web.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;
import ru.alfabank.alfatest.cucumber.annotations.Name;
import ru.alfabank.alfatest.cucumber.annotations.Optional;
import ru.alfabank.alfatest.cucumber.api.AkitaPage;
import ru.netology.web.data.DataHelper;

@Name("Перевод")
public class CardReplenishment extends AkitaPage {
    @Name("поле ввода суммы")
    @FindBy (css = "[data-test-id='amount'] input")
    private SelenideElement amountField;

    @Name("поле ввода номера карты")
    @FindBy (css = "[data-test-id='from'] input")
    private SelenideElement from;

    @Name("кнопка перевода")
    @FindBy (css = "[data-test-id='action-transfer']")
    private SelenideElement replenish;

    @Optional
    @Name("сообщение об ошибке")
    @FindBy (css = "[data-test-id='error-notification']")
    private SelenideElement error;

    private static void deleteStringAndPasteText(SelenideElement element, String text) {
        String deleteString = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
        element.setValue(deleteString).setValue(text);
    }

    private void fillForm(String randomAmount, String card) {
        deleteStringAndPasteText(amountField, randomAmount);
        deleteStringAndPasteText(from, card);
        replenish.click();
    }

    public DashboardPage transferMoneyCard2ToCard1(DataHelper.AuthInfo info) {
        fillForm(info.getAmount(), info.getCard2());
        return Selenide.page(DashboardPage.class);
    }

    public DashboardPage transferMoneyCard1ToCard2(DataHelper.AuthInfo info) {
        fillForm(info.getAmount(), info.getCard1());
        return Selenide.page(DashboardPage.class);
    }
}
