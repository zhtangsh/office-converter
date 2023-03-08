package com.zhtangsh.oc.config;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("oc")
public class OCProperties {

  private Office office;

  @Data
  public static class Office {

    private String home;
    private String host;
    private List<Integer> ports;
  }
}
