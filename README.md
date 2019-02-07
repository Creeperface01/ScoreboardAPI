Example plugin usage
-

##### Simple scoreboard example

```kotlin
    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        val p = e.player

        val sb = builder().build()
        sb.setDisplayName("${TextFormat.GREEN}Test Scoreboard")
        sb.setScore(1, "${TextFormat.RED} ---------------  ", 1)
        sb.setScore(2, "test 2", 2)
        sb.setScore(3, "test 3", 3)
        sb.setScore(4, "test 4", 4)
        sb.setScore(5, "test 5", 5)
        sb.setScore(6, "test 6", 6)

        sb.addPlayer(p)
    }
```

##### More complex scoreboard example

```kotlin
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
```