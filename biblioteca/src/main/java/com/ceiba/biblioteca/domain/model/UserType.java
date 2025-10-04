package com.ceiba.biblioteca.domain.model;

import com.ceiba.biblioteca.domain.exception.BadUserTypeException;
import com.ceiba.biblioteca.domain.helper.DomainConstants;

public enum UserType {
    USER_AFFILIATE(1),
    USER_EMPLOYEE(2),
    USER_INVITED(3);

    private final int numberUserType;

    UserType(int numberUserType){
        this.numberUserType = numberUserType;
    }

    public int getNumberUserType(){
        return numberUserType;
    }

    public static UserType fromNumberUserType(int numberUserType) {
        for (UserType e : UserType.values()) {
            if (e.getNumberUserType() == numberUserType) {
                return e;
            }
        }
        throw new BadUserTypeException(DomainConstants.MSG_BAD_USER_TYPE);
    }

}
