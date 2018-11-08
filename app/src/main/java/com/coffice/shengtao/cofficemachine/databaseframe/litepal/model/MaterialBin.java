package com.coffice.shengtao.cofficemachine.databaseframe.litepal.model;

import java.util.Map;

/**
 * 物料藏
 * 有关物料的信息
 * 每种物料的剩余量
 */
public class MaterialBin {
    private int id;
    private Map<Materiel,Double> materieInfo;//物料信息
}
