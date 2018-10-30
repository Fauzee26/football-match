package fauzi.hilmy.bolakade.matches.next

import fauzi.hilmy.bolakade.api.ApiRepository
import fauzi.hilmy.bolakade.util.CoroutineContextProvider
import com.google.gson.Gson
import fauzi.hilmy.bolakade.api.TheSportDBApi
import fauzi.hilmy.bolakade.model.match.ResponsePrevNext
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class NextMatchPresenter(private val view: NextMatchView,
                         private val apiRepository: ApiRepository,
                         private val gson: Gson,
                         private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getNextList(league: String?) {
        view.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getNext(league)),
                        ResponsePrevNext::class.java
                )
            }
            view.showTeamList(data.await().events)
            view.hideLoading()
        }
    }
}