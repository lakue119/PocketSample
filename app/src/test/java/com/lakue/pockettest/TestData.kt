package com.lakue.pockettest

import com.lakue.pockettest.model.ResponsePocket
import com.lakue.pockettest.model.ResultPocket

val testPocket = ResponsePocket(
    1118,
    "https://pokeapi.co/api/v2/pokemon?offset=2&limit=1",
    "https://pokeapi.co/api/v2/pokemon?offset=0&limit=1",
    listOf(ResultPocket("ivysaur","https://pokeapi.co/api/v2/pokemon/2/"))
)
