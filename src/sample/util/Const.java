package sample.util;

import java.util.Locale;

public class Const {
    public static final String EMPTY_FIELD = "";

    /*   WORDS   */
    public static final String titlePageHome = "аис Страхового агентства";
    public static final String registration = "Регистрация";
    public static final String titleDetailClient = "информация о клиенте";
    public static final String titleCarDriver = "Водитель";
    public static final String titleInsuranceEvent = "Страховой случай";
    public static final String titleStatistic = "Статистика";

    /*   PATHS   */
    public static final String pathSceneHome = "/sample/scenes/home.fxml";
    public static final String pathSceneRegistrationClient = "/sample/scenes/client.fxml";
    public static final String pathSceneRegistrationCar = "/sample/scenes/car.fxml";
    public static final String pathSceneRegistrationPolicy = "/sample/scenes/policy.fxml";
    public static final String pathSceneDetailClient = "/sample/scenes/detail_client.fxml";
    public static final String pathSceneDetailCarDriver = "/sample/scenes/car_driver.fxml";
    public static final String pathSceneDetailInsuranceEvent = "/sample/scenes/insurance_event.fxml";
    public static final String pathSceneStatistic = "/sample/scenes/statistic.fxml";

    /*   WINDOW   */
    public final static int applicationWidth = 1108;
    public final static int applicationHeight = 552;
    public static final int registrationWindowWidth = 450;
    public static final int registrationWindowHeight = 550;
    public static final int detailClientWidth = 450;
    public static final int detailClientHeight = 550;
    public static final int carDriverWidth = 400;
    public static final int carDriverHeight = 210;
    public static final int insuranceEventWidth = 400;
    public static final int insuranceEventHeight = 210;
    public static final int statisticWidth = 350;
    public static final int statisticHeight = 194;

    /*   SOME FILES   */
    public static final String pathDefaultImage = "/resources/img-profile.jpg";

    /*   HINT   */
    public static final String surnameHint = "Фамилия";
    public static final String nameHint = "имя";
    public static final String middleNameHint = "Отчество";
    public static final String dateOfBirthHint = "Дата рождения: 01.01.2000";
    public static final String passportSeriesHint = "Серия паспорта";
    public static final String passportNumberHint = "Номер паспорта";
    public static final String addressHint = "Адрес проживания";

    public static final String vinHint = "VIN";
    public static final String brandHint = "Марка, модель";
    public static final String yearReleaseHint = "Год выпуска";
    public static final String registrationPlateHint = "Регистрационный знак";
    public static final String powerHint = "Мощность";

    public static final String driverSurnameHint = "Фамилия";
    public static final String driverNameHint = "имя";
    public static final String driverMiddleNameHint = "Отчество";
    public static final String driverSeriesHint = "Серия водительского удостоверения";
    public static final String driverNumberHint = "Номер водительского удостоверения";

    public static final String contractHint = "Номер договора";
    public static final String dateContractHint = "Дата заключения договора";

    public static final String seriesPolicyHint = "Серия полиса";
    public static final String numberPolicyHint = "Номер полиса";
    public static final String startPolicyHint = "Начало страхования";
    public static final String endPolicyHint = "Конец страхования";
    public static final String pricePolicy = "Цена составления полиса";
    public static final String amountPolicy = "Сумма страхования";
    public static final String servicePolicy = "Сервис";
    public static final String periodPolicy = "Период страхования";

    public static final String tableHint = "Пусто!";

    public static final String accidentNumberHint = "Номер аварии";
    public static final String dateAccidentNumberHint = "Дата";
    public static final String descriptionAccidentNumberHint = "Описание";
    public static final String amountPaymentAccidentNumberHint = "Сумма выплаты";

    /*   DIALOG   */
    public static final String dialogError = "Ошибка!";
    public static final String dialogEmptyFields = "Необходимо заполнить все поля!";
    public static final String dialogLimitCarDriver = "Превышен лимит водителей!";

    /*   RESPONSE   */
    public static final String success = "ok";

    /*   ERRORS   */
    public static final String incorrectFormat = "Неправильный формат ввода в поле %s";
    public static final String characterLimitExceeded = "Превышен лимит символов в поле %s";
    public static final String incorrectFormatDate = "Неправильный формат ввода даты в поле %s";

    /*   FIELDS   */
    public static final String surnameField = "Фамилия (50)";
    public static final String nameField = "имя (50)";
    public static final String middleNameField = "Отчество (50)";
    public static final String dateOfBirthField = "Дата рождения: 01.01.2000";
    public static final String passportSeriesField = "Серия паспорта (4)";
    public static final String passportNumberField = "Номер паспорта (6)";
    public static final String addressField = "Адрес проживания (150)";

    public static final String brandField = "Модель, марка (50)";
    public static final String yearReleaseField = "Год выпуска: 01.01.2000";
    public static final String registrationPlateField = "А000АА152 (8,9)";
    public static final String powerField = "Мощность (5)";

    public static final String contractField = "Номер контракта (10)";
    public static final String dateContractField = "Дата заключение договора: 01.01.2000";

    public static final String vinField = "VIN (17)";
    public static final String seriesPolicyField = "Серия полиса (3)";
    public static final String numberPolicyField = "Номер полиса (10)";
    public static final String startPolicyField = "Начал страхования: 01.01.2000";
    public static final String endPolicyField = "Конец страхования: 01.01.2000";
    public static final String pricePolicyField = "Цена составления полиса (5)";
    public static final String amountPolicyField = "Сумма страхования (10)";
    public static final String servicePolicyField = "Сервис (13)";
    public static final String periodPolicyField = "Период страхования: 01.01.2000";
}
