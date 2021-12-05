package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import ru.netology.data.DataHelper.*;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;


public class MoneyTransferPage {

    private SelenideElement sumField = $("[data-test-id=amount] input");
    private SelenideElement cardField = $("[data-test-id='from'] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement notEnoughMoneyError = $(withText("Недостаточно средств для совершения перевода!"));

    public void moneyTransfer(CardData cardData, int amount) {
        sumField.setValue(String.valueOf(amount));
        cardField.setValue(cardData.getCardNumber());
        transferButton.click();
    }

    public void notEnoughMoneyError(CardData cardData, int amount) {
        sumField.setValue(String.valueOf(amount));
        cardField.setValue(cardData.getCardNumber());
        transferButton.click();
        if (amount > Integer.parseInt(cardData.getCardBalance())) {
            notEnoughMoneyError.shouldBe(Condition.visible);
        }

    }

}
