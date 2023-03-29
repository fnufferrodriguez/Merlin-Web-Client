package gov.usbr.wq.dataaccess.model;

import gov.usbr.wq.dataaccess.json.QualityVersions;
import gov.usbr.wq.dataaccess.mapper.MerlinObjectMapper;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by Bryson Spilman
 */
public final class QualityVersionWrapper
{
    private final QualityVersions _qualityVersions;
    public QualityVersionWrapper(QualityVersions qualityVersions)
    {
        _qualityVersions = qualityVersions;
    }

    public Integer getQualityVersionID()
    {
        return _qualityVersions.getQvID();
    }

    public String getQualityVersionName()
    {
        return _qualityVersions.getQvName();
    }

    public String getQualityVersionDescription()
    {
        return _qualityVersions.getQvDescription();
    }

    public Boolean isQualityVersionActive()
    {
        return _qualityVersions.isQvActive();
    }

    public Boolean isQualityVersionQueryHistory()
    {
        return _qualityVersions.isQvQueryHistory();
    }

    public Integer getQualityVersionUI()
    {
        return _qualityVersions.getQvUI();
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
        QualityVersionWrapper that = (QualityVersionWrapper) o;
        return Objects.equals(_qualityVersions, that._qualityVersions);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(_qualityVersions);
    }

    @Override
    public String toString()
    {
        return _qualityVersions.toString();
    }

    public String toJsonString() throws IOException
    {
        return MerlinObjectMapper.mapObjectToJson(_qualityVersions);
    }

}
