package com.example.ecdingdemo;

import android.os.Environment;
import android.util.Log;

import com.huawei.tup.TUPInterfaceService;
import com.huawei.tup.login.LoginAuthInfo;
import com.huawei.tup.login.LoginAuthServerInfo;
import com.huawei.tup.login.LoginAuthType;
import com.huawei.tup.login.LoginAuthorizeParam;
import com.huawei.tup.login.LoginAuthorizeResult;
import com.huawei.tup.login.LoginLogLevel;
import com.huawei.tup.login.LoginServerType;
import com.huawei.tup.login.LoginSmcAuthorizeResult;
import com.huawei.tup.login.LoginUportalAuthorizeResult;
import com.huawei.tup.login.LoginVerifyMode;
import com.huawei.tup.login.sdk.TupLoginManager;
import com.huawei.tup.login.sdk.TupLoginNotifyBase;
import com.huawei.tup.login.sdk.TupLoginOptResult;

import java.io.File;

public class LoginService extends TupLoginNotifyBase {
    private static LoginService ins;
    private TupLoginManager tupLoginManager;

    public LoginService() {
    }

    public synchronized static LoginService getInstance() {
        if (ins == null) {
            ins = new LoginService();
        }
        return ins;
    }

    public int init(TUPInterfaceService tupInterfaceService) {
        tupLoginManager = TupLoginManager.getIns(this, EcApplication.getApplication());
        String path = Environment.getExternalStorageDirectory() + File.separator + "ECTEST";
        int fileCount = 1;
        int maxLogSize = 5120;
        tupLoginManager.setLogParam(LoginLogLevel.LOGIN_E_LOG_INFO, maxLogSize, fileCount, path);
        tupLoginManager.setVerifyMode(LoginVerifyMode.LOGIN_E_VERIFY_MODE_NONE);
        int resutl = tupLoginManager.loginInit(tupInterfaceService);
        if (resutl != 0) {
            Log.i("djh", "login init fail");
        }
        return resutl;
    }

    @Override
    public void onAuthorizeResult(int i, TupLoginOptResult tupLoginOptResult, LoginAuthorizeResult loginAuthorizeResult) {
        super.onAuthorizeResult(i, tupLoginOptResult, loginAuthorizeResult);
    }

    @Override
    public void onAuthorizeResult(int i, TupLoginOptResult tupLoginOptResult, LoginSmcAuthorizeResult loginSmcAuthorizeResult) {
        super.onAuthorizeResult(i, tupLoginOptResult, loginSmcAuthorizeResult);
    }

    @Override
    public void onAuthorizeResult(int i, TupLoginOptResult tupLoginOptResult, LoginUportalAuthorizeResult loginUportalAuthorizeResult) {
        super.onAuthorizeResult(i, tupLoginOptResult, loginUportalAuthorizeResult);
        if (tupLoginOptResult.getOptResult()==0){
            Log.i("djh","鉴权登入成功");
        }
    }

    public boolean authorizeLogin(LoginParam loginParam){
        String regServerUrl = loginParam.getServerUrl();
        String proxyServerUrl = loginParam.getProxyUrl();
        String userName = loginParam.getUserName();
        String password = loginParam.getPassword();
        int serverPort = loginParam.getServerPort();

        LoginAuthInfo authInfo = new LoginAuthInfo();
        authInfo.setUserName(userName);
        authInfo.setPassword(password);

        LoginAuthServerInfo serverInfo = new LoginAuthServerInfo();
        serverInfo.setServerType(LoginServerType.LOGIN_E_SERVER_TYPE_PORTAL);
        serverInfo.setServerUrl(regServerUrl);
        serverInfo.setServerPort(serverPort);
        serverInfo.setServerVersion("");
        serverInfo.setProxyUrl(proxyServerUrl);
        serverInfo.setProxyPort(serverPort);

        LoginAuthorizeParam authorizeParam = new LoginAuthorizeParam();
        authorizeParam.setAuthType(LoginAuthType.LOGIN_E_AUTH_NORMAL);
        authorizeParam.setAuthInfo(authInfo);
        authorizeParam.setAuthServer(serverInfo);
        authorizeParam.setUserAgent("SoftClient on Mobile");
        authorizeParam.setUserTiket("");

        int uportalLoginResult = tupLoginManager.authorize(authorizeParam);
        Log.i("djh", "uportalLoginResult->" + uportalLoginResult);
        return uportalLoginResult == 0;

    }


}
