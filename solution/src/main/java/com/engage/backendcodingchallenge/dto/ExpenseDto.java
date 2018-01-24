package com.engage.backendcodingchallenge.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

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
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate date;
	
	@NotNull
	@JsonProperty("amount")
	@JsonFormat(pattern="^\\d+(\\.)\\d{2}( EUR)?$")
	private String value;
	
	private Double vat;
	
	@NotBlank
	@Size(max=200)
	private String reason;
	
	private BigDecimal gbpValue;

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
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
	
	public Double getVat() {
		return Double.valueOf(gbpValue.doubleValue() - (gbpValue.doubleValue() / 1.2));
	}
	
	@Override
	public String toString() {
		return "ExpenseDto [id=" + id + ", date=" + date + ", value=" + value + ", vat=" + vat + ", reason=" + reason + "]";
	}

	public static ExpenseDto createExpenseDto(Expense expense) {
		ExpenseDto expenseDto = new ExpenseDto();
		expenseDto.setId(expense.getId());
		expenseDto.setDate(expense.getDate());
		expenseDto.setValue(expense.getValue().toString());
		expenseDto.setReason(expense.getReason());
		expenseDto.setGbpValue(expense.getValue());
		return expenseDto;
	}

}
