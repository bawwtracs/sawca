package main

import (
	"fmt"
	"sawca/diary"
	"sawca/mgo"
	"sawca/route"
)

func main() {

	mgo.Init()

	fmt.Println("to run")

	r := route.Setup()

	diary.Regist(r, mgo.GetCollection("diary"))

	r.Run(":80")
}
