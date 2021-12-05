package ru.netology.test;

import com.codeborne.selenide.Configuration;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

import ru.netology.data.DataHelper;
import ru.netology.data.DataHelper.*;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;


public class MoneyTransferTest {

    DashboardPage dashboardPage;

    @BeforeEach
    void setUp() {
        Configuration.headless = true;
        open("http://localhost:9999", LoginPage.class);
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);

    }

    @Test
    public void shouldTransferMoneyFromFirstToSecond() {
        int amount = 700;
        var firstCardData = CardData.getFirstCardData();
        var secondCardData = CardData.getSecondCardData();
        int firstCardBalanceBeforeTransfer = dashboardPage.getFirstCardCurrentBalance();
        int secondCardCurrentBalanceBeforeTransfer = dashboardPage.getSecondCardCurrentBalance();
        val moneyTransferPage = dashboardPage.secondCard();
        moneyTransferPage.moneyTransfer(firstCardData, amount);
        int firstCardBalanceAfterTransfer = DataHelper.balanceCardTransferFrom(firstCardBalanceBeforeTransfer, amount);
        int secondCArdBalanceAfterTransfer = DataHelper.balanceCardTransferTo(secondCardCurrentBalanceBeforeTransfer, amount);
        int firstCardNewBalance = dashboardPage.getFirstCardCurrentBalance();
        int secondCArdNewBalance = dashboardPage.getSecondCardCurrentBalance();
        assertEquals(firstCardBalanceAfterTransfer, firstCardNewBalance);
        assertEquals(secondCArdBalanceAfterTransfer, secondCArdNewBalance);

    }

    @Test
    public void shouldTransferMoneyFromSecondToFirst() {
        int amount = 400;
        var firstCardBalanceBeforeTransfer = dashboardPage.getFirstCardCurrentBalance();
        var secondCardCurrentBalanceBeforeTransfer = dashboardPage.getSecondCardCurrentBalance();
        val moneyTransferPage = dashboardPage.firstCard();
        CardData secondCardData = CardData.getSecondCardData();
        moneyTransferPage.moneyTransfer(secondCardData, amount);
        int firstCardBalanceAfterTransfer = DataHelper.balanceCardTransferTo(firstCardBalanceBeforeTransfer, amount);
        int secondCArdBalanceAfterTransfer = DataHelper.balanceCardTransferFrom(secondCardCurrentBalanceBeforeTransfer, amount);
        int firstCardNewBalance = dashboardPage.getFirstCardCurrentBalance();
        int secondCArdNewBalance = dashboardPage.getSecondCardCurrentBalance();
        assertEquals(firstCardBalanceAfterTransfer, firstCardNewBalance);
        assertEquals(secondCArdBalanceAfterTransfer, secondCArdNewBalance);


    }

    @Test
    public void shouldNotTransferIfNotEnoughMoney() {
        int amount = 50_000;
        var firstCardData = CardData.getFirstCardData();
        var secondCardData = CardData.getSecondCardData();
        val moneyTransferPage = dashboardPage.secondCard();
        moneyTransferPage.moneyTransfer(firstCardData, amount);
        moneyTransferPage.notEnoughMoneyError(firstCardData, amount);
    }

}


