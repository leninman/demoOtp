
package com.demos.demoOtp1.Models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Cliente implements Serializable{
    
    @Id
    private String PartyId;
    private String IssuedIdentType;
    private String IssuedIdentValue;
    private String IssuedDocType;
    private String Descripcion;
    private String FullName;
    private String EmailAddr;
    private String CelPhone;
    private String HomePhone;

    public Cliente() {
        
    }
    public Cliente(String PartyId, String IssuedIdentType, String IssuedIdentValue, String IssuedDocType, String Descripcion, String FullName, String EmailAddr, String CelPhone, String HomePhone) {
        this.PartyId = PartyId;
        this.IssuedIdentType = IssuedIdentType;
        this.IssuedIdentValue = IssuedIdentValue;
        this.IssuedDocType = IssuedDocType;
        this.Descripcion = Descripcion;
        this.FullName = FullName;
        this.EmailAddr = EmailAddr;
        this.CelPhone = CelPhone;
        this.HomePhone = HomePhone;
    }

    public String getPartyId() {
        return PartyId;
    }

    public void setPartyId(String PartyId) {
        this.PartyId = PartyId;
    }

    public String getIssuedIdentType() {
        return IssuedIdentType;
    }

    public void setIssuedIdentType(String IssuedIdentType) {
        this.IssuedIdentType = IssuedIdentType;
    }

    public String getIssuedIdentValue() {
        return IssuedIdentValue;
    }

    public void setIssuedIdentValue(String IssuedIdentValue) {
        this.IssuedIdentValue = IssuedIdentValue;
    }

    public String getIssuedDocType() {
        return IssuedDocType;
    }

    public void setIssuedDocType(String IssuedDocType) {
        this.IssuedDocType = IssuedDocType;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public String getEmailAddr() {
        return EmailAddr;
    }

    public void setEmailAddr(String EmailAddr) {
        this.EmailAddr = EmailAddr;
    }

    public String getCelPhone() {
        return CelPhone;
    }

    public void setCelPhone(String CelPhone) {
        this.CelPhone = CelPhone;
    }

    public String getHomePhone() {
        return HomePhone;
    }

    public void setHomePhone(String HomePhone) {
        this.HomePhone = HomePhone;
    }
    
    private static long serialVersionUID = 1L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static void setSerialVersionUID(long serialVersionUID) {
        Cliente.serialVersionUID = serialVersionUID;
    }
    
    
}
