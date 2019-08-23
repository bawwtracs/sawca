package main

import (
	"fmt"
	"sawca/daily"
	"sawca/mgo"
	"sawca/route"
)

func main() {

	mgo.Init()

	fmt.Println("to run")

	r := route.Setup()

	daily.Regist(r, mgo.GetCollection("daily"))

	r.Run(":80")
}
