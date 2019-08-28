package main

import (
	"sawca/diary"
	"sawca/mgo"
	"sawca/route"
)

func main() {

	mgo.Init()

	r := route.Setup()

	diary.Regist(r, mgo.GetCollection("diary"))

	r.Run(":80")
}
