/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package descryptoanalysis;


/**
 *
 * @author Administrator
 */
public class CPByte {
    
    private boolean[] sByte=null;
    public int len;
    public CPByte(int numberOfBits){
       sByte=new boolean[numberOfBits];
       len=numberOfBits;
       for(int i=0;i<len;i++)
            sByte[i]=false;
    }
  
   public CPByte(int numberOfBits,int value){
       sByte=new boolean[numberOfBits];
       len=numberOfBits;
       int idx = numberOfBits - 1;
       while (value > 0) {
          sByte[idx--]= (value % 2 == 1);
          value /= 2;
        }
   }
     
   public void Set(int index,boolean value){
        if(index>=len || index<0)
           throw new IllegalArgumentException("Index should be less than the lenght");
        
        sByte[index]=value;
    }
    
    public boolean Get(int index){
        if(index>=len || index<0)
           throw new IllegalArgumentException("Index should be less than the lenght");
        
        return sByte[index];
    }
    
    public CPByte Get(int fromIndex,int toIndex){
        int l=toIndex-fromIndex;
        CPByte cpByte=new CPByte(l);
        for(int i=0;i<len;i++){
         cpByte.Set(i,sByte[fromIndex]);
         fromIndex++;
        }            
        return cpByte;
    }
    
     public CPByte Xor(CPByte cpByte){
         if(cpByte.len != len)
             throw new IllegalArgumentException("Bytes should be with the same lenght");
         
         CPByte xorByte=new CPByte(len);
         for(int i=0;i<len;i++){
             xorByte.Set(i,(cpByte.Get(i) && !sByte[i]) || (sByte[i] && !cpByte.Get(i)));
         }
         return xorByte;
     }
     
    public static void main(String[] args) {
         CPByte b=new CPByte(16,8);
         for(int i=0;i<b.len;i++){
             System.out.println(b.Get(i));
         }
     }
}
