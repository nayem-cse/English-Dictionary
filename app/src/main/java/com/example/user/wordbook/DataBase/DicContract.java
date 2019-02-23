package com.example.user.wordbook.DataBase;

import android.provider.BaseColumns;

/**
 * Created by User on 12/1/2017.
 */

public class DicContract {

    private DicContract()
    {

    }
    public  static  final class DicEntry  implements BaseColumns {

        public  final static String TABLE_NAME="WordBook";
        public  final static String _ID=BaseColumns._ID;
        public  final static String C_WORD="word";
        public  final static String C_WORD_MEANING="meaning";
        public  final static String C_TYPE="type";
        public  final static String C_WORD_EXAMPLE="example";

    }


}
