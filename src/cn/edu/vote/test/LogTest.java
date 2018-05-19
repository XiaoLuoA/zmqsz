package cn.edu.vote.test;

import org.apache.log4j.Logger;

/**
 * @author zzu
 */
public class LogTest {

    public static void main(String[] args) {
        Logger logger = Logger.getLogger("test");
        logger.info("this is info");
        logger.warn("this is warn");
        logger.debug("this is debug");
        logger.error("this is error");
        int a = 1, b = 0;
        try {
            System.out.println(a / b);
        } catch (Exception e) {
            logger.error(e);
            logger.error(e.getStackTrace());
        }
    }
}
