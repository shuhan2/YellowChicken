package com.thoughtworks;

import java.util.Scanner;
import java.util.stream.IntStream;

public class App {

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    System.out.println("请点餐（菜品Id x 数量，用逗号隔开）：");
    String selectedItems = scan.nextLine();
    String summary = bestCharge(selectedItems);
    System.out.println(summary);
  }

  /**
   * 接收用户选择的菜品和数量，返回计算后的汇总信息
   *
   * @param selectedItems 选择的菜品信息
   */
  public static String bestCharge(String selectedItems) {
    // 此处补全代码
    String[] foodIdAndCounts = selectedItems.split(",");
    StringBuilder originalFoodInfos = new StringBuilder();
    int firstPrice = 0;

    int secondPrice = 0;
    int secondDiscountPrice = 0;

    int finalPrice;
    String discountDetail = "";

    for (String foodIdAndCount : foodIdAndCounts) {

      String[] foodInfos = foodIdAndCount.split("x");
      String foodId = foodInfos[0].trim();
      int index = IntStream.range(0, getItemIds().length)
          .filter(i -> foodId.equals(getItemIds()[i]))
          .findFirst()
          .orElse(-1);
      int count = Integer.valueOf(foodInfos[1].trim());
      double itemPrice = getItemPrices()[index];
      int singleTypePrice = (int) (itemPrice * count);

      firstPrice += singleTypePrice;

      boolean isHalf = false;
      for (String element : getHalfPriceIds()) {
        if (element.equals(foodId)) {
          isHalf = true;
        }
      }

      if (isHalf) {
        secondPrice += singleTypePrice / 2;
        secondDiscountPrice += singleTypePrice / 2;
      } else {
        secondPrice += singleTypePrice;
      }
      originalFoodInfos.append(getItemNames()[index]).append(" x ").append(count).append(" = ").append(singleTypePrice).append("元\n");
    }

    if (firstPrice >= 30) {
      firstPrice = firstPrice - 6;
      discountDetail = "-----------------------------------\n" +"使用优惠:\n" +"满30减6元，省6元\n";
    }

    if (firstPrice <= secondPrice) {
      finalPrice = firstPrice;
    } else {
      discountDetail = "-----------------------------------\n" + "使用优惠:\n" + "指定菜品半价(黄焖鸡，凉皮)，省" + secondDiscountPrice + "元\n";
      finalPrice = secondPrice;
    }

    String foodDetails = "============= 订餐明细 =============\n"
        + originalFoodInfos
        + discountDetail
        + "-----------------------------------\n"
        + "总计：" + finalPrice + "元\n"
        + "===================================";
    return foodDetails;
  }

  /**
   * 获取每个菜品依次的编号
   */
  public static String[] getItemIds() {
    return new String[]{"ITEM0001", "ITEM0013", "ITEM0022", "ITEM0030"};
  }

  /**
   * 获取每个菜品依次的名称
   */
  public static String[] getItemNames() {
    return new String[]{"黄焖鸡", "肉夹馍", "凉皮", "冰粉"};
  }

  /**
   * 获取每个菜品依次的价格
   */
  public static double[] getItemPrices() {
    return new double[]{18.00, 6.00, 8.00, 2.00};
  }

  /**
   * 获取半价菜品的编号
   */
  public static String[] getHalfPriceIds() {
    return new String[]{"ITEM0001", "ITEM0022"};
  }
}
