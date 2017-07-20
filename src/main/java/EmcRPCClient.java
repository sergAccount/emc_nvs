public interface EmcRPCClient {
    boolean sendFileToChain(String filename, String chainName);
    boolean readFileFromChain(String chainName, String filename);
}
