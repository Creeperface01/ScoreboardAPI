package gt.creeperface.nukkit.scoreboardapi

import cn.nukkit.Player
import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
import cn.nukkit.event.player.PlayerJoinEvent
import gt.creeperface.nukkit.scoreboardapi.scoreboard.*

internal class Test(private val api: ScoreboardAPI) : Listener {

    init {
        api.server.pluginManager.registerEvents(this, api)
    }

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        val p = e.player

        test2(p)
    }

    fun test1(p: Player) {
        val scoreboard = FakeScoreboard()
        val obj = Objective("scoreboard", ObjectiveCriteria("dummy", false))
        val dobj = DisplayObjective(obj, ObjectiveSortOrder.DESCENDING, ObjectiveDisplaySlot.SIDEBAR)
        obj.displayName = "  §fＭｉｎｅ   "
        obj.setScore(1, "§lsite.ru", 1)
        obj.setScore(2, " ", 2)
        obj.setScore(3, "§lKills: §8>>§7 null", 3)
        obj.setScore(4, "§lBalance §8>>§7 " + Math.random() * 100, 4)
        obj.setScore(5, "§l§bStats ", 5)
        obj.setScore(6, "  ", 6)
        obj.setScore(7, "§lGroup §8>>§7 " + Math.random() * 100, 7)
        obj.setScore(8, "§lNickname §8>>§7 " + p.name, 8)
        obj.setScore(9, "§l§bPlayer", 9)
        obj.setScore(10, "   ", 10)

        scoreboard.objective = dobj

        scoreboard.addPlayer(p)

        var time = 2
        api.server.scheduler.scheduleRepeatingTask(api, {
            when (time) {
                2 -> {
                    obj.setScore(4, "§lBalance §8>>§7 " + Math.random() * 100, 4)
                    obj.setScore(1, "§lsite.ru", 1)
                    scoreboard.update()
                }
                1 -> {
                    obj.setScore(4, "§lBalance §8>>§7 " + Math.random() * 100, 4)
                    obj.setScore(1, "§l§9site.ru", 1)
                    scoreboard.update()
                }
                0 -> {
                    obj.setScore(4, "§lBalance §8>>§7 " + Math.random() * 100, 4)
                    obj.setScore(1, "§l§dsite.ru", 1)
                    scoreboard.update()
                }
            }
            time = 2
        }, 40)
    }

    fun test2(p: Player) {
        val scoreboard = ScoreboardAPI.builder().build()
        scoreboard.setDisplayName("Test name")

        fun text1() {
            for (i in 0 until 5) {
                scoreboard.setScore(i.toLong(), "$i", i)
            }
        }

        fun text2() {
            for (i in 0 until 5) {
                scoreboard.setScore(i.toLong(), "${i * 10}", i)
            }
        }

        var counter = -5
        api.server.scheduler.scheduleDelayedRepeatingTask(api, {
            p.sendMessage(counter.toString())
            when (counter++) {
                5 -> {
                    text1()
                    scoreboard.update()
                }
                10 -> {
                    text2()
                    scoreboard.update()
                }
                15 -> {
                    scoreboard.resetAllScores()
                    scoreboard.update()
                }
//                20 -> {
//                    text1()
//                    scoreboard.update()
//                }
//                30 -> {
//                    scoreboard.resetAllScores()
//                    text2()
//                    scoreboard.update()
//                }
                20 -> {
                    scoreboard.setScore(0, "0 - switch1", 0)
                    scoreboard.update()
                    scoreboard.setScore(1, "1 - switch1", 1)
                    scoreboard.update()
                }
                25 -> {
                    scoreboard.setScore(0, "0 - switch2", 0)
                    scoreboard.update()
                    scoreboard.setScore(1, "1 - switch2", 1)
                    scoreboard.update()
                }
            }
        }, 20, 20)

        api.server.scheduler.scheduleDelayedTask(api, {
            scoreboard.addPlayer(p)
        }, 60)
    }
}