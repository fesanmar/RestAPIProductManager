package com.felipesantacruz.productmanager.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.felipesantacruz.productmanager.user.model.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "purchase_order")
//For @CreatedDate to work. @EnableJpaAuditing must be present in a class annotated with @Configuration
@EntityListeners(AuditingEntityListener.class)
public class Order
{
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_entity_id")
	private UserEntity customer;

	@CreatedDate
	private LocalDateTime date;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@Builder.Default
	@JsonManagedReference
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<OrderRow> orderRows = new HashSet<>();
	
	public float getTotal()
	{
		return (float) orderRows.stream()
								.mapToDouble(OrderRow::getSubtotal)
								.sum();
	}
	
	public void addOrderRow(OrderRow r)
	{
		orderRows.add(r);
		r.setOrder(this);
	}
	
	public void removeOrderRow(OrderRow r)
	{
		orderRows.remove(r);
		r.setOrder(null);
	}
}
