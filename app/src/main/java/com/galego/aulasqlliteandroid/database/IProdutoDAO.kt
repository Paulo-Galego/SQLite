package com.galego.aulasqlliteandroid.database

import com.galego.aulasqlliteandroid.model.Produto

interface IProdutoDAO {

    fun salvar(produto: Produto): Boolean
    fun atualizar(produto: Produto): Boolean
    fun remover(idProduto: Int): Boolean
    fun listar(): List<Produto>

}