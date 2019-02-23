package com.example.user.wordbook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.user.wordbook.DataBase.DicContract;

/**
 * Created by User on 12/1/2017.
 */



    public class DicDbHelper extends SQLiteOpenHelper {

        private  static  final String DATABASE_NAME="dictionary.db";
        private  static  final int DATABASE_VERSION=1;

        public DicDbHelper(Context context)

        {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String SQL_CREATE_DIC_TABLE="CREATE TABLE "+ DicContract.DicEntry.TABLE_NAME+"("
                    + DicContract.DicEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + DicContract.DicEntry.C_WORD+" TEXT, "
                    +DicContract.DicEntry.C_TYPE+" TEXT, "
                    + DicContract.DicEntry.C_WORD_MEANING+" TEXT, "
                    +DicContract.DicEntry.C_WORD_EXAMPLE+" TEXT );";

            db.execSQL(SQL_CREATE_DIC_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
