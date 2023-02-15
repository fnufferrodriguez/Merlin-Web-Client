package gov.usbr.wq.dataaccess.http;

public final class ApiConnectionInfo
{
    private final String _apiRoot;

    public ApiConnectionInfo(String apiRoot)
    {
        _apiRoot = apiRoot;
    }

    public String getApiRoot()
    {
        return _apiRoot;
    }
}
