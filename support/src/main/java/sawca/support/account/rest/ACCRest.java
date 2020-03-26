package sawca.support.account.rest;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.purgeteam.dispose.starter.exception.category.BusinessException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Log4j2
@RestController
@RequestMapping("/api/acc")
public class ACCRest {

    @Resource
    private MongoTemplate mongoTemplate;

    @PostMapping("/register")
    public JSONObject register(@RequestBody JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String confirm = jsonObject.getString("confirm");
        if (password == null || !password.equals(confirm)) {
            throw new BusinessException("800", "password check failed");
        }
        Query query = new Query();
        query.addCriteria(new Criteria().and("username").is(username));
        JSONObject account = mongoTemplate.findOne(query, JSONObject.class, "account");
        if (account != null) {
            throw new BusinessException("801", "this username has been registered");
        }
        return mongoTemplate.insert(jsonObject, "account");
    }

    @PostMapping("/login")
    public JSONObject login(@RequestBody JSONObject jsonObject, HttpSession session) {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        if (StringUtils.isBlank(username)) {
            throw new BusinessException("803", "empty username");
        }
        Query query = new Query();
        query.addCriteria(new Criteria().and("username").is(username));
        JSONObject account = mongoTemplate.findOne(query, JSONObject.class, "account");
        if (account == null) {
            throw new BusinessException("804", "user not found");
        } else if (!password.equals(account.getString("password"))) {
            throw new BusinessException("804", "password error");
        } else {
            session.setAttribute("account", account);
            accountFilter(account);
            return account;
        }
    }

    @GetMapping("/info")
    public JSONObject getAccount(HttpSession session) throws BusinessException {
        JSONObject account = (JSONObject) session.getAttribute("account");
        if (account == null) {
            throw new BusinessException("805", "need login");
        }
        accountFilter(account);
        return account;
    }

    private void accountFilter(JSONObject account) {
        account.remove("password");
    }
}
