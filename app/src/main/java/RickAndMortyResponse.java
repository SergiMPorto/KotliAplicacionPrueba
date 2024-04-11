import com.google.gson.annotations.SerializedName;

data class RickAndMortyResponse (

        @SerializedName("info") val response:String,
        @SerializedName("results") val superheroes: List<SuperheroItemResponse>) {

    data class SuperheroItemResponse(
            @SerializedName("id") val superheroId: Int,
            @SerializedName("name") val name :String,
            @SerializedName("status") val status: String,
            @SerializedName("species") val species :String,
            @SerializedName("type") val type: String,
            @SerializedName("gender") val gender: String
    )


}

