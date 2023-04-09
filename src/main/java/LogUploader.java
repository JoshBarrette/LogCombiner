import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

public class LogUploader {
    public static LogsResponse uploadLog(File logFile, String LogName, String mapName, String key) throws IOException {
        // Build the post request
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost("http://logs.tf/upload");
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("title", LogName, ContentType.TEXT_PLAIN);
        builder.addTextBody("map", mapName, ContentType.TEXT_PLAIN);
        builder.addTextBody("key", key, ContentType.TEXT_PLAIN);
        FileBody uploadFilePart = new FileBody(logFile);
        builder.addPart("logfile", uploadFilePart);
        HttpEntity multipart = builder.build();
        uploadFile.setEntity(multipart);

        // Send the request
        CloseableHttpResponse response = httpClient.execute(uploadFile);

        // Get the response as a string
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");

        // Convert the response to a LogsResponse object and return it
        Gson gson = new Gson();
        return gson.fromJson(responseString, LogsResponse.class);
    }
}
