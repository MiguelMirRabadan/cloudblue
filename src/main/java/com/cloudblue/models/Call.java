package com.cloudblue.models;


import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class Call {

    @Getter @Setter
    private String ip;



}
