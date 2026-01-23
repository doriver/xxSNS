package com.example.sns.user.presentation;

import com.example.sns.config.security.jwt.JwtToken;
import com.example.sns.user.application.UserSignService;

import com.example.sns.user.presentation.dto.UserSignInDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {
	
	private final UserSignService userSignService;

	// 로그인
	@PostMapping("/users/sign-in")
    public String signIn(@RequestBody UserSignInDTO userSignInDTO, HttpServletResponse response) {

		JwtToken jwtToken = userSignService.authenticateUser(userSignInDTO.getNickname(), userSignInDTO.getPassword());
		log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());

		// ACCESS TOKEN 쿠키로 발급
		Cookie accessCookie = new Cookie("Authorization", jwtToken.getAccessToken());
		accessCookie.setHttpOnly(true);
		accessCookie.setMaxAge(30 * 60); // 30분 동안 유효
		accessCookie.setPath("/");

		response.addCookie(accessCookie);
        
		return "redirect:/invest-view";
	}

	// 아이디 중복확인 기능 - 입력받은id를 db에서 조회(select where)
	@GetMapping("/users/{loginId}")
	public ApiResponse<Map<String, Boolean>> isDuplicateId (
			@PathVariable("loginId") String loginId) {

		Map<String, Boolean> result = new HashMap<>();

		if(userBO.isDuplicateId(loginId)) {
			result.put("is_duplicate", true);
		} else {
			result.put("is_duplicate", false);
		}

		return ApiResponse.success(result);
	}


	// 회원가입 기능 - 입력받은 정보들을 db에 저장(insert)
	@PostMapping("/users")
	public ApiResponse<?> signUp( @Validated @RequestBody UserSaveForm form
			, BindingResult bindingResult ) {

		if (bindingResult.hasErrors()) {
			Map<String, String> validationMessage
					= cmnValidation.getValidationMessage(bindingResult);

			return ApiResponse.fail("failValidation", validationMessage);
		}

		int count = userBO.signUp(form.getLoginId(), form.getPassword(), form.getNickName(), form.getEmail());

		if (count == 1) {
			return ApiResponse.success();
		} else { // 암호화 실패경우
			return ApiResponse.fail("비밀번호 암호화 실패");
		}

	}

	// 사용자의 위치설정 기능
	@PatchMapping("/users/location")
	public ApiResponse<?> userLocation(
			@RequestParam("location") String location
			, UserInfo userInfo) {

		int count = userBO.editLocation(userInfo.getUserId(), location);

		if (count == 1) {
			return ApiResponse.success();
		} else {
			return ApiResponse.fail("위치 설정 실패");
		}

	}


	// 사용자의 프로필 설정 기능
	@PatchMapping("/users/profile")
	public ApiResponse<?> userProfile(
			@RequestParam("profileStatusMessage") String profileStatusMessage
			, @RequestParam(value = "file", required = false) MultipartFile file
			, UserInfo userInfo) {

		int count = userBO.editProfile(userInfo.getUserId(), file, profileStatusMessage);

		if (count == 1) {
			return ApiResponse.success();
		} else {
			return ApiResponse.fail("프로필 설정 실패");
		}

	}
	
}
