package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import ru.netology.data.DataHelper.*;

import static com.codeborne.selenide.Selenide.$;

public class MoneyTransferPage {

    private static SelenideElement sumField = $("[data-test-id=amount] input");
    private static SelenideElement cardField = $("[data-test-id='from'] input");
    private static SelenideElement transferButton = $("[data-test-id=action-transfer]");


    public static void moneyTransfer(CardData cardData, int amount) {
        sumField.setValue(String.valueOf(amount));
        cardField.setValue(cardData.getCardNumber());
        transferButton.click();
    }

}


