package com.felipesantacruz.productmanager.user.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity implements UserDetails
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	private String fullName;
	
	@Email(message = "Not a valid email")
	private String email;

	@Column(unique = true)
	@NotBlank(message = "Username shouldn't be blank")
	@Length(min = 3)
	private String username;

	@NotBlank(message = "Password shouldn't be blank")
	@Length(min = 8)
	private String password;

	private String avatar;

	@CreatedDate
	private LocalDateTime createdAt;
	
	@Builder.Default
	private LocalDateTime lastPasswordChangeAt = LocalDateTime.now();
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private Set<UserRole> roles;

	
	private static final int PASSWORD_EXPIRATION_DATE = 60;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		// @formatter:off
		return roles.stream()
				.map(ur -> new SimpleGrantedAuthority("ROLE_".concat(ur.name())))
				.collect(Collectors.toList());		
		// @formatter:on
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return lastPasswordChangeAt
				.isBefore(getPasswordExpirationDate());
	}

	private LocalDateTime getPasswordExpirationDate()
	{
		return lastPasswordChangeAt
					.plusDays(PASSWORD_EXPIRATION_DATE);
	}

	@Override
	public boolean isEnabled()
	{
		return true;
	}

}
