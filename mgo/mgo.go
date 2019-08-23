package mgo

import (
	"log"
	"sawca/conf"
	"time"

	"gopkg.in/mgo.v2"
)

var session *mgo.Session
var database *mgo.Database

// Init pool
func Init() {
	config := &conf.MgoConf{}
	err := config.Load("./conf/mgo.json", config)
	if err != nil {
		panic(err)
	}
	dialInfo := &mgo.DialInfo{
		Addrs:     []string{config.Hosts},
		Direct:    false,
		Timeout:   time.Second * 1,
		PoolLimit: 4096, // Session.SetPoolLimit
	}
	session, err = mgo.DialWithInfo(dialInfo)
	if err != nil {
		log.Println(err.Error())
	}
	session.SetMode(mgo.Monotonic, true)
	database = session.DB(config.Database)
}

// GetSession return config session
func GetSession() *mgo.Session {
	return session
}

// GetDataBase return config database
func GetDataBase() *mgo.Database {
	return database
}

// GetCollection return collection
func GetCollection(name string) *mgo.Collection {
	return database.C(name)
}

// GetErrNotFound ...
func GetErrNotFound() error {
	return mgo.ErrNotFound
}
