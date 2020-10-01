package main

import (
	"fmt"
	"math"
	"math/rand"
	"os"
	"strconv"
	"sync"
)

func calculate(size int, dots int, threadId int, ratio chan <- float32, group *sync.WaitGroup) {
	defer group.Done()

	fmt.Printf("thread %d start\n", threadId)
	inCount := 0

	for dot := 0; dot < dots; dot++ {
		xPos := rand.Intn(size)
		yPos := rand.Intn(size)

		distance := float32(math.Sqrt(math.Pow(float64(xPos), 2) + math.Pow(float64(yPos), 2)))

		if distance <= float32(size) {
			inCount++
		}
	}
	ratio <- float32(inCount) / float32(dots)
}


func main() {
	ratio := make(chan float32, 4)
	wg := &sync.WaitGroup{}

	wg.Add(4)
	squareSize, _ := strconv.Atoi(os.Args[2])
	dotsCount, _ := strconv.Atoi(os.Args[1])

	for i := 0; i < 4; i++ {
		go calculate(squareSize / 2, dotsCount, i, ratio, wg)
	}

	wg.Wait()
	close(ratio)

	result := float32(0)

	for value := range ratio {
		result += value
	}


	fmt.Printf("result: %f", result)
}
