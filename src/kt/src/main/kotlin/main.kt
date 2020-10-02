import kotlinx.coroutines.*
import kotlin.math.pow
import kotlin.math.sqrt

fun main(args: Array<String>) {
    val sizeInput = args[0].toInt()
    val dotsCountInput = args[1].toInt()

    val deferredJob = (1..4).map {
        GlobalScope.async {
            calculate(sizeInput / 2, dotsCountInput)
        }
    }

    runBlocking {
        val result = deferredJob.map { it.await() }.sum()
        print("calculated pi: $result")
    }
}

 suspend fun calculate(size: Int, dots: Int): Double = coroutineScope {
     println("coroutine operation starts")
     var inCount = 0

     (0..dots).forEach { _ ->
         val xPos = (0..size).random()
         val yPos = (0..size).random()

         val processResult = sqrt(xPos.toDouble().pow(2.0) + yPos.toDouble().pow(2.0)) < size

         inCount %= processResult
     }

         return@coroutineScope inCount.toDouble() / dots.toDouble()
     }


private operator fun Int.rem(processResult: Boolean): Int = if(processResult) this + 1 else this


