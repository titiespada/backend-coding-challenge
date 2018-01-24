package com.engage.backendcodingchallenge.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.engage.backendcodingchallenge.dto.ExpenseDto;

@Entity
@Table(name = "expense")
public class Expense implements Serializable {

	private static final long serialVersionUID = -2994756146947263079L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "date", nullable = false)
	private LocalDate date;
	
	@Column(name = "value", nullable = false)
	private BigDecimal value;

	@Column(name = "reason", nullable = false, length = 200)
	private String reason;
	
	public Expense() {}
	
	public Expense(Integer id, LocalDate date, BigDecimal value, String reason) {
		this.id = id;
		this.date = date;
		this.value = value;
		this.reason = reason;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return this.date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
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

	public static Expense createExpense(ExpenseDto expenseDto) {
		Expense expense = new Expense();
		expense.setId(expenseDto.getId());	
		expense.setDate(expenseDto.getDate());
		expense.setValue(expenseDto.getGbpValue());
		expense.setReason(expenseDto.getReason());
		return expense;
	}

}
