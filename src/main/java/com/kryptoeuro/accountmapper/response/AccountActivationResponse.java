package com.kryptoeuro.accountmapper.response;

import com.kryptoeuro.accountmapper.domain.AuthorisationType;
import com.kryptoeuro.accountmapper.state.AuthenticationStatus;
import lombok.Data;

@Data
public class AccountActivationResponse {
	String authenticationStatus;
	String authorisationType;

	public AccountActivationResponse(AuthenticationStatus status, AuthorisationType type) {
		this.authenticationStatus = status.name();
		this.authorisationType = type.name();
	}
}