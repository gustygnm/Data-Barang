package com.gnm.myapplication;

public class tes {
    public static void main(String[] args) {
        tes tes = new tes();
        tes.segitiga(5);
    }

    private void segitiga(int n) {
        for (int i = 0; i < n; i++) {
            for (int j = n-1; j >=i ; j--) {
                System.out.print(" ");
            }
            for (int j = 0; j <=i ; j++) {
                System.out.print("*");
            }
            for (int j = 0; j <i ; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
