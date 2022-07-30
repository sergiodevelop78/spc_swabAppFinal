package com.spcswabapp.content.new_anexok

import com.spcswabapp.models.AnexoKItemsModel

class AnexoKItemsProvider {
    companion object {
        val anexoKItemsList = listOf<AnexoKItemsModel>(
            AnexoKItemsModel(
                1,
                "Opcion 1",
                "Durante el transporte",
                1,
                estado = 5
            ),
            AnexoKItemsModel(
                2,
                "Opcion 2",
                "Durante el transporte",
                1,
                estado=5

            ),
            AnexoKItemsModel(
                3,
                "Opcion 3",
                "Durante el transporte",
                1,
                estado=5
            )
        )
    }
}