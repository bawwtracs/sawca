package main

import (
	"fmt"
	"log"
	"math/rand"
	"net/http"
	"strconv"
	"strings"

	"github.com/gin-gonic/gin"
	"gopkg.in/mgo.v2"
	"gopkg.in/mgo.v2/bson"
)

// Cors 跨域
func Cors() gin.HandlerFunc {
	return func(c *gin.Context) {
		method := c.Request.Method               //请求方法
		origin := c.Request.Header.Get("Origin") //请求头部
		var headerKeys []string                  // 声明请求头keys
		for k := range c.Request.Header {
			headerKeys = append(headerKeys, k)
		}
		headerStr := strings.Join(headerKeys, ", ")
		if headerStr != "" {
			headerStr = fmt.Sprintf("access-control-allow-origin, access-control-allow-headers, %s", headerStr)
		} else {
			headerStr = "access-control-allow-origin, access-control-allow-headers"
		}
		if origin != "" {
			c.Writer.Header().Set("Access-Control-Allow-Origin", "*")
			c.Header("Access-Control-Allow-Origin", "*")                                       // 这是允许访问所有域
			c.Header("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE,UPDATE") //服务器支持的所有跨域请求的方法,为了避免浏览次请求的多次'预检'请求
			//  header的类型
			c.Header("Access-Control-Allow-Headers", "Authorization, Content-Length, X-CSRF-Token, Token,session,X_Requested_With,Accept, Origin, Host, Connection, Accept-Encoding, Accept-Language,DNT, X-CustomHeader, Keep-Alive, User-Agent, X-Requested-With, If-Modified-Since, Cache-Control, Content-Type, Pragma")
			//  允许跨域设置                                                                                                      可以返回其他子段
			c.Header("Access-Control-Expose-Headers", "Content-Length, Access-Control-Allow-Origin, Access-Control-Allow-Headers,Cache-Control,Content-Language,Content-Type,Expires,Last-Modified,Pragma,FooBar") // 跨域关键设置 让浏览器可以解析
			c.Header("Access-Control-Max-Age", "172800")                                                                                                                                                           // 缓存请求信息 单位为秒
			c.Header("Access-Control-Allow-Credentials", "false")                                                                                                                                                  //  跨域请求是否需要带cookie信息 默认设置为true
			c.Set("content-type", "application/json")                                                                                                                                                              // 设置返回格式是json
		}

		//放行所有OPTIONS方法
		if method == "OPTIONS" {
			c.JSON(http.StatusOK, "Options Request!")
		}
		// 处理请求
		c.Next() //  处理请求
	}
}

//Daily test
type Daily struct {
	ID      bson.ObjectId `json:"id" bson:"_id,omitempty" form:"id"`
	Keyword string        `json:"keyword" bson:"keyword" form:"keyword"`
	Title   string        `json:"title" bson:"title" form:"title"`
	Content string        `json:"content" bson:"content" form:"content"`
	Date    string        `json:"date" bson:"date" form:"date"`
}

func setupRouter() *gin.Engine {
	// Disable Console Color
	// gin.DisableConsoleColor()
	r := gin.Default()

	r.Use(Cors())

	// Ping test
	r.GET("/ping", func(c *gin.Context) {
		c.String(http.StatusOK, "pong")
	})

	// create one
	r.GET("/daily/put/:title/:content/:date/:keyword", func(c *gin.Context) {
		daily := &Daily{ID: bson.NewObjectId(), Keyword: c.Param("keyword"), Title: c.Param("title"), Content: c.Param("content"), Date: c.Param("date")}
		create(daily)
		c.String(http.StatusOK, "objectId %s", daily.ID)
	})

	// get one
	r.GET("/daily/get/id/:id", func(c *gin.Context) {
		daily := queryOne(c.Param("id"))
		c.String(http.StatusOK, "objectId %v", daily)
	})

	// list
	r.GET("/test/list", func(c *gin.Context) {
		page, _ := strconv.Atoi(c.Query("page"))
		size, _ := strconv.Atoi(c.Query("size"))
		result := []TestDoc{}
		count := getCount()
		queryList(&result, page, size)
		c.JSON(http.StatusOK, gin.H{
			"data":  result,
			"code":  0,
			"msg":   "",
			"count": count,
		})
	})

	//list
	r.GET("/test/gen/:size", func(c *gin.Context) {
		size, _ := strconv.Atoi(c.Param("size"))
		createTestData(size)
		c.String(http.StatusOK, "sucess")
	})

	return r
}

// TestDoc test
type TestDoc struct {
	ID       bson.ObjectId `json:"id" bson:"_id,omitempty" form:"id"`
	Username string        `json:"username" bson:"username" form:"username"`
	Sign     string        `json:"sign" bson:"sign" form:"sign"`
	Sex      int           `json:"sex" bson:"sex" form:"sex"`
	City     int           `json:"city" bson:"city" form:"city"`
	Dept     int           `json:"dept" bson:"dept" form:"dept"`
	Logins   int           `json:"logins" bson:"logins" form:"logins"`
	Age      int           `json:"age" bson:"age" form:"age"`
}

func createTestData(size int) {
	session, err := mgo.Dial("localhost") // open an connection -> Dial function
	if err != nil {
		panic(err)
	}
	defer session.Close() // session must close at the end

	session.SetMode(mgo.Monotonic, true) // Optional. Switch the session to a monotonic behavior.

	c := session.DB("test").C("test_table")
	changeInfo, err := c.RemoveAll(nil)
	fmt.Println(changeInfo)
	for i := 0; i < size; i++ {
		username, sign := "user_"+strconv.Itoa(i+1), "sign-"+strconv.Itoa(i+1)
		testDoc := &TestDoc{ID: bson.NewObjectId(), Username: username, Sign: sign, Sex: rand.Int(), City: rand.Int(), Dept: rand.Int(), Logins: rand.Int(), Age: rand.Int()}
		err = c.Insert(testDoc)
	}
}

func getCount() int {
	session, err := mgo.Dial("localhost") // open an connection -> Dial function
	if err != nil {
		panic(err)
	}
	defer session.Close() // session must close at the end

	session.SetMode(mgo.Monotonic, true) // Optional. Switch the session to a monotonic behavior.

	c := session.DB("test").C("test_table")
	res, _ := c.Find(bson.M{}).Count()
	return res
}

func create(daily *Daily) {
	session, err := mgo.Dial("localhost") // open an connection -> Dial function
	if err != nil {
		panic(err)
	}
	defer session.Close() // session must close at the end

	session.SetMode(mgo.Monotonic, true) // Optional. Switch the session to a monotonic behavior.

	c := session.DB("sawca").C("daily")
	err = c.Insert(daily)
	if err != nil {
		log.Fatal(err)
	}
}

func queryOne(id string) Daily {
	session, err := mgo.Dial("localhost") // open an connection -> Dial function
	if err != nil {
		panic(err)
	}
	defer session.Close() // session must close at the end

	session.SetMode(mgo.Monotonic, true) // Optional. Switch the session to a monotonic behavior.

	c := session.DB("sawca").C("daily")
	result := Daily{}
	err = c.Find(bson.M{"_id": bson.ObjectIdHex(id)}).One(&result)
	if err != nil {
		log.Fatal(err)
	}
	return result
}

func queryList(result *[]TestDoc, page int, size int) {
	session, err := mgo.Dial("localhost") // open an connection -> Dial function
	if err != nil {
		panic(err)
	}
	defer session.Close() // session must close at the end

	session.SetMode(mgo.Monotonic, true) // Optional. Switch the session to a monotonic behavior.

	c := session.DB("test").C("test_table")
	c.Find(bson.M{}).Sort("_id").Skip(page * size).Limit(size).All(result)
}

func queryAll() {

}

func main() {
	r := setupRouter()
	// Listen and Server in 0.0.0.0:8080
	r.Run(":80")
}
