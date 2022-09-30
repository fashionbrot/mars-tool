package com.github.fashionbrot.tool.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author fashi
 */

@Getter
@AllArgsConstructor
public enum AlgorithmEnum {
    RSA256("RSA256"),
    RSA512("RSA512"),
    HMAC256("HMAC256"),
    HMAC512("HMAC512")
    ;


    private String algorithm;
}
