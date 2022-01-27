package com.cloudblue.dao;

import com.cloudblue.models.Call;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CallDAO implements iCall {


    @Override
    public String ipRefactor(Call call) {
        if(call.getIp().equals("0:0:0:0:0:0:0:1")) call.setIp("127.0.0.1");
        return call.getIp();
    }




}
