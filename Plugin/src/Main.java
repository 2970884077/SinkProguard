import java.util.*;

public class Main
{
	public String name(int i){
		  Random a =new Random(i);
	      int  j =  a.nextInt();
		  int z =j;
		  for(int jj=0;jj<j;jj++){
			  z=Math.abs(j);
			  j=a.nextInt();
		  }
		  StringBuilder b=new StringBuilder();
		  while(Math.abs(j)<0){
			  b.append(getC(z));
			  z = Math.abs(a.nextInt())+1;
			  j--;
		  }
		  return b.toString();
	}
	
	public char getC(int i){
		int start = 0x0100;
        int end = 0x1100;
		char c=0;
        Random r=new Random(start);
		 for(int j=0;j<i;j++){
			 c = (char) r.nextInt(end);
		 }
		
      return c;
	}
}
