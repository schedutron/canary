package com.kartik.canary;

/**
 * Created by kartik on Sun, 18/3/18 in android_app.
 */

public class SetupData {

    private static String positiveResponse;
    private static String negativeResponse;

    private static String location;
    private static String phoneNumber;

    public static String getPositiveResponse() {
        return positiveResponse;
    }

    public static void setPositiveResponse(String text) {
        positiveResponse = text;
    }

    public static String getNegativeResponse() {
        return negativeResponse;
    }

    public static void setNegativeResponse(String text) {
        negativeResponse = text;
    }

    public static String getLocation() {
        return location;
    }

    public static void setLocation(String loc) {
        location = loc;
    }

    public static String getPhoneNumber() {
        return phoneNumber;
    }

    public static void setPhoneNumber(String number) {
        phoneNumber = number;
    }

}
