package com.bmc.flow.modules.resources.base;

import java.util.function.ToIntFunction;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class ResourceConstants {

  private static final Integer MAX_PAGE_SIZE = 100;

  private static final Integer MIN_PAGE_SIZE = 5;

  /**
   * a pageSize has to be between {@link ResourceConstants#MIN_PAGE_SIZE} and {@link ResourceConstants#MAX_PAGE_SIZE}
   */
  public static final ToIntFunction<Integer> CHECK_PAGE = (pageSize) -> max(min(pageSize, MAX_PAGE_SIZE), MIN_PAGE_SIZE);

  private ResourceConstants() {
  }

}
