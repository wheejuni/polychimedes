import kotlinx.coroutines.*
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val sizeInput = args[0].toInt()
    val dotsCountInput = args[1].toInt()

    val asyncStartTime = System.currentTimeMillis()

    val deferredJob = (1..4).map {
        GlobalScope.async {
            calculate(it, sizeInput / 2, dotsCountInput)
        }
    }

    runBlocking {
        val result = deferredJob.map { it.await() }.sum()
        println("calculated pi: $result")
    }

    val finishedAsyncTime = System.currentTimeMillis()

    println("elapsed async time: ${finishedAsyncTime - asyncStartTime} ms")

    val elapsedNonParallel = measureTimeMillis {
        val result = (1..4).map {
            runBlocking {
                calculate(it, sizeInput / 2, dotsCountInput)
            }
        }.sum()

        println("non-parallel result: $result")
    }

    println("nonparallel elapsed time: $elapsedNonParallel ms")
}

 suspend fun calculate(threadId: Int, size: Int, dots: Int): Double = coroutineScope {
     println("coroutine #$threadId operation starts")
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


