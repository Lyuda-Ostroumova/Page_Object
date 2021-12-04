package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ru.netology.data.DataHelper;
import ru.netology.data.DataHelper.*;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.MoneyTransferPage;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selectors.withText;


public class MoneyTransferTest {

    @BeforeEach
    void setUp() {
        Configuration.headless = true;
        open("http://localhost:9999");
        LoginPage loginPage = new LoginPage();
        AuthInfo authInfo = DataHelper.getAuthInfo();
        VerificationPage verificationPage = loginPage.validLogin(authInfo);
        VerificationCode verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

    }

    @Test
    public void shouldTransferMoneyFromFirstToSecond() {
        int amount = 700;
        CardData firstCardData = CardData.getFirstCardData();
        CardData secondCardData = CardData.getSecondCardData();
        int firstCardBalanceBeforeTransfer = DashboardPage.getFirstCardCurrentBalance();
        int secondCardCurrentBalanceBeforeTransfer = DashboardPage.getSecondCardCurrentBalance();
        DashboardPage.secondCard();
        MoneyTransferPage.moneyTransfer(firstCardData, amount);
        int firstCardBalanceAfterTransfer = DataHelper.balanceCardTransferFrom(firstCardBalanceBeforeTransfer, amount);
        int secondCArdBalanceAfterTransfer = DataHelper.balanceCardTransferTo(secondCardCurrentBalanceBeforeTransfer, amount);
        int firstCardNewBalance = DashboardPage.getFirstCardCurrentBalance();
        int secondCArdNewBalance = DashboardPage.getSecondCardCurrentBalance();
        assertEquals(firstCardBalanceAfterTransfer, firstCardNewBalance);
        assertEquals(secondCArdBalanceAfterTransfer, secondCArdNewBalance);


    }

    @Test
    public void shouldTransferMoneyFromSecondToFirst() {
        int amount = 400;
        int firstCardBalanceBeforeTransfer = DashboardPage.getFirstCardCurrentBalance();
        int secondCardCurrentBalanceBeforeTransfer = DashboardPage.getSecondCardCurrentBalance();
        DashboardPage.firstCard();
        CardData secondCardData = CardData.getSecondCardData();
        MoneyTransferPage.moneyTransfer(secondCardData, amount);
        int firstCardBalanceAfterTransfer = DataHelper.balanceCardTransferTo(firstCardBalanceBeforeTransfer, amount);
        int secondCArdBalanceAfterTransfer = DataHelper.balanceCardTransferFrom(secondCardCurrentBalanceBeforeTransfer, amount);
        int firstCardNewBalance = DashboardPage.getFirstCardCurrentBalance();
        int secondCArdNewBalance = DashboardPage.getSecondCardCurrentBalance();
        assertEquals(firstCardBalanceAfterTransfer, firstCardNewBalance);
        assertEquals(secondCArdBalanceAfterTransfer, secondCArdNewBalance);


    }

    @Test
    public void shouldNotTransferIfNotEnoughMoney() {
        int amount = 50_000;
        CardData firstCardData = CardData.getFirstCardData();
        CardData secondCardData = CardData.getSecondCardData();
        int firstCardBalanceBeforeTransfer = DashboardPage.getFirstCardCurrentBalance();
        int secondCardCurrentBalanceBeforeTransfer = DashboardPage.getSecondCardCurrentBalance();
        DashboardPage.secondCard();
        MoneyTransferPage.moneyTransfer(firstCardData, amount);
        $(withText("Недостаточно средств для совершения перевода!")).shouldBe(Condition.visible);

    }

}


