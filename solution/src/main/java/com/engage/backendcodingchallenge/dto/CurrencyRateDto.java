package com.engage.backendcodingchallenge.dto;

import java.io.Serializable;

public class CurrencyRateDto implements Serializable {

    private static final long serialVersionUID = -6802159779846386812L;

    private String result;
    private long timestamp;
    private String from;
    private String to;
    private Double rate;

    public CurrencyRateDto() { }

    public CurrencyRateDto(String result, long timestamp, String from,
            String to, Double rate) {
        this.result = result;
        this.timestamp = timestamp;
        this.from = from;
        this.to = to;
        this.rate = rate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "CurrencyConvertDto [result=" + result + ", timestamp=" + timestamp + ", from=" + from + ", to=" + to
                + ", rate=" + rate + "]";
    }

}
