package main

import (
	"context"
	"time"

	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

// GetClient mongodb connect
func GetClient(uri string) (*mongo.Client, error) {
	opt := options.Client().ApplyURI(uri)
	opt.SetLocalThreshold(3 * time.Second)  //只使用与mongo操作耗时小于3秒的
	opt.SetMaxConnIdleTime(5 * time.Second) //指定连接可以保持空闲的最大毫秒数
	opt.SetMaxPoolSize(200)                 //使用最大的连接数
	client, err := mongo.Connect(context.Background(), opt)
	return client, err
}
