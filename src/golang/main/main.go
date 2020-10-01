package main

import (
	"fmt"
	"math"
	"math/rand"
	"os"
	"strconv"
)

func calculate(size int, dots int, threadFlag int, ratio chan <- float32) {
	inCount := 0

	for dot := 0; dot < dots; dot++ {
		xPos := rand.Intn(size)
		yPos := rand.Intn(size)

		distance := float32(math.Sqrt(math.Pow(float64(xPos), 2) + math.Pow(float64(yPos), 2)))

		if distance <= float32(size) {
			inCount++
		}
	}
	fmt.Printf("thread %d finished\n", threadFlag)
	ratio <- float32(inCount) / float32(dots)
}

func main() {
	ratio := make(chan float32)

	sum := float32(0)

	squareSize, _ := strconv.Atoi(os.Args[2])
	dotsCount, _ := strconv.Atoi(os.Args[1])

	for i := 0; i < 4; i++ {
		go calculate(squareSize / 2, dotsCount, i, ratio)
		sum = sum + <-ratio
	}

	fmt.Printf("%f", sum)
}
