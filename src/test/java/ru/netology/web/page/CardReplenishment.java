package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.RandomAmount;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CardReplenishment {
    private static SelenideElement amount = $("[data-test-id='amount'] input");
    private static SelenideElement from = $("[data-test-id='from'] input");
    private static SelenideElement replenish = $("[data-test-id='action-transfer']");
    private static SelenideElement error = $("[data-test-id='error-notification']");

    private static void deleteStringAndPasteText(SelenideElement element, String text) {
        String deleteString = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
        element.setValue(deleteString).setValue(text);
    }

    private static void fillForm(String randomAmount, String card) {
        deleteStringAndPasteText(amount, randomAmount);
        deleteStringAndPasteText(from, card);
        replenish.click();
    }

    private static String calculationPlusBalance(String balancePage, String randomAmount) {
        int result = Integer.parseInt(balancePage) + Integer.parseInt(randomAmount);
        return String.valueOf(result);
    }

    private static String calculationMinusBalance(String balancePage, String randomAmount) {
        int result = Integer.parseInt(balancePage) - Integer.parseInt(randomAmount);
        return String.valueOf(result);
    }

    public static DashboardPage transferMoneyCardToCardAndCheckBalance(DataHelper.AuthInfo info) {
        String randomAmount = RandomAmount.randomAmount();
        String resultBalanceCard1;
        String resultBalanceCard2;
        resultBalanceCard1 = calculationPlusBalance(DashboardPage.checkBalanceCard1(), randomAmount);
        resultBalanceCard2 = calculationMinusBalance(DashboardPage.checkBalanceCard2(), randomAmount);
        DashboardPage.replenishmentCard1();
        fillForm(randomAmount, info.getCard2());
        assert resultBalanceCard1.equalsIgnoreCase(DashboardPage.checkBalanceCard1());
        assert resultBalanceCard2.equalsIgnoreCase(DashboardPage.checkBalanceCard2());
        DashboardPage.replenishmentCard2();
        fillForm(randomAmount, info.getCard1());
        return new DashboardPage();
    }

    public static void failedNumberCard(DataHelper.AuthInfo info) {
        String randomAmount = RandomAmount.randomAmount();
        DashboardPage.replenishmentCard1();
        fillForm(randomAmount, info.getCard2().substring(3, 17));
        error.shouldBe(visible);
    }

    public static void goingBeyondAmount(DataHelper.AuthInfo info) {
        String randomAmount = RandomAmount.randomAmount(10001, 1000000);
        DashboardPage.replenishmentCard1();
        fillForm(randomAmount, info.getCard2());
        error.shouldBe(visible);
    }
}
