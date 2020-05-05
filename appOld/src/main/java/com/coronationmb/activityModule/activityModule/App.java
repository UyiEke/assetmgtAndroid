package com.coronationmb.activityModule.activityModule;


import android.app.Application;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by UEke on 11/12/2018.
 */

public class App extends Application {
    private LogOutListener listener;
    Timer timer;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public void startUserSession() {
         cancelTimer();

         timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            listener.onSessionLogout();
            }
        },200000);
    }

    private void cancelTimer(){
        if(timer!=null) {
            timer.cancel();
        }
    }

    public void registerSessionListener(LogOutListener listener) {
        this.listener=listener;
    }

    public void onUserInteraction() {
        startUserSession();
    }
}
