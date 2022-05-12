package com.bobo.baseframe.app;

/**
 * @ClassName DbConstant
 * @Description 数据库常量
 */
public class DbConstant {

    public static final String DATABASE_NAME = "general.db";

    /**
     * UserDb 表
     */
    public static class UserDb {
        public static final String TAB_NAME = "user",
                ID = "id",
                LOGIN_NAME = "loginName",
                PASSWORD = "password";
    }

    public static class Component {
        public static final String
            UPDATE_TIME = "updateTime";
    }

    /**
     * HistoryDb 表
     */
    public static class HistoryDb {
        public static final String TAB_NAME = "history",
                ID = "id",
                CONTENT = "content";
    }

    /**
     * HistoryDb 表
     */
    public static class HistoryCityDb {
        public static final String TAB_NAME = "historycity",
                ID = "id",
                CITY = "city";
    }
}
