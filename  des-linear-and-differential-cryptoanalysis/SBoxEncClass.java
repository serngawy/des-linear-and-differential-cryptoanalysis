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
public class SBoxEncClass {
    
    public int[] sBox;//=new int[]{14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7};
    public int[] sBox_INV;//=new int[]{14,3,4,8,1,12,10,15,7,13,9,6,11,2,0,5};
    private int[] KeyPermutation=new int[]{0,4,8,12,1,5,9,13,2,6,10,14,3,7,11,15};
    
    public SBoxEncClass(){
           sBox=new int[]{14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7};  
           sBox_INV=new int[]{14,3,4,8,1,12,10,15,7,13,9,6,11,2,0,5};
    }
     public SBoxEncClass(int[] SBox,int[] SBoxINV){
         sBox=SBox;
         sBox_INV=SBoxINV;
    }
    
     public BitSet getSBoxValue(BitSet bSet) {
        int idx = HelperClass.toInteger(bSet, 4);
        return HelperClass.toBitSet(sBox[idx], 4);
    }
     
     public BitSet getSBoxInvValue(BitSet bSet) {
        int idx = HelperClass.toInteger(bSet, 4);
        return HelperClass.toBitSet(sBox_INV[idx], 4);
    }
    
     public BitSet permuteBSet(BitSet bSet) {
        BitSet bSetPerm = new BitSet(16);
        for (int i = 0; i < 16; i++) {
            bSetPerm.set(KeyPermutation[i], bSet.get(i));
        }
        return bSetPerm;
    }
    
  
     public BitSet getSBoxesValue(BitSet bSet) {
            BitSet result = new BitSet(16);
            for (int i = 0; i < 4; i++) {
              BitSet toSBox = new BitSet(4);
              for (int j = 0; j < 4; j++) {
                toSBox.set(j, bSet.get(i * 4 + j));
              }
              BitSet fromSBox = getSBoxValue(toSBox);
              for (int j = 0; j < 4; j++) {
                result.set(i * 4 + j, fromSBox.get(j));
              }
            }
            return result;
        }
     
      public BitSet getSBoxesValueOld(BitSet bSet) {
            BitSet result = new BitSet(16);
            int k=3;
            for (int i = 0; i < 4; i++) {
              BitSet toSBox = bSet.get(i*4, k);
              BitSet fromSBox = getSBoxValue(toSBox);
              for (int j = 0; j < 4; j++) {
                result.set(i * 4 + j, fromSBox.get(j));
              }
              k+=4;
            }
            return result;
        }
  
      
       public BitSet getSBoxesInvValueOld(BitSet bSet) {
            BitSet result = new BitSet(16);
            int k=3;
            for (int i = 0; i < 4; i++) {
              BitSet toSBox=new BitSet(4);
              toSBox = bSet.get(i*4, k);
              //System.out.println(" - "+HelperClass.toInteger(toSBox,4));
              BitSet fromSBox = getSBoxInvValue(toSBox);
              for (int j = 0; j < 4; j++) {
                result.set(i * 4 + j, fromSBox.get(j));
              }
              k+=4;
            }
            return result;
      }
        public BitSet getSBoxesInvValue(BitSet bSet) {
            BitSet result = new BitSet(16);
            for (int i = 0; i < 4; i++) {
              BitSet toSBox = new BitSet(4);
              for (int j = 0; j < 4; j++) {
                toSBox.set(j, bSet.get(i * 4 + j));
              }
              //System.out.println(" 1 "+ HelperClass.toInteger(toSBox,4));
              BitSet fromSBox = getSBoxInvValue(toSBox);
              for (int j = 0; j < 4; j++) {
                result.set(i * 4 + j, fromSBox.get(j));
              }
            }
            return result;
      }

        //will be delete
     public BitSet sBoxEncRound(BitSet pBSit, BitSet kBSit, boolean permute) {
            pBSit.xor(kBSit);
            pBSit = getSBoxesValue(pBSit);
            if (permute) {
                  pBSit = permuteBSet(pBSit);
              }
            return pBSit;
      }

         public BitSet sBoxEncRound(BitSet pBSit, BitSet kBSit) {
            pBSit.xor(kBSit);
            pBSit = getSBoxesValue(pBSit);
            return permuteBSet(pBSit);
          }

           
         //will be delete
        public BitSet sBoxDecRound(BitSet cBSit, BitSet kBSit, boolean permute) {
            if (permute) {
                    cBSit = permuteBSet(cBSit);
                }
            cBSit = getSBoxesInvValue(cBSit);
            cBSit.xor(kBSit);
            return cBSit;
      }

       public BitSet sBoxDecRound(BitSet cBSit, BitSet kBSit) {
            cBSit = permuteBSet(cBSit);
            cBSit = getSBoxesInvValue(cBSit);
            cBSit.xor(kBSit);
            return cBSit;
      }

       //sub round Keys should Be 5
       public BitSet sBoxEncBlock(BitSet pBSit, BitSet[] roundKeys) {
            pBSit = HelperClass.getCopy(pBSit, 16);
            for (int i = 0; i < 4; i++) {
              pBSit = sBoxEncRound(pBSit, roundKeys[i], i != 3);
            }
            pBSit.xor(roundKeys[4]);
            return pBSit;
      }

      public BitSet sBoxDecBlock(BitSet cBSit, BitSet[] roundKeys) {
            cBSit = HelperClass.getCopy(cBSit, 16);
            cBSit.xor(roundKeys[4]);
            for (int i = 3; i >= 0; i--) {
              cBSit = sBoxDecRound(cBSit, roundKeys[i], i != 3);
            }
            return cBSit;
      }
      
       public BitSet partialSBoxDecBlock(BitSet cBSit, BitSet roundKey,int roundNo) {
            cBSit = HelperClass.getCopy(cBSit, 16);
            cBSit.xor(roundKey);
            cBSit = sBoxDecRound(cBSit, roundKey, roundNo!= 3);
            return cBSit;
      }
       
       public void testSboxInv(){
           for(int i=0;i<16;i++){
               System.out.println("i = "+i+" SBox = "+sBox[i]+" sBoxInv = "+sBox_INV[sBox[i]]);
           }
       }
   
    public static void main(String[] args) {
      
       SBoxEncClass sb=new SBoxEncClass();
       BitSet s1= sb.getSBoxesValue(HelperClass.toBitSet(0x2345, 16));
       BitSet s2=sb.getSBoxesValueOld(HelperClass.toBitSet(0x5678, 16));
       System.out.println(HelperClass.toInteger(s1, 16)+" - "+HelperClass.toInteger(s1, 16)+"\n"); 
    
       BitSet s3= sb.getSBoxesInvValue(HelperClass.toBitSet(0x8831, 16));
       BitSet s4=sb.getSBoxesInvValueOld(HelperClass.toBitSet(0x8881, 16));
       System.out.println(HelperClass.toInteger(s3, 16)+" - "+HelperClass.toInteger(s4, 16)+"\n"); 
       
       sb.testSboxInv();
   }
   
}
