package gov.usbr.wq.dataaccess.http;

import java.util.Objects;

public final class ApiConnectionInfo
{
    private final String _apiRoot;

    /**
     * ApiConnectionInfo object that houses the URL api root. Example usage: new ApiConnectionInfo("https://www.example.com")
     *
     * @param apiRoot - apiRoot String. 'https://www.example.com' is equivalent to 'https://www.example.com/'
     */
    public ApiConnectionInfo(String apiRoot)
    {
        _apiRoot = apiRoot;
    }

    public String getApiRoot()
    {
        return _apiRoot;
    }

    private String removeLastSlash(String apiRoot)
    {
        String retVal = apiRoot;
        if(apiRoot.endsWith("/"))
        {
            int index = apiRoot.lastIndexOf("/");
            retVal = apiRoot.substring(0, index);
        }
        return retVal;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        ApiConnectionInfo that = (ApiConnectionInfo) o;
        return Objects.equals(removeLastSlash(_apiRoot), removeLastSlash(that._apiRoot));
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(removeLastSlash(_apiRoot));
    }
}
