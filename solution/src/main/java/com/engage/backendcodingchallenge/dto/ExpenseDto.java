package com.engage.backendcodingchallenge.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.Min;
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
	@Min(value=0)
	@JsonProperty("amount")
	private Double value;
	
	private Double vat;
	
	@NotBlank
	@Size(max=200)
	private String reason;

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
	
	public Double getValue() {
		return value;
	}
	
	public void setValue(Double value) {
		this.value = value;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public Double getVat() {
		return Double.valueOf(value.doubleValue() - (value.doubleValue() / 1.2));
	}
	
	@Override
	public String toString() {
		return "ExpenseDto [id=" + id + ", date=" + date + ", value=" + value + ", vat=" + vat + ", reason=" + reason + "]";
	}

	public static ExpenseDto createExpenseDto(Expense expense) {
		ExpenseDto expenseDto = new ExpenseDto();
		expenseDto.setId(expense.getId());
		expenseDto.setDate(expense.getDate());
		expenseDto.setValue(expense.getValue());
		expenseDto.setReason(expense.getReason());
		return expenseDto;
	}

}
