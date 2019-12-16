package com.example.javabaselibrary;

/**
 * 应用模块: <p>
 * 类名称:<p>
 * 类描述: <p>
 *
 * @author darryrzhong
 * @since 2019/12/15  23:30
 */
public class MainModel implements MainContract.Model {
    @Override
    public String login(String phone, String pass) {
        return phone+" / "+pass;
    }
}
