package ru.netology.data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardData {

        private String cardNumber;
        private String cardBalance;

        public static CardData getFirstCardData() {
            return new CardData("5559000000000001", "1000");
        }

        public static CardData getSecondCardData() {
            return new CardData("5559000000000002", "1000");
        }

    }

    public static int balanceCardTransferFrom(int balance, int amount) {
        int newAmount = balance - amount;
        return newAmount;
    }

    public static int balanceCardTransferTo(int balance, int amount) {
        int newAmount = balance + amount;
        return newAmount;
    }

}