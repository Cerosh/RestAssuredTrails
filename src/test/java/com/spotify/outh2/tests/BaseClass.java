package com.spotify.outh2.tests;

import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class BaseClass {
    @BeforeMethod
    public void settingNameAndId(Method methodName){
        System.out.println("Starting : "+ methodName.getName());
        System.out.println("Thread Id: " + Thread.currentThread().getId());
    }
}
