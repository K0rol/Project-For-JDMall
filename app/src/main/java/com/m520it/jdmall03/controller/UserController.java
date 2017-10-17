package com.m520it.jdmall03.controller;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall03.bean.Account;
import com.m520it.jdmall03.bean.Result;
import com.m520it.jdmall03.constant.IDiyMessage;
import com.m520it.jdmall03.constant.NetworkConst;
import com.m520it.jdmall03.utils.AESUtils;
import com.m520it.jdmall03.utils.NetworkUtil;

import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/8/3 0003.
 * <p>
 *      与用户有关的登录 退出  保存用户信息的其他请求
 */
public class UserController extends BaseController {

    public UserController(Context context) {
        super(context);
    }

    @Override
    public void handleMessage(int action, Object... values) {
        switch (action) {
            case IDiyMessage.LOGIN_ACTION:
                onModelChanged(action,login((String) values[0], (String) values[1]));
                break;
            case IDiyMessage.REGIST_ACTION:
                onModelChanged(action,regist((String) values[0], (String) values[1]));
                break;
            case IDiyMessage.SAVE_ACCOUNT_ACTION:
                saveUserAccount((String) values[0], (String) values[1]);
                break;
            case IDiyMessage.QUERY_ACCOUNT_ACTION:
                onModelChanged(action,queryUser());
                break;
            case IDiyMessage.CLEAR_ACCOUNT_ACTION:
                clearAccountTable();
                break;
        }
    }

    private void clearAccountTable() {
        DataSupport.deleteAll(Account.class);
    }

    /**
     * 如果没有用户登录 可能返回为null
     * */
    private Account queryUser() {
        List<Account> accounts = DataSupport.findAll(Account.class);
        if (accounts.size()>0){
            Account account = accounts.get(0);
            if (!TextUtils.isEmpty(account.getPwd())){
                try {
                    account.setPwd(AESUtils.decrypt(account.getPwd()));
                    return account;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private void saveUserAccount(String name, String pwd){
        DataSupport.deleteAll(Account.class);
        //TODO 加密密码
        try {
            pwd = AESUtils.encrypt(pwd);
            Account account = new Account(name, pwd);
            account.save();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Account account = new Account(name, "");
        account.save();
    }

    private Result login(String name, String pwd) {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("username", name);
        paramsMap.put("pwd", pwd);
        String jsonStr = NetworkUtil.doPost(NetworkConst.LOGIN_URL, paramsMap);
        return JSON.parseObject(jsonStr, Result.class);
    }

    private Result regist(String name,String pwd){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("username", name);
        paramsMap.put("pwd", pwd);
        String jsonStr = NetworkUtil.doPost(NetworkConst.REGIST_URL, paramsMap);
        return JSON.parseObject(jsonStr, Result.class);
    }


}
