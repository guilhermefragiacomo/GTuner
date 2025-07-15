package br.edu.ifsp.dmo.gtuner.data.model

import java.util.UUID

class Sheet (
    val id: String,
    val name: String,
    val url: String,
    val author: String,
    val arrangment: String
) {
    companion object {
        fun create_new_sheet(
            url: String,
            name: String,
            author: String,
            arrangment: String
        ): Sheet {
            return Sheet(
                url = url,
                id = generate_sheet_id(),
                name = name,
                author = author,
                arrangment = arrangment
            )
        }

        fun generate_sheet_id() : String {
            return "C" + UUID.randomUUID().toString().replace("-", "").take(10)
        }
    }

    override fun toString(): String {
        return id + " " + name + " " + url + " " + author + " " + arrangment
    }
}