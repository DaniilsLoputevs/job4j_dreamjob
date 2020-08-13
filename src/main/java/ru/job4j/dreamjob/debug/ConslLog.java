package ru.job4j.dreamjob.debug;

public class ConslLog {

    public static void log(String desc, Object object) {
        System.out.println("ConslLog: " + desc + ": " + object);
    }
    public static void log(String desc) {
        System.out.println("ConslLog: " + desc);
    }


    public static void main(String[] args) {
        class Ext {
            @Override
            public String toString() {
                return "Ext.toString()";
            }
        }

        log("ext", new Ext());
    }
}
