package br.com.fydelis.netbeans.project.utils;

public class TemplateHelper {

    public static String gerarArquivoPro(String nomeProjeto) {
        return String.format(
            "QT += core gui\n" +
            "QT += widgets\n" +
            "\n" +
            "TARGET = %s\n" +
            "TEMPLATE = app\n" +
            "\n" +
            "SOURCES += \\\n" +
            "    src/main.cpp \\\n" +
            "    src/mainwindow.cpp\n" +
            "\n" +
            "HEADERS += \\\n" +
            "    include/mainwindow.h\n" +
            "\n" +
            "RESOURCES += \\\n" +
            "    resources/resources.qrc\n",
            nomeProjeto
        );
    }
}