package com.chanakyabhardwaj.bdgt;

import java.util.ArrayList;

/**
 * Created by chanakya.bharwaj on 17/01/16.
 */
public class ExpenseCategory {
    public static ArrayList<String> categories = new ArrayList<String>();
    public static void init() {
        categories.add("breakfast");
        categories.add("lunch");
        categories.add("dinner");
        categories.add("train tickets");
        categories.add("groceries");
        categories.add("clothes");
        categories.add("coffee");
        categories.add("beer");
        categories.add("drinks");
    }
}
