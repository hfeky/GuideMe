package com.guideme.guideme;

import android.app.Application;

import com.instabug.library.Instabug;
import com.instabug.library.invocation.InstabugInvocationEvent;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new Instabug.Builder(this, "595d2f1c6982cc162ef3ca613d6d9bad")
                .setInvocationEvents(InstabugInvocationEvent.SHAKE, InstabugInvocationEvent.SCREENSHOT)
                .build();
    }
}
