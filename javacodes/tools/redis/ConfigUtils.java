package com.andy.util.redis;

import java.util.Properties;

/**
 * @Description:获取配置文件中的键值对
 * @Author: Andy Hoo
 * @Date: 2017/10/14 13:09
 */
public class ConfigUtils {
        private static java.util.Properties property;
        private ConfigUtils() {
        }
        public static void init(Properties props) {
            property = props;
        }

        public static String getProperty(String key) {
            return property.getProperty(key);
        }

        public String getProperty(String key, String defaultValue) {
            return property.getProperty(key, defaultValue);

        }

}
