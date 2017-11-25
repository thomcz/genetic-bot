package de.genetic.bot.simulation

import java.awt.Point

class Map (val width : Int, val height :Int) {
    lateinit var player: Player
    val zombies: ArrayList<Zombie> = ArrayList()
    val bombs: ArrayList<Bomb> = ArrayList()

    fun addPlayer(player: Player) {
        if (isInMap(player.position)) {
            this.player = player
        }
    }

    fun addZombie(zombie: Zombie) {
        if (isInMap(zombie.position)) {
            zombies.add(zombie)
        }
    }

    fun addBomb(bomb: Bomb) {
        if (isInMap(bomb.position)) {
            bombs.add(bomb)
        }
    }

    fun isInMap(position : Point) : Boolean {
        return (position.x in 0..(width - 1) && position.y >= 0 && position.y < height)
    }



}