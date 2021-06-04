package sample.enums;

import java.util.Random;

public enum PriceValues {
    PRICE_5000, PRICE_7500, PRICE_10000, UNKNOWN_PRICE;

    public static Integer getValue(PriceValues price) {
        switch (price) {
            case PRICE_5000 -> {
                return 5000;
            }
            case PRICE_7500 -> {
                return 7500;
            }
            case PRICE_10000 -> {
                return 10000;
            }
            default -> {
                return 0;
            }
        }
    }

    public static int getDbKey(Integer price) {
        switch (price) {
            case 5000 -> {
                return 1;
            }
            case 7500 -> {
                return 2;
            }
            case 10000 -> {
                return 3;
            }
            default -> {
                return 0;
            }
        }
    }

    public static String getDbValue(Integer id) {
        switch (id) {
            case 1 -> {
                return "5000";
            }
            case 2 -> {
                return "7500";
            }
            case 3 -> {
                return "10000";
            }
            default -> {
                return "0";
            }
        }
    }
}
