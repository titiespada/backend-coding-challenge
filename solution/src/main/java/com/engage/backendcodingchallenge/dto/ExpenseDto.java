package com.engage.backendcodingchallenge.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExpenseDto implements Serializable {

    private static final long serialVersionUID = 525813058606462731L;

    private Integer id;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date date;

    @NotNull
    @JsonProperty("amount")
    @DecimalMin(value = "0.01", inclusive = true)
    @DecimalMax(value = "9999999999999.99", inclusive = true)
    private BigDecimal value;

    private String currency;

    private BigDecimal vat;

    @NotBlank
    @Size(min = 1, max = 200)
    private String reason;

    private BigDecimal gbpValue;
    
    public Integer getId() {
        return id;
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
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public BigDecimal getGbpValue() {
        return gbpValue;
    }

    public void setGbpValue(BigDecimal gbpValue) {
        this.gbpValue = gbpValue;
    }

    public BigDecimal getVat() {
        return this.vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    @Override
    public String toString() {
        return "ExpenseDto [id=" + id + ", date=" + date + ", value=" + value + ", reason=" + reason + "]";
    }

}
