package io.github.ital023.dscatalog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailDTO {

	@Email(message = "Email inválido")
	@NotBlank(message = "Campo obrigatório")
    private String email;

	public EmailDTO() {
	}

	public EmailDTO(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
}
