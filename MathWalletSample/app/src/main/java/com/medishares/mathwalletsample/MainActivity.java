package com.medishares.mathwalletsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.medishares.mathwalletlib.MathWalletCallBack;
import com.medishares.mathwalletlib.MathWalletManager;
import com.medishares.mathwalletlib.bean.MathWalletLogin;
import com.medishares.mathwalletlib.bean.MathWalletPay;
import com.medishares.mathwalletlib.util.LogUtil;

import org.json.JSONObject;

import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatButton loginBtn = findViewById(R.id.login_btn);
        AppCompatButton payBtn = findViewById(R.id.pay_btn);
        loginBtn.setOnClickListener(this);
        payBtn.setOnClickListener(this);
        LogUtil.isDebug = true;     //打开log日志
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:        //登录
                login();
                break;
            case R.id.pay_btn:          //支付
                pay();
                break;
        }
    }

    private void pay() {
        MathWalletPay mathWalletPay = new MathWalletPay();
        mathWalletPay.setBlockchain("eosio");       //公链标识
        mathWalletPay.setAction("transfer");        //支付
        mathWalletPay.setDappName("麦子钱包测试SDK"); //dapp名字
        mathWalletPay.setDappIcon("http://medishares.oss-cn-hongkong.aliyuncs.com/logo/mds-parity.png");//dapp图标Url
        mathWalletPay.setFrom("account11");         //付款人
        mathWalletPay.setTo("account12");           //收款人
        mathWalletPay.setAmount("1.2345");          //转账数量
        mathWalletPay.setContract("eosio.token");   //合约地址
        mathWalletPay.setSymbol("EOS");             //token名称
        mathWalletPay.setPrecision(4);              //转账token精度
        mathWalletPay.setDappData("麦子钱包dapp测试");//memo or data
        mathWalletPay.setDesc("这是支付测试");        //交易的说明信息
        mathWalletPay.setExpired(3600l);            //交易过期时间
        mathWalletPay.setCallback("customscheme://customhost?action=transfer");   //回调，scheme和host务必和RouterActivity在xml中设置的相同
        MathWalletManager.getInstance().requestPay(this, mathWalletPay, new MathWalletCallBack() {
            @Override
            public void callBack(Map<String, String> params, String uriString) {
                LogUtil.e(TAG, new JSONObject(params).toString());
                LogUtil.e(TAG, uriString);
            }
        });
    }

    private void login() {
        MathWalletLogin mathWalletLogin = new MathWalletLogin();
        mathWalletLogin.setBlockchain("eosio");                  //公链标识
        mathWalletLogin.setAction("login");                      //登录
        mathWalletLogin.setDappName("麦子钱包测试—SDK");           //dapp名字
        mathWalletLogin.setDappIcon("http://medishares.oss-cn-hongkong.aliyuncs.com/logo/mds-parity.png");//dapp图标Url
        mathWalletLogin.setUuID(UUID.randomUUID().toString());   //登录验证唯一标识
        mathWalletLogin.setLoginUrl("http://www.medishares.org");//service生成，用于接收此次登录验证的URL
        mathWalletLogin.setExpired(3600l);                            //登录过期时间
        mathWalletLogin.setLoginMemo("这是登录测试");               //备注信息，可选
        mathWalletLogin.setCallback("customscheme://customhost?action=login");                //回调，scheme和host务必和RouterActivity在xml中设置的相同
        MathWalletManager.getInstance().requestLogin(this, mathWalletLogin, new MathWalletCallBack() {
            @Override
            public void callBack(Map<String, String> params, String uriString) {
                LogUtil.e(TAG, new JSONObject(params).toString());
                LogUtil.e(TAG, uriString);
            }
        });
    }
}
