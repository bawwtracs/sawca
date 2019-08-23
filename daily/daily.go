package daily

import (
	"net/http"
	"strconv"
	"strings"

	"github.com/gin-gonic/gin"
	"gopkg.in/mgo.v2"
	"gopkg.in/mgo.v2/bson"
)

// Daily is just daily
type Daily struct {
	ID      bson.ObjectId `bson:"_id" json:"id" form:"id"`
	Date    string        `bson:"date" json:"date" form:"date"`
	KeyWord string        `bson:"keyWord" json:"kerword" form:"kerword"`
	Title   string        `bson:"title" json:"title" form:"title" binding:"required"`
	Content string        `bson:"content" json:"content" form:"content" binding:"required"`
}

// Regist regist the model api
// the uri should be a generic interface
func Regist(r *gin.Engine, collection *mgo.Collection) {

	// create a daily
	r.POST("/daily", func(c *gin.Context) {
		daily := &Daily{}
		if c.ShouldBind(daily) != nil {
			c.JSON(http.StatusBadRequest, http.StatusText(http.StatusBadRequest))
		} else {
			daily.ID = bson.NewObjectId()
			if err := collection.Insert(daily); err != nil {
				c.JSON(http.StatusInternalServerError, http.StatusText(http.StatusInternalServerError))
			} else {
				c.JSON(http.StatusOK, daily)
			}
		}
	})

	// update by id
	r.PUT("/daily/:id", func(c *gin.Context) {
		daily := &Daily{}
		if c.ShouldBind(daily) != nil {
			c.JSON(http.StatusBadRequest, http.StatusText(http.StatusBadRequest))
		} else {
			if err := collection.UpdateId(c.Param("id"), daily); err != nil {
				c.JSON(http.StatusInternalServerError, http.StatusText(http.StatusInternalServerError))
			}
		}
	})

	// retrieve by id
	r.GET("/daily/id/:id", func(c *gin.Context) {
		daily := &Daily{}
		if err := collection.FindId(bson.M{"_id": c.Param("id")}).One(daily); err != nil {
			c.JSON(http.StatusNotFound, http.StatusText(http.StatusNotFound))
		} else {
			c.JSON(http.StatusOK, daily)
		}
	})

	// retrieve list
	r.GET("/daily/list", func(c *gin.Context) {
		limit, _ := strconv.Atoi(c.Query("limit"))
		offset, _ := strconv.Atoi(c.Query("offset"))
		total := 233
		result := &[]Daily{}
		sort := strings.Split(c.Query("sort"), ",")
		if err := collection.Find(bson.M{}).Sort(sort...).Skip(offset).Limit(limit).All(result); err != nil {
			c.JSON(http.StatusBadRequest, http.StatusText(http.StatusBadRequest))
		} else {
			c.JSON(http.StatusOK, gin.H{
				"paging": gin.H{
					"limit":  limit,
					"offset": offset,
					"total":  total,
				},
				"data": result,
			})
		}
	})

	// delete by id
	r.DELETE("/dail/:id", func(c *gin.Context) {
		if err := collection.RemoveId(c.Param("id")); err != nil {
			c.JSON(http.StatusNotFound, http.StatusText(http.StatusNotFound))
		} else {
			c.JSON(http.StatusOK, http.StatusText(http.StatusOK))
		}
	})

}
