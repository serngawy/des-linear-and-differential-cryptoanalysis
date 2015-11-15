/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package descryptoanalysis;

import java.util.BitSet;

/**
 *
 * @author Administrator
 */
public class HelperClass {
    
    
    
    
    public HelperClass(){
    
    }
    
    
 public static BitSet meargeBitSet(BitSet bSet1,int lenghtbSet1, BitSet bSet2,
      int lenghtbSet2) {
    for (int i = 0; i < lenghtbSet2; i++) {
         bSet1.set(lenghtbSet1 + i, bSet2.get(i));
     }
    return bSet1;
  }
    
   public static int toInteger(BitSet set, int totalBits) {
    int acc = 0;
    for (int i = 0; i < totalBits; i++) {
      acc += ((set.get(totalBits - i - 1)) ? 1 : 0) << i;
    }
    return acc;
  }

   public static BitSet getCopy(BitSet set, int totalBits) {
    BitSet newset = new BitSet();
    for (int i = 0; i < totalBits; i++) {
      newset.set(i, set.get(i));
    }
    return newset;

  }
  
  public static BitSet toBitSet(int value, int totalBits) {
    int idx = totalBits - 1;
    BitSet result = new BitSet();
    while (value > 0) {
      result.set(idx--, value % 2 == 1);
      value /= 2;
    }
    return result;
  }
  
   
  public static BitSet toBitSet(char c, int totalBits) {
    int value = Character.getNumericValue(c);
    int idx = totalBits - 1;
    BitSet result = new BitSet();
    while (value > 0) {
      result.set(idx--, value % 2 == 1);
      value /= 2;
    }
    return result;
  }
    
}
