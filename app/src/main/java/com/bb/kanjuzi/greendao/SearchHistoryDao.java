package com.bb.kanjuzi.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.bb.kanjuzi.bean.SearchHistory;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SEARCH_HISTORY".
*/
public class SearchHistoryDao extends AbstractDao<SearchHistory, String> {

    public static final String TABLENAME = "SEARCH_HISTORY";

    /**
     * Properties of entity SearchHistory.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Name = new Property(0, String.class, "name", true, "NAME");
        public final static Property Href = new Property(1, String.class, "href", false, "HREF");
    }


    public SearchHistoryDao(DaoConfig config) {
        super(config);
    }
    
    public SearchHistoryDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SEARCH_HISTORY\" (" + //
                "\"NAME\" TEXT PRIMARY KEY NOT NULL ," + // 0: name
                "\"HREF\" TEXT);"); // 1: href
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SEARCH_HISTORY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SearchHistory entity) {
        stmt.clearBindings();
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(1, name);
        }
 
        String href = entity.getHref();
        if (href != null) {
            stmt.bindString(2, href);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SearchHistory entity) {
        stmt.clearBindings();
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(1, name);
        }
 
        String href = entity.getHref();
        if (href != null) {
            stmt.bindString(2, href);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public SearchHistory readEntity(Cursor cursor, int offset) {
        SearchHistory entity = new SearchHistory( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // name
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // href
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, SearchHistory entity, int offset) {
        entity.setName(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setHref(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    @Override
    protected final String updateKeyAfterInsert(SearchHistory entity, long rowId) {
        return entity.getName();
    }
    
    @Override
    public String getKey(SearchHistory entity) {
        if(entity != null) {
            return entity.getName();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(SearchHistory entity) {
        return entity.getName() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
