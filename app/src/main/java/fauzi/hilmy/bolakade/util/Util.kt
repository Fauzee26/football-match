package fauzi.hilmy.bolakade.util

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import fauzi.hilmy.bolakade.R
import fauzi.hilmy.bolakade.api.ApiClient
import fauzi.hilmy.bolakade.model.League
import fauzi.hilmy.bolakade.model.logo.ResponseLogo
import fauzi.hilmy.bolakade.model.logo.TeamsItemm
import fauzi.hilmy.bolakade.util.MyConstant.API_KEY
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class Util {

    companion object {
        fun formatDate(date: String?): String {
            return if (date.isNullOrBlank()) {
                date.orEmpty()
            } else {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val data: Date? = dateFormat.parse(date)
                val formatted = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault())
                formatted.format(data)
            }
        }

        fun timeFormat(time: String?): String {
            if (time.isNullOrBlank()) {
                return time.orEmpty()
            } else {
                val hourWib = time!!.substring(0, 2).toInt() + 7
                val hourr: Int
                val hourrr: String
                if (hourWib > 23) {
                    hourr = hourWib - 24
                    hourrr = "0" + hourr.toString()
                } else {
                    hourr = hourWib
                    hourrr = hourr.toString()
                }
                val minuteWib = time.substring(3, 5)
                return "$hourrr:$minuteWib WIB"
            }
        }

        fun loadBadge(idTeam: String?, imageView: ImageView) {
            val callRetro = ApiClient().getInstance().getDetailTeam(idTeam)
            callRetro.enqueue(object : retrofit2.Callback<ResponseLogo> {
                override fun onResponse(call: Call<ResponseLogo>, response: Response<ResponseLogo>) {
                    Log.v("Cek Data Gambar", response.body().toString())
                    if (response.isSuccessful) {
                        val data: List<TeamsItemm> = response.body()!!.logo
                        data.forEach {
                            Picasso.get().load(it.strTeamBadge).placeholder(R.drawable.ball).into(imageView)
                        }

                    } else {

                    }
                }

                override fun onFailure(call: Call<ResponseLogo>, t: Throwable) {
                    Log.e("Error: Load Badge == ", t.message)
                }
            })
        }

        fun getListLeague(context: Context, league: MutableList<League> = mutableListOf()) : List<League> {
            val leagueName = context.resources.getStringArray(R.array.leagueNamearray)
            val leagueId = context.resources.getStringArray(R.array.leagueIdarray)

            league.clear()

            for (i in leagueName.indices) {
                league.add(League(leagueName[i], leagueId[i]))
            }

            return league
        }

        fun formatPlayer(players: String?): String {
            return if (players.isNullOrBlank()) {
                players.orEmpty()
//            } else if (players!!.length == 1) {
//                val formatted = players.replace("; ", "")
//                formatted
            } else {
                val formatted = players!!.replace(";", ",")
                formatted
            }
        }

        fun formatNumPlayer(players: String?): String {
            return if (players.isNullOrBlank()) {
                players.orEmpty()
            } else {
                var result = ""

                players!!.split(';').forEach {
                    result += it.split(":").asReversed().reduce { sum, element -> sum + " " + element } + "\n"
                }
                result
            }
        }
    }
}

fun String.dateTimeToFormat(format: String = "yyyy-MM-dd HH:mm:ss"): Long {

    val formatter = SimpleDateFormat(format, Locale.ENGLISH)
    val date = formatter.parse(this)

    return date.time
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}