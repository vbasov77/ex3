package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DisplayView<V extends View> {

    private DataCheck model;
    private V view;

    public DisplayView(V v) {
        view = v;
    }

    public void start() {
        boolean flagWork = true;
        do {
            String input = view.getInputData(
                    "Введите данные через пробел (Фамилию Имя Отчество ДатуРождения НомерТелефона Пол),\n" +
                            " или Exit для прекращения программы:");
            if (input.equals("Exit")) {
                flagWork = false;
                break;
            } else {
                String[] splitedInput = input.replaceAll("\\s+", " ").split(" ");

                int inputDataCount = checkInputDataCount(splitedInput.length);
                if (inputDataCount == -1) {
                    view.printOutputData("Слишком мало данных на вводе (должно быть " + DataCheck.dataCount
                            + " разделённых пробелом ' ': Фамилия Имя Отчество НомерТелефона ДатаРождения Пол)\n");
                } else if (inputDataCount == 0) {
                    view.printOutputData("Слишком много данных на вводе (должно быть " + DataCheck.dataCount
                            + " разделённых пробелом ' ': Фамилия Имя Отчество НомерТелефона ДатаРождения Пол)\n");
                } else {
                    try {
                        model = new DataCheck();
                        model.CheckData(splitedInput);
                        writePersonData(model);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParseInputDataException e) {
                        view.printOutputData(e.getMessage());
                    }
                }
            }
        } while (flagWork);
    }

    private int checkInputDataCount(int inputDataCount) {
        if (inputDataCount < DataCheck.dataCount) {
            return -1;
        } else if (inputDataCount > DataCheck.dataCount) {
            return 0;
        } else {
            return inputDataCount;
        }
    }

    private void writePersonData(DataCheck data) throws IOException {
        File filepath = new File("C:\\Users\\Admin\\Java_projects\\JavaExceptions\\src\\seminar3\\homework\\" + data.getLastName());
        try (FileWriter fw = new FileWriter(filepath, true)) {
            fw.append(data.toString() + "\n");
        } catch (IOException e) {
            throw e;
        }
    }
}