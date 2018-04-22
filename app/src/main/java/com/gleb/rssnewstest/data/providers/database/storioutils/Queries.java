package com.gleb.rssnewstest.data.providers.database.storioutils;

import com.pushtorefresh.storio3.sqlite.queries.DeleteQuery;
import com.pushtorefresh.storio3.sqlite.queries.Query;

public class Queries {

    public static Query getQueryList(String tableName) {
        return Query.builder()
                .table(tableName)
                .build();
    }

    public static DeleteQuery deleteQueryItem(String tableName, String columnName, Long id) {
        return DeleteQuery.builder()
                .table(tableName)
                .where(columnName + " = ?")
                .whereArgs(id)
                .build();
    }
}
