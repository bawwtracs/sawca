package main

import (
	"github.com/gin-gonic/gin"
)

func main() {

	client, _ := GetClient("mongodb://localhost:27017")
	diaryCollection := client.Database("sawca").Collection("diary")

	router := gin.Default()
	DiaryRegist(router, diaryCollection)
	router.Run()
}
