package com.taquitosncapas.helpinghands.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity(name = "tokens")
@Getter
@Setter
public class Token {

	@Id
	@SequenceGenerator(name = "token_id_gen", sequenceName = "token_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_id_gen")
	private Long id;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "active")
	private Boolean active = true;
	
	@Column(name = "create_at", updatable = false)
	private Timestamp create_at = Timestamp.valueOf(LocalDateTime.now());

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user")
	private User user;

	public Token() {
		super();
	}

	public Token(String content, User user,Boolean active) {
		super();
		this.content = content;
		this.user = user;
		this.active = active;
	}

	@Override
	public String toString() {
		return "content=" + content + ", active=" + active + ", create_at=" + create_at
				+ ", user=" + user + "]";
	}
}
