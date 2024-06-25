package com.charmroom.charmroom.dto;

import org.springframework.web.multipart.MultipartFile;

import com.charmroom.charmroom.dto.validation.ValidUser;
import com.charmroom.charmroom.entity.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class SignupDto {
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@ValidUser.Same.PasswordConfirm(password = "password", rePassword = "rePassword")
	public static class SignupRequestDto {
		@Size(min = 3, max = 30)
		@NotEmpty(message = "ID는 필수항목입니다.")
		@ValidUser.Unique.Username
		private String username;
	
		@Size(max = 255)
		@NotEmpty(message = "비밀번호는 필수항목입니다.")
		private String password;
		
		@Size(max = 255)
		@NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
		private String rePassword;
		
		@Size(max = 255)
		@NotEmpty(message = "이메일은 필수항목입니다.")
		@Email
		@ValidUser.Unique.Email
		private String email;
		
		@Size(min = 3, max = 30)
		@NotEmpty(message = "닉네임은 필수항목입니다.")
		@ValidUser.Unique.Nickname
		private String nickname;
		
		MultipartFile image;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class SignupResponseDto {
		private String username;
		private String email;
		private String nickname;
		private String role;
		
		public static SignupResponseDto fromEntity(User user) {
			return SignupResponseDto.builder()
					.username(user.getUsername())
					.email(user.getEmail())
					.nickname(user.getNickname())
					.role(user.getLevel().getValue())
					.build();
		}
	}
	
	
}
