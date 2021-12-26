package com.example.eveningswipe.ui.filmswipe

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eveningswipe.httpRequests.FilterByGroupId
import com.example.eveningswipe.httpRequests.HttpRequests

const val IMG_BASE_URL = "https://image.tmdb.org/t/p/original"
const val BASE_URL = "http://192.168.178.35:8080/api/filter/byid/"
    //"http://localhost:8080/api/movie/details/" --> doesn't work because it's local
    //instead use: "http://YOUR_IP_ADRESS:8080/api/movie/details/"
var MovieById = ArrayList<FilterByGroupId>()
var i: Int = 0

class SwipeViewModel: ViewModel() {
    val movieTitle: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val movieText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun nextMovie(imgView: ImageView) {
        var url = BASE_URL + "5"
        // + element in list with movie Id
        MovieById = HttpRequests.getMovieById(url)
        movieTitle.value = MovieById.get(0).genre_1
        //get(i).title
        //var imgURL = IMG_BASE_URL + MovieById.poster_path
        //Picasso.get().load(imgURL).into(imgView)
        i+=1
    }

    fun match() {
        // movie ranking +1
        // endpoint rate movie
    }
}

//dummy list
var dummy = listOf(
    "tt0120667",
    "tt0120737",
    "tt0120755",
    "tt0120804",
    "tt0120903",
    "tt0121765",
    "tt0121766",
    "tt0132910",
    "tt0133152",
    "tt0134847",
    "tt0141926",
    "tt0145487",
    "tt0146316",
    "tt0151150",
    "tt0151299",
    "tt0159273",
    "tt0159378",
    "tt0160009",
    "tt0160275",
    "tt0164052",
    "tt0164184",
    "tt0165929",
    "tt0167190",
    "tt0167260",
    "tt0167261",
    "tt0168504",
    "tt0170452",
    "tt0171827",
    "tt0172156",
    "tt0172495",
    "tt0173840",
    "tt0177971",
    "tt0179626",
    "tt0179987",
    "tt0181689",
    "tt0181852",
    "tt0183790",
    "tt0184858",
    "tt0184894",
    "tt0186566",
    "tt0187078",
    "tt0187393",
    "tt0187738",
    "tt0190332",
    "tt0190865",
    "tt0192614",
    "tt0194104",
    "tt0194704",
    "tt0199753",
    "tt0199898",
    "tt0202677",
    "tt0205380",
    "tt0206634",
    "tt0207198",
    "tt0207826",
    "tt0208198",
    "tt0208988",
    "tt0209163",
    "tt0210223",
    "tt0211938",
    "tt0212346",
    "tt0212867",
    "tt0212879",
    "tt0213149",
    "tt0214728",
    "tt0215715",
    "tt0215824",
    "tt0216216",
    "tt0216651",
    "tt0218533",
    "tt0218817",
    "tt0220623",
    "tt0227194",
    "tt0227445",
    "tt0228750",
    "tt0230055",
    "tt0232500",
    "tt0233142",
    "tt0233469",
    "tt0233600",
    "tt0234000",
    "tt0234215",
    "tt0236027",
    "tt0236167",
    "tt0237534",
    "tt0238380",
    "tt0238552",
    "tt0238883",
    "tt0239235",
    "tt0239341",
    "tt0239655",
    "tt0240510",
    "tt0242445",
    "tt0242519",
    "tt0242653",
    "tt0243278",
    "tt0243415",
    "tt0243573",
    "tt0243609",
    "tt0243876",
    "tt0244479",
    "tt0244884",
    "tt0245562",
    "tt0245803",
    "tt0245844",
    "tt0246460",
    "tt0246544",
    "tt0247944",
    "tt0248012",
    "tt0248185",
    "tt0249371",
    "tt0250687",
    "tt0251094",
    "tt0251433",
    "tt0251756",
    "tt0252277",
    "tt0252503",
    "tt0253556",
    "tt0265086",
    "tt0265226",
    "tt0266048",
    "tt0266308",
    "tt0266408",
    "tt0266465",
    "tt0266697",
    "tt0266987",
    "tt0267287",
    "tt0269043",
    "tt0269217",
    "tt0271748",
    "tt0271946",
    "tt0272020",
    "tt0272105",
    "tt0273516",
    "tt0273870",
    "tt0274188",
    "tt0274830",
    "tt0275083",
    "tt0275277",
    "tt0275688",
    "tt0275773",
    "tt0276210",
    "tt0276816",
    "tt0277327",
    "tt0277386",
    "tt0277434",
    "tt0277941",
    "tt0278274",
    "tt0278291",
    "tt0278351",
    "tt0279112",
    "tt0280380",
    "tt0280427",
    "tt0280486",
    "tt0280490",
    "tt0280609",
    "tt0280990",
    "tt0281019",
    "tt0281718"
)