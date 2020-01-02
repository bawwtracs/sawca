package sawca.support.office.rest;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

@Log4j2
@RestController
@RequestMapping("/api")
public class OfficeRest {

    @PostMapping("/onlyoffice/callback")
    public String documentEditorCallback(HttpServletRequest request) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(request.getInputStream()).useDelimiter("\\A");
            String body = scanner.hasNext() ? scanner.next() : "";
            JSONObject jsonObject = JSONObject.parseObject(body);

            String downloadUri = (String) jsonObject.get("url");
            URL url = new URL(downloadUri);
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            /* pre */
            String attachId = request.getParameter("attachId");
            String filePath = "/uploadfile/2020/oa/docx/1/2/5a36007a7acb46dc85c9db3529ecb7b6.docx";
            String dataRealPath = "F:/workspace/zqoa/zqoaNew/src/main/webapp";
            File file = new File(dataRealPath + filePath);

            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                int read;
                final byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1) {
                    fileOutputStream.write(bytes, 0, read);
                }
                fileOutputStream.flush();
            }
//            Integer status = jsonObject.getInteger("status");
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject result = new JSONObject();
        result.put("error", "0");
        return result.toJSONString();
    }

}
