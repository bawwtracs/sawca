package sawca.support.account.rest;

import com.alibaba.fastjson.JSONObject;
import com.purgeteam.dispose.starter.exception.category.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
        Query query = new Query().addCriteria((new Criteria().and("email").is(email)));
        return mongoTemplate.count(query, "account") <= 0;
    }

    @GetMapping("/avail/username/{username}")
    public boolean checkUsernameAvail(@PathVariable String username) {
        Query query = new Query().addCriteria((new Criteria().and("username").is(username)));
        return mongoTemplate.count(query, "account") <= 0;
    }

    @PostMapping("/register")
    public JSONObject register(@RequestBody JSONObject jsonObject) {
        String email = jsonObject.getString("email");
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        if (!checkPass(password)) {
            throw new BusinessException("800", "Your password must be between 8 and 30 characters");
        }
        Query query = new Query();
        query.addCriteria(new Criteria().and("username").is(username));
        JSONObject account = mongoTemplate.findOne(query, JSONObject.class, "account");
        if (account != null) {
            throw new BusinessException("801", "This username has been registered");
        }
        query = new Query();
        query.addCriteria((new Criteria().and("email").is(email)));
        account = mongoTemplate.findOne(query, JSONObject.class, "account");
        if (account != null) {
            throw new BusinessException("802", "This email has been registered");
        }
        account = mongoTemplate.insert(jsonObject, "account");
        accountFilter(account);
        return account;
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
            throw new BusinessException("804", "User not found");
        } else if (!password.equals(account.getString("password"))) {
            throw new BusinessException("804", "Password error");
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
            throw new BusinessException("805", "Need login");
        }
        accountFilter(account);
        return account;
    }

    private void accountFilter(JSONObject account) {
        account.remove("password");
    }

    private boolean checkPass(String password) {
        int l = password.length();
        return l >= 8 && l <= 30;
    }
}
