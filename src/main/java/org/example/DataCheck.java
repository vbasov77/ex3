package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DataCheck {
    public static int dataCount = 6;

    private String firstName;
    private String lastName;
    private String patronymic;
    private LocalDate birthDate;
    private int phone;
    private Gender gender;

    public DataCheck() {
    }

    public void CheckData(String[] splitedString) throws ParseInputDataException {
        if (splitedString == null) {
            throw new NullPointerException("Нет данных");
        }

        StringBuilder fullErrorsMessages = new StringBuilder();
        for (String string : splitedString) {
            if (Character.isLetter(string.charAt(0))) {
                if (string.length() == 1) {
                    if (this.gender == null) {
                        try {
                            this.gender = checkGender(string);
                        } catch (GenderException e) {
                            fullErrorsMessages.append(e.getMessage());
                        }
                    } else {
                        fullErrorsMessages.append("Гендер указан больше 1 раза!\n");
                    }
                } else {
                    if (this.lastName == null) {
                        try {
                            this.lastName = checkFullName(string);
                        } catch (FullNameException e) {
                            fullErrorsMessages.append(e.getMessage());
                        }
                    } else if (this.firstName == null) {
                        try {
                            this.firstName = checkFullName(string);
                        } catch (FullNameException e) {
                            fullErrorsMessages.append(e.getMessage());
                        }
                    } else if (this.patronymic == null) {
                        try {
                            this.patronymic = checkFullName(string);
                        } catch (FullNameException e) {
                            fullErrorsMessages.append(e.getMessage());
                        }
                    } else {
                        fullErrorsMessages.append("Слишком много элементов распознаны как ФИО!\n");
                    }
                }
            } else {

                if (string.matches("[0-9]{2}\\.[0-9]{2}\\.[0-9]{4}")) {
                    if (this.birthDate == null) {
                        try {
                            this.birthDate = checkBirthDate(string);
                        } catch (BirthDateException e) {
                            fullErrorsMessages.append(e.getMessage());
                        }
                    } else {
                        fullErrorsMessages.append("Дата рождения должна быть только одна, а обнаружены две: '"
                                + this.birthDate + "','" + string + "'\n");
                    }
                } else {
                    if (this.phone == 0) {
                        try {
                            this.phone = (int) checkPhone(string);
                        } catch (PhoneException e) {
                            fullErrorsMessages.append(e.getMessage());
                        }
                    } else {
                        fullErrorsMessages.append("Должен быть только один телефонный нормер, а не несколько: '"
                                + this.phone + "','" + string + "'\n");
                    }
                }

            }
        }
        if (!fullErrorsMessages.isEmpty()) {
            throw new ParseInputDataException(fullErrorsMessages.toString());
        }
    }

    public String getLastName() {
        return lastName;
    }

    private String checkFullName(String inputString) throws FullNameException {
        if (inputString.toLowerCase().matches("^[a-zа-яё]*$")) {
            return inputString;
        } else {
            throw new FullNameException(inputString);
        }
    }

    private long checkPhone(String inputString) throws PhoneException {
        if (inputString.length() == 10) {
            try {
                return Long.parseLong(inputString);
            } catch (NumberFormatException e) {
                throw new PhoneException(inputString);
            }
        } else {
            throw new PhoneException(inputString);
        }
    }

    private Gender checkGender(String inputString) throws GenderException {
        try {
            return Gender.valueOf(inputString);
        } catch (IllegalArgumentException e) {
            throw new GenderException(inputString);
        }
    }

    private LocalDate checkBirthDate(String inputString) throws BirthDateException {
        try {
            return LocalDate.parse(inputString,
                    DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (DateTimeParseException e) {
            throw new BirthDateException(inputString);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<").append(lastName).append(">")
                .append("<").append(firstName).append(">")
                .append("<").append(patronymic).append(">")
                .append("<").append(birthDate.toString()).append(">")
                .append("<").append(phone).append(">")
                .append("<").append(gender).append(">");
        return sb.toString();
    }


}
