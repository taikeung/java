package com.fid.common.multiDataSource;

 
/**
 * @ClassName DynamicDataSourceHolder
 * @Description 切换数据源实现类
 * @author hudaqiang
 * @Date 2017年6月8日 下午5:59:50
 * @version 1.0.0
 */
public class DynamicDataSourceHolder {  
  
    private static final ThreadLocal<String> local = new ThreadLocal<String>();  
      
    /** 
     * 清空当前数据源 
     */  
    public static void clear(){  
        local.remove();  
    }  
      
    /** 
     * 获取当前数据源 
     */  
    public static String get(){  
        return local.get();  
    }  
      
    /** 
     * 设置当前数据源 
     */  
    public static void set(String source){  
        local.set(source);  
    }  
      
}  