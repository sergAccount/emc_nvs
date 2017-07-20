import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.LinkedHashMap;

public class EmcRPCClientImpl implements EmcRPCClient {

    private String url;
    private String user;
    private String password;

    public EmcRPCClientImpl(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public boolean sendFileToChain(String localFilename, String chainName) {

        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("emccoinrpc", "secret_password".toCharArray());
            }
        });

        try {
            Path path = Paths.get(localFilename);
            JsonRpcHttpClient client = new JsonRpcHttpClient(new URL("http://127.0.0.1:6662"));
            String stringBase64 = Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(localFilename)));

            Object[] param = {chainName, stringBase64, 1};
            client.invoke("name_new", param);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean readFileFromChain(String chainName, String localFilename) {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("emccoinrpc", "secret_password".toCharArray());
            }
        });

        try {
            JsonRpcHttpClient client = new JsonRpcHttpClient(new URL("http://127.0.0.1:6662"));
            LinkedHashMap response = client.invoke("name_show",
                    new Object[] {chainName}, LinkedHashMap.class);

            if (response.containsKey("value")) {
                Path localFile = Files.createFile(Paths.get(localFilename));
                Files.newOutputStream(localFile).write(Base64.getDecoder().decode(response.get("value").toString()));
                System.out.println("Create file " + localFile.toString());
            }

        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return false;
        }

        return true;
    }
}
