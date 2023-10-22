package com.complexzeng.cronettest.data;

public class ImageRepository {

  private static final String[] imageUrls = {
          "https://pic-go-1252561521.cos.ap-guangzhou.myqcloud.com/157deb5c-3a30-4bc6-b12e-f10ac933b210.png",
  };

  public static int numberOfImages() {
    return imageUrls.length;
  }

  public static String getImage(int position) {
    return imageUrls[position];
  }
}