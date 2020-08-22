package com.automation.api.Util;

import org.testng.annotations.Test;

public class configReader {
	  
  public int methodtwo(int a, int b) {
	  int sum = a+ b;
	  return sum;
	  
  }
  
  public static  int methodone(int a, int b) {
	  int sum = a+ b;
	  return sum;
	  
  }
  
  public static void main(String[] args) {
	
	  configReader md = new configReader();
	  int result = md.methodtwo(2,3);
	  System.out.println(result);
	  int resultone = methodone(3,4);
  }
  
  @Test
  public void testone() {
	  
  }
  
}
