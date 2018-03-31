package com.collect.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.collect.CollectApplication;
import com.collect.security.WebSecurity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Heo, Jin Han
 * @since 2018-03-31
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@ContextConfiguration(classes={CollectApplication.class, WebSecurity.class})
public class UserControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void 인자가_아무것도_없을때_400() throws Exception {
    this.mockMvc.perform(post("/user/signup"))
        .andExpect(status().isBadRequest());

  }
}
