package com.bb.kanjuzi.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.bb.kanjuzi.bean.DetailPageBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DETAIL_PAGE_BEAN".
*/
public class DetailPageBeanDao extends AbstractDao<DetailPageBean, String> {

    public static final String TABLENAME = "DETAIL_PAGE_BEAN";

    /**
     * Properties of entity DetailPageBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property Article = new Property(1, String.class, "article", false, "ARTICLE");
        public final static Property Writer = new Property(2, String.class, "writer", false, "WRITER");
        public final static Property Content = new Property(3, String.class, "content", false, "CONTENT");
        public final static Property Liked = new Property(4, boolean.class, "liked", false, "LIKED");
    }


    public DetailPageBeanDao(DaoConfig config) {
        super(config);
    }
    
    public DetailPageBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DETAIL_PAGE_BEAN\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"ARTICLE\" TEXT," + // 1: article
                "\"WRITER\" TEXT," + // 2: writer
                "\"CONTENT\" TEXT," + // 3: content
                "\"LIKED\" INTEGER NOT NULL );"); // 4: liked
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DETAIL_PAGE_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DetailPageBean entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String article = entity.getArticle();
        if (article != null) {
            stmt.bindString(2, article);
        }
 
        String writer = entity.getWriter();
        if (writer != null) {
            stmt.bindString(3, writer);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(4, content);
        }
        stmt.bindLong(5, entity.getLiked() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DetailPageBean entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String article = entity.getArticle();
        if (article != null) {
            stmt.bindString(2, article);
        }
 
        String writer = entity.getWriter();
        if (writer != null) {
            stmt.bindString(3, writer);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(4, content);
        }
        stmt.bindLong(5, entity.getLiked() ? 1L: 0L);
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public DetailPageBean readEntity(Cursor cursor, int offset) {
        DetailPageBean entity = new DetailPageBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // article
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // writer
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // content
            cursor.getShort(offset + 4) != 0 // liked
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DetailPageBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setArticle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setWriter(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setContent(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setLiked(cursor.getShort(offset + 4) != 0);
     }
    
    @Override
    protected final String updateKeyAfterInsert(DetailPageBean entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(DetailPageBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(DetailPageBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
