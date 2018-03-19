/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hoodiv.javaluator;

/**
 *
 * @author Hood
 */
public interface EvaluationContext {
    default public IllegalArgumentException getError(String msg){        
        return new IllegalArgumentException(msg);
    }
    
    default public IllegalArgumentException getError(String msg,Token tok){   
        if(tok!=null){
            return getError(tok.appendTokenInfo(msg));
        } else {
            return getError(msg);
        }
    }    
}
