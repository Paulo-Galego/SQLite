package com.galego.aulasqlliteandroid.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.sql.SQLException

class DataBaseHelper(context: Context): SQLiteOpenHelper(
    context,
    "loja.db",
    null,
    1
) {

    companion object{
        const val TABELA_PRODUTOS = "produtos"
        const val ID_PRODUTO = "id_produto"
        const val TITULO_PRODUTO = "titulo"
        const val DESCRICAO_PRODUTO = "descricao"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        Log.i("info_db", "Executou OnCreate")
        val sql = "CREATE TABLE IF NOT EXISTS $TABELA_PRODUTOS (" +
                "$ID_PRODUTO INTEGER not NULL PRIMARY key AUTOINCREMENT," +
                "$TITULO_PRODUTO VARCHAR(100)," +
                "$DESCRICAO_PRODUTO TEXT" +
                ");"

        try {
            db?.execSQL(sql)
            Log.i("info_db", "Sucesso ao criar a tabela")
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_db", "Erro ao criar a tabela")
        }


    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        Log.i("info_db", "Executou onUpgrade")
    }

}