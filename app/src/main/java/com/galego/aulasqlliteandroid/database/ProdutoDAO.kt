package com.galego.aulasqlliteandroid.database

import android.content.ContentValues
import android.content.Context
import android.provider.ContactsContract.Data
import android.util.Log
import com.galego.aulasqlliteandroid.model.Produto

class ProdutoDAO(context: Context) : IProdutoDAO {

    private val escrita = DataBaseHelper(context).writableDatabase
    private val leitura = DataBaseHelper(context).readableDatabase

    override fun salvar(produto: Produto): Boolean {
        //val sql = "INSERT INTO ${DataBaseHelper.TABELA_PRODUTOS} VALUES(null, '${produto.titulo}', 'Descricao...');"
        val valores = ContentValues()
        valores.put(DataBaseHelper.TITULO_PRODUTO, produto.titulo)
        valores.put(DataBaseHelper.DESCRICAO_PRODUTO, produto.descricao)

        try {
            // escrita.execSQL(sql)
            escrita.insert(
                DataBaseHelper.TABELA_PRODUTOS,
                null,
                valores
            )
            Log.i("info_db", "Sucesso ao inserir")
        } catch (e: Exception) {
            Log.i("info_db", "Erro ao inserir")
            return false
        }
        return true
    }

    override fun atualizar(produto: Produto): Boolean {
        /*
        val sql =
            "UPDATE ${DataBaseHelper.TABELA_PRODUTOS} " +
                    "SET ${DataBaseHelper.TITULO_PRODUTO}= '${produto.titulo}' " +
                    " WHERE ${DataBaseHelper.ID_PRODUTO}=${produto.idProduto} "
        */

        val valores = ContentValues()
        valores.put("${DataBaseHelper.TITULO_PRODUTO}", produto.titulo)
        valores.put("${DataBaseHelper.DESCRICAO_PRODUTO}", produto.descricao)
        val args = arrayOf(produto.idProduto.toString())

        try {
           // escrita.execSQL(sql)
            escrita.update(
                DataBaseHelper.TABELA_PRODUTOS,
                valores,
                "id_produto = ?",
                args

            )
            Log.i("info_db", "Sucesso ao atualizar")
        } catch (e: Exception) {
            Log.i("info_db", "Erro ao atualizar ${e.message}")
            return false
        }

        return true
    }

    override fun remover(idProduto: Int): Boolean {
       /* val sql =
            "DELETE FROM ${DataBaseHelper.TABELA_PRODUTOS} WHERE ${DataBaseHelper.ID_PRODUTO}=$idProduto "
*/
        val args = arrayOf(idProduto.toString())

        try {
            //escrita.execSQL(sql)
            escrita.delete(
                DataBaseHelper.TABELA_PRODUTOS,
                "${DataBaseHelper.ID_PRODUTO} = ?",
                args

            )
            Log.i("info_db", "Sucesso ao remover")
        } catch (e: Exception) {
            Log.i("info_db", "Erro ao remover ${e.message}")
            return false
        }
        return true
    }

    override fun listar(): List<Produto> {

        val listaProdutos = mutableListOf<Produto>()

        val sql = "SELECT * FROM ${DataBaseHelper.TABELA_PRODUTOS};"
        val cursor = leitura.rawQuery(sql, null)

        val indiceId = cursor.getColumnIndex("${DataBaseHelper.ID_PRODUTO}")
        val indiceTitulo = cursor.getColumnIndex("${DataBaseHelper.TITULO_PRODUTO}")
        val indiceDescricao = cursor.getColumnIndex("${DataBaseHelper.DESCRICAO_PRODUTO}")

        var texto = ""
        while (cursor.moveToNext()) {  //avança para o próximo
            val idProduto = cursor.getInt(indiceId)
            val tituloProduto = cursor.getString(indiceTitulo)
            val descricaoProduto = cursor.getString(indiceDescricao)


            listaProdutos.add(
                Produto(idProduto, tituloProduto, descricaoProduto)
            )

        }
        return listaProdutos
    }


}