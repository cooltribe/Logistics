/**
 * 
 */
package com.seeyuan.logistics.util;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * 用于Date的格式化。它默认将日期格式化为 yyyy-MM-dd HH:mm:ss 格式的字符串，同时允许用户指定日期格式以覆盖默认行为
  * @类名: DateSerializer.java
  * @描述: xxx
  * @作者: wangjinkui
  * @日期: 2014年8月22日 上午8:54:58
  * @版本:  V1.0
  * ============================================================================
  * 版权所有 2010-2014 江苏辉源供应链管理有限公司（SEARUN）,并保留所有权利。
  * ----------------------------------------------------------------------------
  * 提示：未经许可不能随意拷贝复制使用本软件，否则SEARUN将保留追究的权力。
  * ----------------------------------------------------------------------------
  * 官方网站：http://www.sy56.com
  * ============================================================================
 */
public class DateSerializer implements JsonSerializer<Date> {

    public static final String DefaultPattern = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式转化的样式字符串
     */
    private String dateFormat;
    
    private SimpleDateFormat simpleDateFormat;
    
    public String getDateFormat() {
        return this.dateFormat;
    }
    
    /**
     * 默认构造器
     */
    public DateSerializer() {
        this(DateSerializer.DefaultPattern);
    }

    /**
     * 指定格式的构造函数
     * @param patterm
     */
    public DateSerializer(String dateFormat) {
        super();
        this.dateFormat = dateFormat;
        this.simpleDateFormat = new SimpleDateFormat(this.dateFormat);
    }
    
    @Override
    public JsonElement serialize(Date date, Type type, JsonSerializationContext context) {
        String value = null;
        if (date == null) {
            value = "";
        } else {
            value = this.simpleDateFormat.format(date);
        }
        
        return new JsonPrimitive(value);
    }

}
