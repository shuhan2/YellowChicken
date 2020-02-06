package com.thoughtworks;

import java.util.Arrays;
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
    StringBuilder originalFoodInfos = new StringBuilder();
    int firstCharge = 0;
    int secondCharge = 0;

    int bestCharge;
    String discountDetail = "";

    for (String foodIdAndCount : selectedItems.split(",")) {
      String[] foodInfos = foodIdAndCount.split("x");
      Food food = generateFood(foodInfos[0].trim());
      int count = Integer.valueOf(foodInfos[1].trim());
      int singleTypeFoodMoney = (int) (food.getPrice() * count);

      originalFoodInfos.append(food.getName())
          .append(" x ")
          .append(count)
          .append(" = ")
          .append(singleTypeFoodMoney)
          .append("元\n");

      firstCharge += singleTypeFoodMoney;

      secondCharge += addSecondCharge(singleTypeFoodMoney, food.getId());

    }

    int secondDiscountCharge = firstCharge - secondCharge;

    if (firstCharge >= 30) {
      firstCharge = firstCharge - 6;
      discountDetail = getDiscountDetail(6, "满30减6元");
    }

    if (firstCharge  <= secondCharge) {
      bestCharge = firstCharge;
    } else {
      discountDetail = getDiscountDetail(secondDiscountCharge, "指定菜品半价(黄焖鸡，凉皮)");
      bestCharge = secondCharge;
    }

    String foodDetails = "============= 订餐明细 =============\n"
        + originalFoodInfos
        + discountDetail
        + "-----------------------------------\n"
        + "总计：" + bestCharge + "元\n"
        + "===================================";
    return foodDetails;
  }

  private static String getDiscountDetail(int discount, String discountChoice) {
    return "-----------------------------------\n" + "使用优惠:\n" + discountChoice + "，省" + discount + "元\n";
  }

  private static int addSecondCharge(int singleTypeFoodMoney, String foodId) {
    if (Arrays.asList(getHalfPriceIds()).contains(foodId)) {
      return singleTypeFoodMoney / 2;
    } else {
      return singleTypeFoodMoney;
    }
  }

  private static Food generateFood(String foodId) {
    int index = IntStream.range(0, getItemIds().length)
        .filter(i -> foodId.equals(getItemIds()[i]))
        .findFirst()
        .orElse(-1);

    return new Food(foodId, getItemNames()[index], getItemPrices()[index]);
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
