package fauzi.hilmy.bola_kade.util

import android.content.Context
import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Picasso
import fauzi.hilmy.bola_kade.R
import fauzi.hilmy.bola_kade.api.ApiClient
import fauzi.hilmy.bola_kade.model.ResponseLastNext
import fauzi.hilmy.bola_kade.model.ResponseLogo
import fauzi.hilmy.bola_kade.model.TeamsItem
import fauzi.hilmy.bola_kade.util.MyConstant.Companion.API_KEY
import retrofit2.Call
import retrofit2.Response
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import java.util.Date

class Util {

    companion object {
        fun FormatDate(date: String?): String {
            if (date.isNullOrBlank()) {
                return date.orEmpty()
            } else {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val data: Date? = dateFormat.parse(date)
                val formatted = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault())
                return formatted.format(data)
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
                val minuteWib = time!!.substring(3, 5)
                return "$hourrr" + ":" + "${minuteWib}" + " WIB"
            }
        }

        fun loadBadge(idTeam: String?, imageView: ImageView, context: Context) {
            val callRetro = ApiClient().getInstance().getBadgeTeam(API_KEY, idTeam)

            callRetro.enqueue(object : retrofit2.Callback<ResponseLogo> {
                override fun onResponse(call: Call<ResponseLogo>, response: Response<ResponseLogo>) {
                    if (response.isSuccessful) {
                        val data: List<TeamsItem> = response.body()!!.teams as List<TeamsItem>
                        data.forEach {
                            Picasso.with(context).load(it.strTeamBadge).placeholder(R.drawable.ball).into(imageView)
                        }

                    } else {

                    }
                }

                override fun onFailure(call: Call<ResponseLogo>, t: Throwable) {
                    Log.e("Error: Load Badge == ", t.message)
                }
            })
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


    fun LoadDetail(idEvents: String?) {
        val callLeague = ApiClient().getInstance().getDetail(API_KEY, idEvents)
        callLeague.enqueue(object : retrofit2.Callback<ResponseLastNext> {
            override fun onResponse(call: Call<ResponseLastNext>, response: Response<ResponseLastNext>) {
                if (response.isSuccessful) {
                    val dataLastNext = response.body()!!.events

                }
            }

            override fun onFailure(call: Call<ResponseLastNext>, t: Throwable) {
                Log.e("Error: Load Last == ", t.message)
            }
        })
    }

}