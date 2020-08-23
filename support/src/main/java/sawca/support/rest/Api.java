package sawca.support.rest;

import com.alibaba.fastjson.JSONObject;
import com.purgeteam.dispose.starter.Result;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Api {

    @Resource
    private MongoTemplate mongoTemplate;

    @GetMapping("{collection}/list/acc/{accid}")
    public List<JSONObject> list(@PathVariable("collection") String collection, @PathVariable("accid") String accid) {
        Query query = Query.query(Criteria.where("accid").is(accid));
        return mongoTemplate.find(query, JSONObject.class, collection);
    }

    @PostMapping("{collection}")
    public JSONObject create(@PathVariable("collection") String collection, @RequestBody JSONObject jsonObject) {
        return mongoTemplate.insert(jsonObject, collection);
    }

    @DeleteMapping("{collection}/{id}")
    public Result<?> delete(@PathVariable("collection") String collection, @PathVariable("id") String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, collection);
        return Result.ofSuccess();
    }
}
