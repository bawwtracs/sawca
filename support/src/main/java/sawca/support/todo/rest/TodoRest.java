package sawca.support.todo.rest;

import com.alibaba.fastjson.JSONObject;
import com.purgeteam.dispose.starter.Result;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/api/todo")
public class TodoRest {

    @Resource
    private MongoTemplate mongoTemplate;

    @GetMapping("/list/acc/{accid}")
    public List<JSONObject> list(@PathVariable("accid") String accid) {
        Query query = Query.query(Criteria.where("accid").is(accid));
        return mongoTemplate.find(query, JSONObject.class, "todo");
    }

    @PostMapping("/create")
    public JSONObject add(@RequestBody JSONObject jsonObject) {
        return mongoTemplate.insert(jsonObject, "todo");
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, "todo");
        return Result.ofSuccess();
    }

}
