package com.engage.backendcodingchallenge.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.engage.backendcodingchallenge.model.Expense;
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
	@JsonFormat(pattern = "^\\d+(\\.)\\d{2}( EUR)?$")
	private String value;
	
	private BigDecimal vat;
	
	@NotBlank
	@Size(max = 200)
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
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
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
	
	private static BigDecimal calculateVat(BigDecimal amount) {
		return amount.subtract(amount.divide(BigDecimal.valueOf(1.2), 2, RoundingMode.HALF_UP));
	}

	public static ExpenseDto createExpenseDto(Expense expense) {
		ExpenseDto expenseDto = new ExpenseDto();
		expenseDto.setId(expense.getId());
		expenseDto.setDate(expense.getDate());
		expenseDto.setValue(expense.getValue().toString());
		expenseDto.setReason(expense.getReason());
		expenseDto.setGbpValue(expense.getValue());
		expenseDto.setVat(calculateVat(expense.getValue()));
		return expenseDto;
	}

}
