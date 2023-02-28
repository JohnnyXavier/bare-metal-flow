package com.bmc.flow.modules.utilities;

public class DataUtils {

  private DataUtils() {
  }

  public static String getImage(final String coverImage, final String placeholder) {
    return coverImage == null ? "https://via.placeholder.com/120?text=BMC+Flow:" + placeholder : coverImage;
  }

}
