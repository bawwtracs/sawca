package sawca.support.account.rest;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.UpdateResult;
import com.purgeteam.dispose.starter.Result;
import com.purgeteam.dispose.starter.exception.category.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/acc")
public class ACCRest {

    @Resource
    private MongoTemplate mongoTemplate;

    @GetMapping("/avail/email/{email}")
    public boolean checkEmailAvail(@PathVariable("email") String email) {
        Query query = Query.query(Criteria.where("email").is(email));
        return mongoTemplate.count(query, "account") <= 0;
    }

    @GetMapping("/avail/username/{username}")
    public boolean checkUsernameAvail(@PathVariable String username) {
        Query query = Query.query(Criteria.where("username").is(username));
        return mongoTemplate.count(query, "account") <= 0;
    }

    @PutMapping("/{id}/head")
    public long register(@RequestBody JSONObject jsonObject, @PathVariable(name = "id") String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = Update.update("head", jsonObject.get("head"));
        UpdateResult updateResult = mongoTemplate.upsert(query, update, "account");
        return updateResult.getModifiedCount();
    }

    @PostMapping("/register")
    public JSONObject register(@RequestBody JSONObject jsonObject) {
        String email = jsonObject.getString("email");
        String username = jsonObject.getString("username");
        Query query = Query.query(Criteria.where("username").is(username));
        JSONObject account = mongoTemplate.findOne(query, JSONObject.class, "account");
        if (account != null) {
            throw new BusinessException("801", "This username has been registered");
        }
        query = Query.query(Criteria.where("email").is(email));
        account = mongoTemplate.findOne(query, JSONObject.class, "account");
        if (account != null) {
            throw new BusinessException("802", "This email has been registered");
        }
        account = mongoTemplate.insert(jsonObject, "account");
        accountFilter(account);
        return account;
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody JSONObject jsonObject, HttpSession session) {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        if (StringUtils.isBlank(username)) {
            throw new BusinessException("803", "empty username");
        }
        if (StringUtils.isBlank(password)) {
            throw new BusinessException("803", "empty password");
        }
        Query query = Query.query(Criteria.where("username").is(username));
        JSONObject account = mongoTemplate.findOne(query, JSONObject.class, "account");
        if (account == null) {
            return Result.ofFail("804", "User not found");
        } else if (!password.equals(account.getString("password"))) {
            return Result.ofFail("804", "Password error");
        } else {
            session.setAttribute("account", account);
            accountFilter(account);
            return Result.ofSuccess(account);
        }
    }

    @GetMapping("/info")
    public Result<?> getAccount(HttpSession session) throws BusinessException {
        JSONObject account = (JSONObject) session.getAttribute("account");
        if (account == null) {
            return Result.ofFail("805", "Need login");
        }
        accountFilter(account);
        return Result.ofSuccess(account);
    }

    private void accountFilter(JSONObject account) {
        account.remove("password");
    }
}
