package com.sbs.text.board;

import com.sbs.text.board.util.Util;
import lombok.Getter;

import java.util.Map;

public class Rq {
  public String url;

  @Getter
  public Map<String, String> params;

  @Getter
  public String urlPath;

  public Rq(String url) {
    this.url = url;
    params = Util.getParamsFromUrl(this.url);
    urlPath = Util.getUrlPathFromUrl(this.url);
  }

  public int getIntParam(String paramName, int defaultValue) {
    if (params.containsKey(paramName) == false) {
      return defaultValue;
    }

    try {
      return Integer.parseInt(params.get(paramName));
    } catch (NumberFormatException e) {
      return defaultValue;
    }

  }

  public String getParam(String paramName, String defaultValue) {
    if(params.containsKey(paramName) == false) {
      return defaultValue;
    }

    return params.get(paramName);
  }
}
