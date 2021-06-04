package sample.enums;

public enum InsuranceServiceValues {
    OSAGO, CASCO, OSAGO_CASKO, UNKNOWN;

    public static String getValue(InsuranceServiceValues value) {
        switch (value) {
            case OSAGO -> {
                return "ОСАГО";
            }
            case CASCO -> {
                return "КАСКО";
            }
            case OSAGO_CASKO -> {
                return "ОСАГО И КАСКО";
            }
            default -> {
                return "unknown";
            }
        }
    }

    public static int getDbKey(String value) {
        switch (value) {
            case "ОСАГО" -> {
                return 1;
            }
            case "КАСКО" -> {
                return 2;
            }
            case "ОСАГО И КАСКО" -> {
                return 3;
            }
            default -> {
                return 0;
            }
        }
    }

    public static String getDbValue(int id) {
        switch (id) {
            case 1 -> {
                return "ОСАГО";
            }
            case 2 -> {
                return "КАСКО";
            }
            case 3 -> {
                return "ОСАГО И КАСКО";
            }
            default -> {
                return "";
            }
        }
    }
}
