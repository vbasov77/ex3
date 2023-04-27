package org.example;

public class Main {
    public static void main(String[] args) {
        DisplayView<View> myprog = new DisplayView<>(new DataView());
        myprog.start();
    }
}
