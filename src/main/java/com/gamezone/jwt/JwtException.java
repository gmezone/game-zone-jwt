package com.gamezone.jwt;

public class JwtException extends  Exception{
    private static final long serialVersionUID = 1L;
    public JwtException(String message){
        super(message);
    }
    public JwtException(String message , Object... args){
        super(String.format(message ,args));
    }
    public JwtException(String message , Throwable cause){
        super(message ,cause);
    }
    public JwtException(String message , Throwable cause ,Object... args){
        super(String.format(message ,args),cause);
    }
}
