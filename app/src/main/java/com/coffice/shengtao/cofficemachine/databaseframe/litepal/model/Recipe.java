package com.coffice.shengtao.cofficemachine.databaseframe.litepal.model;

import java.util.Map;

/**
 * 饮品的配方
 */
public class Recipe {
   private int id;
   private String drinkRecipeName;//饮品配方的名称
   private Map<Materiel,Double> recipeDetal;//配方明细
}
