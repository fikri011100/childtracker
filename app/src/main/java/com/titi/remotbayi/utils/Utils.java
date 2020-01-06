package com.titi.remotbayi.utils;

import androidx.annotation.Nullable;

public class Utils {
    public static String dbGetdata (String table, @Nullable String[] join, @Nullable String[] where, @Nullable String[] order){

        String selectQuery = "SELECT * FROM " + table;

        //================ JOIN ================//
        //usage
        //  String[] join = {
        //      TABLE_USER+","+KEY_ID+"="+ITEM_CAT_ID
        //  };

        //action
        if(join != null){
            for (int i = 0; i < join.length; i++){
                String[] joinSplit = join[i].split(",");
                String typeJoin = "INNER";
                if(joinSplit.length > 2){
                    typeJoin = joinSplit[2];
                }
                selectQuery = selectQuery + " "+typeJoin+" JOIN "+joinSplit[0]+" on "+joinSplit[1];
            }
        }
        //=====================================//

        //================= WHERE ==============//
        //usage
        //

        //action
        if(where != null){
            for (int i = 0; i < where.length; i++){
                selectQuery = selectQuery + (i == 0 ? " WHERE " : " AND ")+ where[i];
            }
        }
        //======================================//

        //================= ORDER ==============//
        //usage
        //  new String[]{"item_lastupdate", "DESC"}

        //action
        if(order != null){
            selectQuery = selectQuery + " ORDER BY " + order[0]+ " " + order[1];
        }
        //======================================//

        System.out.println(selectQuery);
        return selectQuery;
    }
}
