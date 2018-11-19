Example plugin usage
-------------

````
    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        val p = e.player

        val scoreboard = FakeScoreboard()
        val obj = Objective("test", ObjectiveCriteria("dummy", true))

        val dobj = DisplayObjective(
                obj,
                ObjectiveSortOrder.DESCENDING,
                ObjectiveDisplaySlot.SIDEBAR
        )

        obj.displayName = TextFormat.YELLOW.toString() + "Game" + TextFormat.WHITE + "Team"
        obj.setScore(1, "${TextFormat.RED} ---------------  ", 6)
        obj.setScore(2, "cau", 5)
        obj.setScore(3, "cau 1", 4)
        obj.setScore(4, "cau 2", 3)
        obj.setScore(5, "cau 3", 2)
        obj.setScore(6, "cau 4", 1)

        scoreboard.objective = dobj

        server.scheduler.scheduleDelayedTask(this, {
            scoreboard.addPlayer(p)
        }, 60)

        server.scheduler.scheduleDelayedRepeatingTask(this, {
            obj.setScore(5, SimpleDateFormat("mm:ss").format(Date(System.currentTimeMillis())), 12)
            scoreboard.update()
        }, 80, 20)
    }
````