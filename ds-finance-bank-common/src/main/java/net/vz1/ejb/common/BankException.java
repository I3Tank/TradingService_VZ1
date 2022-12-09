package net.vz1.ejb.common;

public class BankException extends java.lang.Exception{

    public BankException(){
    }

    public BankException(String msg){ super(msg); }

    public BankException(String msg, Throwable cause){ super(msg, cause); }

    public BankException(Throwable cause){ super(cause); }
}
