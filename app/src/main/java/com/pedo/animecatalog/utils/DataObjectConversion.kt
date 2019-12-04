package com.pedo.animecatalog.utils

import com.pedo.animecatalog.database.AnimeModel
import com.pedo.animecatalog.domain.Anime
import com.pedo.animecatalog.network.JikanSeasonResponse
import com.pedo.animecatalog.network.JikanTopResponse
import com.pedo.animecatalog.network.NetworkAnime

//domain as database model
fun Anime.asDatabaseModel() : AnimeModel{
    val titleSynonymsString = convertListStringToString(titleSynonyms)
    val studiosString = convertListStringToString(studios)
    val genresString = convertListStringToString(genres)
    val openingThemesString = convertListStringToString(openingThemes)
    val endingThemesString = convertListStringToString(endingThemes)

    return AnimeModel(
        id = id,
        url = url,
        imageUrl = imageUrl,
        trailerUrl = trailerUrl,
        title = title,
        titleEnglish = titleEnglish,
        titleJapanese = titleJapanese,
        titleSynonyms = titleSynonymsString,
        type = type,
        source = source,
        episodes = episodes,
        status = status,
        airing = airing,
        aired = aired,
        duration = duration,
        rating = rating,
        score = score,
        scoredBy = scoredBy,
        rank = rank,
        popularity = popularity,
        members = members,
        favorites = favorites,
        synopsis = synopsis,
        background = background,
        premiered = premiered,
        broadcast = broadcast,
        studios = studiosString,
        genres = genresString,
        openingThemes = openingThemesString,
        endingThemes = endingThemesString
    )
}

//database model as domain
fun AnimeModel.asDomainModel() : Anime{
    val titleSynonymsList = convertStringToListString(titleSynonyms)
    val studiosList = convertStringToListString(studios)
    val genresList = convertStringToListString(genres)
    val openingThemesList = convertStringToListString(openingThemes)
    val endingThemesList = convertStringToListString(endingThemes)

    return Anime(
        id = id,
        url = url,
        imageUrl = imageUrl,
        trailerUrl = trailerUrl,
        title = title,
        titleEnglish = titleEnglish,
        titleJapanese = titleJapanese,
        titleSynonyms = titleSynonymsList,
        type = type,
        source = source,
        episodes = episodes,
        status = status,
        airing = airing,
        aired = aired,
        duration = duration,
        rating = rating,
        score = score,
        scoredBy = scoredBy,
        rank = rank,
        popularity = popularity,
        members = members,
        favorites = favorites,
        synopsis = synopsis,
        background = background,
        premiered = premiered,
        broadcast = broadcast,
        studios = studiosList,
        genres = genresList,
        openingThemes = openingThemesList,
        endingThemes = endingThemesList
    )
}

//network model as domain
fun NetworkAnime.asDomainModel() : Anime{
    val airedString = aired?.string
    val studioList = convertStudiosToListString(studios)
    val genreList = convertGenresToListString(genres)

    return Anime(
        id = id,
        url = url,
        imageUrl = imageUrl,
        trailerUrl = trailerUrl,
        title = title,
        titleEnglish = titleEnglish,
        titleJapanese = titleJapanese,
        titleSynonyms = titleSynonyms,
        type = type,
        source = source,
        episodes = episodes ?: 0,
        status = status,
        airing = airing,
        aired = airedString,
        duration = duration,
        rating = rating,
        score = score ?: 0.0,
        scoredBy = scoredBy,
        rank = rank ?: 0,
        popularity = popularity,
        members = members,
        favorites = favorites,
        synopsis = synopsis,
        background = background,
        premiered = premiered,
        broadcast = broadcast,
        studios = studioList,
        genres = genreList,
        openingThemes = openingThemes,
        endingThemes = endingThemes
    )
}

//network model as database
fun NetworkAnime.asDatabaseModel() : AnimeModel{
    val airedString = aired?.string
    val titleSynonymsList = convertListStringToString(titleSynonyms)
    val studioString = convertStudiosToString(studios)
    val genreString = convertGenresToString(genres)
    val openingThemesString = convertListStringToString(openingThemes)
    val endingThemesString = convertListStringToString(endingThemes)

    return AnimeModel(
        id = id,
        url = url,
        imageUrl = imageUrl,
        trailerUrl = trailerUrl,
        title = title,
        titleEnglish = titleEnglish,
        titleJapanese = titleJapanese,
        titleSynonyms = titleSynonymsList,
        type = type,
        source = source,
        episodes = episodes ?: 0,
        status = status,
        airing = airing,
        aired = airedString,
        duration = duration,
        rating = rating,
        score = score ?: 0.0,
        scoredBy = scoredBy,
        rank = rank  ?: 0,
        popularity = popularity,
        members = members,
        favorites = favorites,
        synopsis = synopsis,
        background = background,
        premiered = premiered,
        broadcast = broadcast,
        studios = studioString,
        genres = genreString,
        openingThemes = openingThemesString,
        endingThemes = endingThemesString
    )
}

//list network model as list domain model
fun JikanTopResponse.asDomainModel() : List<Anime>{
    return animeList.map{
        Anime(
            id = it.id,
            url = it.url,
            imageUrl = it.imageUrl,
            trailerUrl = it.trailerUrl,
            title = it.title,
            titleEnglish = it.titleEnglish,
            titleJapanese = it.titleJapanese,
            titleSynonyms = it.titleSynonyms,
            type = it.type,
            source = it.source,
            episodes = it.episodes ?: 0,
            status = it.status,
            airing = it.airing,
            aired = it.aired?.string,
            duration = it.duration,
            rating = it.rating,
            score = it.score ?: 0.0,
            scoredBy = it.scoredBy,
            rank = it.rank  ?: 0,
            popularity = it.popularity,
            members = it.members,
            favorites = it.favorites,
            synopsis = it.synopsis,
            background = it.background,
            premiered = it.premiered,
            broadcast = it.broadcast,
            studios = convertStudiosToListString(it.studios),
            genres = convertGenresToListString(it.genres),
            openingThemes = it.openingThemes,
            endingThemes = it.endingThemes
        )
    }
}

//list network model (season) as list domain model
fun JikanSeasonResponse.asDomainModel() : List<Anime>{
    return animeList.map{
        Anime(
            id = it.id,
            url = it.url,
            imageUrl = it.imageUrl,
            trailerUrl = it.trailerUrl,
            title = it.title,
            titleEnglish = it.titleEnglish,
            titleJapanese = it.titleJapanese,
            titleSynonyms = it.titleSynonyms,
            type = it.type,
            source = it.source,
            episodes = it.episodes ?: 0,
            status = it.status,
            airing = it.airing,
            aired = it.aired?.string,
            duration = it.duration,
            rating = it.rating,
            score = it.score ?: 0.0,
            scoredBy = it.scoredBy,
            rank = it.rank ?: 0,
            popularity = it.popularity,
            members = it.members,
            favorites = it.favorites,
            synopsis = it.synopsis,
            background = it.background,
            premiered = it.premiered,
            broadcast = it.broadcast,
            studios = convertStudiosToListString(it.studios),
            genres = convertGenresToListString(it.genres),
            openingThemes = it.openingThemes,
            endingThemes = it.endingThemes
        )
    }
}

fun List<AnimeModel>.asDomainModel() : List<Anime>{
        return map{
            Anime(
                id = it.id,
                url = it.url,
                imageUrl = it.imageUrl,
                trailerUrl = it.trailerUrl,
                title = it.title,
                titleEnglish = it.titleEnglish,
                titleJapanese = it.titleJapanese,
                titleSynonyms = convertStringToListString(it.titleSynonyms),
                type = it.type,
                source = it.source,
                episodes = it.episodes,
                status = it.status,
                airing = it.airing,
                aired = it.aired,
                duration = it.duration,
                rating = it.rating,
                score = it.score,
                scoredBy = it.scoredBy,
                rank = it.rank,
                popularity = it.popularity,
                members = it.members,
                favorites = it.favorites,
                synopsis = it.synopsis,
                background = it.background,
                premiered = it.premiered,
                broadcast = it.broadcast,
                studios = convertStringToListString(it.studios),
                genres = convertStringToListString(it.genres),
                openingThemes = convertStringToListString(it.openingThemes),
                endingThemes = convertStringToListString(it.endingThemes)
            )
        }
}

//list network model as list domain model
fun JikanTopResponse.asDatabaseModel() : List<AnimeModel>{
    return animeList.map{
        AnimeModel(
            id = it.id,
            url = it.url,
            imageUrl = it.imageUrl,
            trailerUrl = it.trailerUrl,
            title = it.title,
            titleEnglish = it.titleEnglish,
            titleJapanese = it.titleJapanese,
            titleSynonyms = convertListStringToString(it.titleSynonyms),
            type = it.type,
            source = it.source,
            episodes = it.episodes ?: 0,
            status = it.status,
            airing = it.airing,
            aired = it.aired?.string,
            duration = it.duration,
            rating = it.rating,
            score = it.score ?: 0.0,
            scoredBy = it.scoredBy,
            rank = it.rank ?: 0,
            popularity = it.popularity,
            members = it.members,
            favorites = it.favorites,
            synopsis = it.synopsis,
            background = it.background,
            premiered = it.premiered,
            broadcast = it.broadcast,
            studios = convertStudiosToString(it.studios),
            genres = convertGenresToString(it.genres),
            openingThemes = convertListStringToString(it.openingThemes),
            endingThemes = convertListStringToString(it.endingThemes)
        )
    }
}