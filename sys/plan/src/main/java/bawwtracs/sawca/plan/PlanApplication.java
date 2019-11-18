package bawwtracs.sawca.plan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("bawwtracs.sawca.plan.mapper")
public class PlanApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlanApplication.class, args);
    }

}
