package com.zthx.ipc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类名称: ServiceId.java
 * <p>
 * 类描述: 通过反射获得Class上的ServiceId即可记录**客户端注册的服务表**
 * <p>
 * Company: 江苏智体互享科技有限公司
 * <p>
 *
 * @author darryrzhong
 * @since 2019/11/12 9:55
 */

// 标识目标类型
@Target(ElementType.TYPE)
// 运行时注解,需要给反射使用
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceId
{
    String value();
}
