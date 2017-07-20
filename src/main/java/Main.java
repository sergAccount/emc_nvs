public class Main {

    public static void main(String[] args) {
        String url = "http://emccoinrpc:secret_password@127.0.0.1:6662";
        EmcRPCClientImpl client = new EmcRPCClientImpl("127.0.0.1:6662",
                "emccoinrpc", "secret_password");
        String param = "inno.stc.xfile";

        client.sendFileToChain("lo", param);
    }



}
