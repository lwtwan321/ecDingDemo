package com.example.ecdingdemo;

import android.app.Application;

import com.huawei.tup.TUPInterfaceService;


public class EcApplication extends Application {
    private TUPInterfaceService tupInterfaceService;

    private static Application app;

    public static Application getApplication() {
        return app;
    }

    private void setApplication(Application application) {
        app = application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setApplication(this);
        tupInterfaceService = new TUPInterfaceService();
        tupInterfaceService.StartUpService();
        tupInterfaceService.SetAppPath(getApplicationInfo().dataDir + "/lib");
        LoginService.getInstance().init(tupInterfaceService);
    }

    @Override
    public void onTerminate() {
        if (tupInterfaceService != null) {
            tupInterfaceService.ShutDownService();
        }
        super.onTerminate();
    }
}
