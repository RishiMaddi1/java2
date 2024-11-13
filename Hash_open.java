package dsa;

import java.util.Scanner;

public class Hash_open {

    public static void main(String[] args) {
        int probes, a[]=new int[11];
        Scanner s= new Scanner(System.in);
        System.out.println(" Enter the key");
        int index, key=s.nextInt();
        while(key!=0) {
            index=key % 10;
            probes=1;
            int t=index;
            while (a[index]!=0) {
                index=(t +(probes*probes)) % 10;
                probes=probes+1;
            }

            a[index]=key;
            System.out.println(" Enter the key (0 to terminate)");
            key=s.nextInt();
        }
        int i=0;
        while(i!=10) {
            System.out.println("location/ bucket "+i+"----->"+a[i]);
            i++;
        }
    }
}