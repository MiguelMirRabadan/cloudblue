package com.cloudblue.controllers;

import com.cloudblue.dao.CallDAO;
import com.cloudblue.models.Call;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ConcurrentHashMap;


@RestController
@ResponseBody
public class CallController {

    @Autowired // se crea un objeto de la clase automáticamente y se comparte en memoria por cada autowired que se indique.
    private CallDAO callDAO;
    @Autowired
    private Call call;
    @Autowired
    private ServerProperties serverProperties;

    private ConcurrentHashMap<String, Integer> chmap = new ConcurrentHashMap<String, Integer>();

    final int maxCalls = 2;

    @RequestMapping(value = "/api/checkip", method = RequestMethod.GET)
    public ResponseEntity<String>  checkip() {
        ConfigServerProperties();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        call.setIp(request.getRemoteAddr());
        callDAO.ipRefactor(call);
        chmap.putIfAbsent(call.getIp(),maxCalls);

        if(chmap.get(call.getIp())<=maxCalls && chmap.get(call.getIp())!=0){
            chmap.computeIfPresent(call.getIp(), (key, oldValue) -> oldValue -1);
            return ResponseEntity.status(HttpStatus.OK).body(call.getIp() + ""+ chmap.get(call.getIp()));
        }
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("");
    }

    public void ConfigServerProperties() {
        //serverProperties.getTomcat().setMaxConnections(2);
        serverProperties.setPort(8080);
    }

    @Scheduled(fixedRate = 1000*60)
    public void ResetCounter() {
        if(!chmap.isEmpty()){
            chmap.computeIfPresent(call.getIp(), (key, oldValue) -> maxCalls);
        }

    }



}
