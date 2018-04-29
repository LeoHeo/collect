package com.collect;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Heo, Jin Han
 * @since 2018-03-31
 */
@RestController
public class IndexController {

  @GetMapping("/")
  public String index() {
    return "index";
  }
}
