package utils;

import java.util.Date;

public class SuperMegaLogger {

    public static void log(String Who, String What, String From){
        String When = (new Date()).toString();
        LogsHibernateUtil.storeLog(Who, When, What, From);
        System.out.println(Who);
        System.out.println(What);
        System.out.println(When);
        System.out.println(From);
    }

}
