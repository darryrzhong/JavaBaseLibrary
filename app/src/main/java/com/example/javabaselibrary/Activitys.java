package com.example.javabaselibrary;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 应用模块 : <p>
 * 类名称: .java<p>
 * 类描述: <p>
 *
 * @author darryrzhong
 * @since 2019/12/16 16:22
 */
public class Activitys extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
