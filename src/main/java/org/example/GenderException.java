package org.example;

public class GenderException extends Exception {
    String data;

    public GenderException(String inputData) {
        this.data = inputData;
    }

    @Override
    public String getMessage() {
        return "Неправильно указан пол: '" + data + "'!\n" +
                "Формат ввода пола: m или f.";
    }
}
