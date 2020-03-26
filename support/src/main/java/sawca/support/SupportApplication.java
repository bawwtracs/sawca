package sawca.support;

import com.purgeteam.dispose.starter.annotation.EnableGlobalDispose;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableGlobalDispose
@SpringBootApplication
@MapperScan(basePackages = {"sawca.support.image.mapper", "sawca.support.office.mapper", "sawca.support.im.mapper", "sawca.support.system.mapper", "sawca.support.vocabulary.mapper"})
public class SupportApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(SupportApplication.class, args);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**")
                .addResourceLocations("file:image/");
    }
}
