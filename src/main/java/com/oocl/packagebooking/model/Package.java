package com.oocl.packagebooking.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "logisticsNumber")
    private String logisticsNumber;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

//    @Column(name = "mobilePhone")
//    private String mobilePhone;

    @Column(name = "status")
    private int status;

    @Column(name = "appointmentTime")
    private Date appointmentTime;

    public Package() {
    }

    public Package(String logisticsNumber, Customer customer, String mobilePhone, int status, Date appointmentTime) {
        this.logisticsNumber = logisticsNumber;
        this.customer = customer;
//        this.mobilePhone = mobilePhone;
        this.status = status;
        this.appointmentTime = appointmentTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogisticsNumber() {
        return logisticsNumber;
    }

    public void setLogisticsNumber(String logisticsNumber) {
        this.logisticsNumber = logisticsNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

//    public String getMobilePhone() {
//        return mobilePhone;
//    }
//
//    public void setMobilePhone(String mobilePhone) {
//        this.mobilePhone = mobilePhone;
//    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
}
