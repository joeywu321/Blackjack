package com.evatar.blackjack.views;

import com.evatar.blackjack.R;
import com.sxh.bcllib.BclSDK;

public class GlobalClass {
    public static GlobalClass instance = null;
    public BclSDK bclSDK;
    public boolean isBclSDKInit;
    public String playerName;
    public int currentCardImgID;


    private GlobalClass() {
        bclSDK = new BclSDK();
        isBclSDKInit = false;
        playerName = "Player";
        currentCardImgID = R.drawable.back_red;
    }


    public static GlobalClass getInstance() {
        if( instance == null)
            instance = new GlobalClass();
        return instance;
    }


}
