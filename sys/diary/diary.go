package main

import (
	"context"
	"strconv"

	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"

	"github.com/gin-gonic/gin"
)

// Diary diary
type Diary struct {
	ID      primitive.ObjectID `bson:"_id" form:"id" json:"id"`
	Title   string             `bson:"title,omitempty" form:"title,omitempty" json:"title,omitempty"`
	Content string             `bson:"content,omitempty" form:"content,omitempty" json:"content,omitempty"`
	Time    int64              `bson:"time,omitempty" form:"time,omitempty" json:"time,omitempty"`
}

// DiaryRegist regist diary api
func DiaryRegist(r *gin.Engine, collection *mongo.Collection) {

	// create one
	r.POST("/diary", func(c *gin.Context) {
		diary := &Diary{}
		if err := c.ShouldBind(diary); err != nil {
			c.JSON(200, gin.H{
				"err": err.Error(),
			})
		} else {
			res, _ := collection.InsertOne(context.Background(), diary)
			c.JSON(200, gin.H{
				"id": res.InsertedID,
			})
		}
	})

	// update by id
	r.PUT("/diary/:id", func(c *gin.Context) {
		diary := &Diary{}
		id, _ := primitive.ObjectIDFromHex(c.Param("id"))
		if err := c.ShouldBind(diary); err != nil {
			c.JSON(200, gin.H{
				"err": err.Error(),
			})
		} else {
			diary.ID = id
			data, _ := bson.Marshal(diary)
			setM := &bson.M{}
			bson.Unmarshal(data, setM)
			res, _ := collection.UpdateOne(context.Background(), bson.M{"_id": id}, bson.M{"$set": setM})
			c.JSON(200, gin.H{
				"update": res.ModifiedCount,
			})
		}
	})

	// retrieve one
	r.GET("/diary/:id", func(c *gin.Context) {
		diary := &Diary{}
		id, _ := primitive.ObjectIDFromHex(c.Param("id"))
		if err := collection.FindOne(context.Background(), bson.D{primitive.E{Key: "_id", Value: id}}).Decode(diary); err != nil {
			c.JSON(500, gin.H{
				"err": err.Error(),
			})
		} else {
			c.JSON(200, gin.H{
				"diary": diary,
			})
		}
	})

	// retrieve all
	r.GET("/diaries/all", func(c *gin.Context) {
		if cursor, err := collection.Find(context.Background(), bson.D{}, options.Find().SetSort(bson.M{"time": -1})); err != nil {
			c.JSON(500, gin.H{
				"err": err.Error(),
			})
		} else {
			diaries := &[]Diary{}
			cursor.All(context.Background(), diaries)
			c.JSON(200, gin.H{
				"diaries": diaries,
			})
		}
	})

	// retrieve pagination
	r.GET("/diaries", func(c *gin.Context) {
		limit, _ := strconv.ParseInt(c.DefaultQuery("limit", "0"), 10, 64)
		offset, _ := strconv.ParseInt(c.DefaultQuery("offset", "0"), 10, 64)
		if cursor, err := collection.Find(context.Background(), bson.M{}, options.Find().SetSkip(offset), options.Find().SetLimit(limit), options.Find().SetSort(bson.M{"time": -1})); err != nil {
			c.JSON(500, gin.H{
				"err": err.Error(),
			})
		} else {
			diaries := &[]Diary{}
			cursor.All(context.Background(), diaries)
			c.JSON(200, gin.H{
				"diaries": diaries,
			})
		}
	})

	// delete by id
	r.DELETE("/diary/:id", func(c *gin.Context) {
		id, _ := primitive.ObjectIDFromHex(c.Param("id"))
		if res, err := collection.DeleteOne(context.Background(), bson.M{"_id": id}); err != nil {
			c.JSON(500, gin.H{
				"err": err.Error(),
			})
		} else {
			c.JSON(200, gin.H{
				"delete": res.DeletedCount,
			})
		}
	})
}
