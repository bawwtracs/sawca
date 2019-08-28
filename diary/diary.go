package diary

import (
	"net/http"
	"strconv"
	"strings"

	"github.com/gin-gonic/gin"
	"gopkg.in/mgo.v2"
	"gopkg.in/mgo.v2/bson"
)

// Diary is just diary
type Diary struct {
	ID      bson.ObjectId `bson:"_id" json:"id" form:"id"`
	Date    string        `bson:"date" json:"date" form:"date"`
	KeyWord string        `bson:"keyWord" json:"kerword" form:"kerword"`
	Title   string        `bson:"title" json:"title" form:"title" binding:"required"`
	Content string        `bson:"content" json:"content" form:"content" binding:"required"`
	Show    string        `bson:"show" json:"show" form:"show"`
	Iofuu   string        `bson:"iofuu" json:"iofuu" form:"iofuu"`
}

// Regist regist the model api
// the uri should be a generic interface
func Regist(r *gin.Engine, collection *mgo.Collection) {

	// create a diary
	r.POST("/diary", func(c *gin.Context) {
		diary := &Diary{}
		if c.ShouldBind(diary) != nil {
			c.JSON(http.StatusBadRequest, http.StatusText(http.StatusBadRequest))
		} else {
			diary.ID = bson.NewObjectId()
			if err := collection.Insert(diary); err != nil {
				c.JSON(http.StatusInternalServerError, http.StatusText(http.StatusInternalServerError))
			} else {
				c.JSON(http.StatusOK, diary)
			}
		}
	})

	// update by id
	r.PUT("/diary/:id", func(c *gin.Context) {
		diary := &Diary{}
		if c.ShouldBind(diary) != nil {
			c.JSON(http.StatusBadRequest, http.StatusText(http.StatusBadRequest))
		} else {
			if err := collection.UpdateId(bson.ObjectIdHex(c.Param("id")), diary); err != nil {
				c.JSON(http.StatusInternalServerError, http.StatusText(http.StatusInternalServerError))
			}
		}
	})

	// retrieve by id
	r.GET("/diary/:id", func(c *gin.Context) {
		diary := &Diary{}
		if err := collection.FindId(bson.ObjectIdHex(c.Param("id"))).One(diary); err != nil {
			c.JSON(http.StatusNotFound, http.StatusText(http.StatusNotFound))
		} else {
			c.JSON(http.StatusOK, diary)
		}
	})

	// retrieve list
	r.GET("/diaries", func(c *gin.Context) {
		limit, _ := strconv.Atoi(c.Query("limit"))
		offset, _ := strconv.Atoi(c.Query("offset"))
		total, _ := collection.Count()
		result := &[]Diary{}
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
	r.DELETE("/diary/:id", func(c *gin.Context) {
		if err := collection.RemoveId(bson.ObjectIdHex(c.Param("id"))); err != nil {
			c.JSON(http.StatusNotFound, http.StatusText(http.StatusNotFound))
		} else {
			c.JSON(http.StatusOK, http.StatusText(http.StatusOK))
		}
	})

}
