package com.coffice.shengtao.cofficemachine.databaseframe.litepal.model;

import org.litepal.crud.LitePalSupport;

/**
 * 机器的表
 * 机器的名称
 * 编号
 * 位置
 * 状态
 */
public class Machine extends LitePalSupport {
   /**
    * 主键
    */
   private int id;
   /**
    * 机器名称
    */
   private String machineName;
   /**
    *机器编号
    */
   private String number;
   /**
    *机器的位置 会发生改变
    */
   private String address;
   /**
    *机器的状态 会发生改变
    */
   private String machineState;//机器状态  正常 异常{网络异常，机器门打开，断电,等？}

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getMachineName() {
      return machineName;
   }

   public void setMachineName(String machineName) {
      this.machineName = machineName;
   }

   public String getNumber() {
      return number;
   }

   public void setNumber(String number) {
      this.number = number;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public String getMachineState() {
      return machineState;
   }

   public void setMachineState(String machineState) {
      this.machineState = machineState;
   }
   public Machine(){

   }
   public Machine(String machineName, String number, String address, String machineState) {
      this.machineName = machineName;
      this.number = number;
      this.address = address;
      this.machineState = machineState;
   }
}
