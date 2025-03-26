package org.example.common;

import java.util.Calendar;

public class Constants {
    // Giới hạn cho Tên
    public static final int MAX_NAME_LENGTH = 100;

    //Giới hạn cho tiêu đề
    public static final int MAX_TITLE_LENGTH = 200;

    // Giới hạn cho số điện thoại
    public static final int MIN_PHONE_NUMBER = 8;
    public static final int MAX_PHONE_NUMBER = 11;

    // Giới hạn cho Địa chỉ
    public static final int MAX_ADDRESS_LENGTH = 300;

    //Giới hạn cho ngày publish
    public static final int MIN_PUBLISH_DATE = 1000;
    public static final int MAX_PUBLISH_DATE = Calendar.getInstance().get(Calendar.YEAR);
}
