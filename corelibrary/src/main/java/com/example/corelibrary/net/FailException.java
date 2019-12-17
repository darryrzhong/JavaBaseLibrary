package com.example.corelibrary.net;

import java.io.IOException;

/**
 * 类名称: FailException.java
 * <p>
 * 类描述: 服务端返回的错误信息
 * <p>
 *
 * @author darryrzhong
 * @since 2019/8/29 17:14
 */
public class FailException extends IOException
{
    
    public FailException()
    {
        super();
    }
    
    public FailException(String errorMessage)
    {
        super(errorMessage);
    }
}
