package conf

import (
	"encoding/json"
	"io/ioutil"
)

// MgoConf is mongodb database configuration
type MgoConf struct {
	Hosts    string
	Database string
}

// Load conf json
func (*MgoConf) Load(filename string, v interface{}) error {
	// curPath, _ := filepath.Abs("./")
	// switch os := runtime.GOOS; os {
	// case "windows":
	// 	curPath += "\\" + filename
	// case "linux":
	// 	curPath += "/" + filename
	// }
	// curPath = strings.Replace(curPath, "\\", "/", -1)
	data, err := ioutil.ReadFile(filename)
	if err != nil {
		return err
	}
	return json.Unmarshal(data, &v)
}
