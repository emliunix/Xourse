package org.xourse.entity;

import javax.persistence.*;

/**
 * Created by Liu Yuhui on 2015/11/28.
 */
@Entity
public class StudentProfile {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private String idCardNumber;
    @Column
    private String telNumber;
    @Column
    private String email;
    @Column
    private String residence;
    @Column
    private String politicalStatus;
    @Column
    private String signature;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(String politicalStatus) {
        politicalStatus = politicalStatus;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
