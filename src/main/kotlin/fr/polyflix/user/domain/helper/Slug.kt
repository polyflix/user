package fr.polyflix.user.domain.helper

import java.text.Normalizer
import java.util.regex.Pattern

object Slug {
    private val NONLATIN: Pattern = Pattern.compile("[^\\w_-]")
    private val SEPARATORS: Pattern = Pattern.compile("[\\s\\p{Punct}&&[^-]]")

    fun make(input: String): String {
        val noseparators: String = SEPARATORS.matcher(input).replaceAll("-")
        val normalized: String = Normalizer.normalize(noseparators, Normalizer.Form.NFD)
        val slug: String = NONLATIN.matcher(normalized).replaceAll("")
        return slug.lowercase().replace("-{2,}".toRegex(), "-").replace("^-|-$".toRegex(), "")
    }
}