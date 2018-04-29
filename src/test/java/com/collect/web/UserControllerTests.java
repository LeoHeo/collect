package com.collect.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.collect.domain.user.Provider;
import com.collect.dto.user.LoginDto;
import com.collect.dto.user.UserSaveDto;
import com.collect.dto.user.ValidEmailDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author Heo, Jin Han
 * @since 2018-03-31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void 인자가_아무것도_없을때_400() throws Exception {
    this.mockMvc.perform(post("/user/signup"))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void 이메일이_유효하지_않을때_400() throws Exception {
    UserSaveDto userSaveDto = new UserSaveDto();
    userSaveDto.setUsername("hjh");
    userSaveDto.setAddress("seoul");
    userSaveDto.setPassword("123456");
    userSaveDto.setEmail("hjh544");
    userSaveDto.setProvider(Provider.create("test"));

    this.mockMvc.perform(
        post("/user/signup")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(userSaveDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void provider_유효하지_않을떄_400() throws Exception {
    UserSaveDto userSaveDto = new UserSaveDto();
    userSaveDto.setUsername("hjh5488@gmail.com");
    userSaveDto.setAddress("seoul");
    userSaveDto.setPassword("123456");
    userSaveDto.setProvider(Provider.create("test"));

    this.mockMvc.perform(
        post("/user/signup")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(userSaveDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void 모든조건이_유효할때_200() throws Exception {
    UserSaveDto userSaveDto = new UserSaveDto();
    userSaveDto.setUsername("hjh5488");
    userSaveDto.setAddress("seoul");
    userSaveDto.setPassword("123456");
    userSaveDto.setEmail("hjh5488@gmail.com");
    userSaveDto.setProvider(Provider.create("google"));

    this.mockMvc.perform(
        post("/user/signup")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(userSaveDto)))
        .andExpect(status().isCreated());
  }

  @Test
  public void 로그인_실패_401() throws Exception {
    LoginDto loginDto = new LoginDto();
    loginDto.setUsername("test");
    loginDto.setPassword("123");

    this.mockMvc.perform(
        post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(loginDto)))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void 로그인_성공_200() throws Exception {
    LoginDto loginDto = new LoginDto();
    loginDto.setUsername("hjh5488");
    loginDto.setPassword("123456");

    this.mockMvc.perform(
        post("/auth/login")
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .content(objectMapper.writeValueAsString(loginDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").exists());
  }

  @Test
  public void 유효하지않은_이메일_포맷_체크_400() throws Exception {
    ValidEmailDto validEmailDto = new ValidEmailDto();
    validEmailDto.setEmail("hjh");

    this.mockMvc.perform(
        post("/user/valid/email")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(validEmailDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void 유효하지않은_이메일_400() throws Exception {
    ValidEmailDto validEmailDto = new ValidEmailDto();
    validEmailDto.setEmail("hjh@gg.com");

    this.mockMvc.perform(
        post("/user/valid/email")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(validEmailDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void 유효한_이메일_200() throws Exception {
    ValidEmailDto validEmailDto = new ValidEmailDto();
    validEmailDto.setEmail("hjh5488@gmail.com");

    this.mockMvc.perform(
        post("/user/valid/email")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(validEmailDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value(validEmailDto.getEmail()));
  }
}
