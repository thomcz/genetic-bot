package de.genetic.bot.simulation

import java.awt.Point
import sun.audio.AudioPlayer.player



class Bomb (val position : Point) {
    var countdown = 10
    val radius = 500
    val damage = 50
    var isExploded = false
    fun Tick() {
        countdown--
        if (countdown <=0) {
            isExploded = true
        }
    }

    fun explode(player: Player, zombies:Array<Zombie>) {
        isExploded = true
        if (inRadius(player.position)) {
            player.damage(damage)
        }

        for (z in zombies) {
            if (inRadius(z.position)) {
                z.kill()
            }
        }
    }

    fun inRadius(position: Point) : Boolean {
        return (this.position.distance(position) < radius)
    }

    override fun toString(): String {
        return String.format("%d %d %d\n", position.x, position.y, countdown)
    }
}