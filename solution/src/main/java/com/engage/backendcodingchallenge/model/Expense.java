package com.engage.backendcodingchallenge.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "expense")
public class Expense implements Serializable {

    private static final long serialVersionUID = -2994756146947263079L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @Column(name = "reason", nullable = false, length = 200)
    private String reason;

    public Expense() { }

    public Expense(Integer id, Date date, BigDecimal value, String reason) {
        this.id = id;
        this.date = new Date(date.getTime());
        this.value = value;
        this.reason = reason;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        if(date == null) {
            return new Date();
        } else {
            return new Date(date.getTime());
        }
    }

    public void setDate(Date date) {
        if(date == null) {
            this.date = new Date();
        } else {
            this.date = new Date(date.getTime());
        }
    }

    public BigDecimal getValue() {
        return this.value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Expense [id=" + id + ", date=" + date + ", value=" + value + ", reason=" + reason + "]";
    }

}
