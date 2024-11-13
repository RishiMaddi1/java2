package dsa;

import java.util.Scanner;

class nodde
{int key;
nodde next;
nodde()
{next= null;}
  nodde(int k)
  {
	  key=k;
	  next=null;
  }
}
public class hash1 {
 
	public static void main(String[] args) {
		nodde a[]=new nodde[10];
		Scanner s= new Scanner(System.in);
		System.out.println(" Enter the key");
		int index, key=s.nextInt();
		while(key!=0)
		{ index=key % 10;
		  if (a[index]==null)
		  a[index]=new nodde(key);
		  else
		  {
			  nodde t=a[index];
			  a[index]=new nodde(key);
			  a[index].next=t;
		  }
			  System.out.println(" Enter the key (0 to terminate)");
		    key=s.nextInt();
		}
		int i=0;
		while(i!=10)
		{ if (a[i]!=null)
			System.out.println("location/ bucket "+i+"----->"+a[i].key);
		else
			System.out.println("location/ bucket "+i+"----->null");
		i++;
		}
	}
 
}