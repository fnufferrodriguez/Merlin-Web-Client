package gov.usbr.wq.dataaccess.model;

import gov.usbr.wq.dataaccess.json.QualityVersions;

public final class QualityVersionWrapperBuilder
{
    private Integer _qualityVersionID;
    private String _qualityVersionName;
    private String _qualityVersionDescription;
    private Boolean _qualityVersionActive;
    private Boolean _qualityVersionQueryHistory;
    private Integer _qualityVersionUI;

    public QualityVersionWrapperBuilder withQualityVersionID(Integer qvID)
    {
        _qualityVersionID = qvID;
        return this;
    }

    public QualityVersionWrapperBuilder withQualityVersionName(String qvName)
    {
        _qualityVersionName = qvName;
        return this;
    }

    public QualityVersionWrapperBuilder withQualityVersionDescription(String qvDescription)
    {
        _qualityVersionDescription = qvDescription;
        return this;
    }

    public QualityVersionWrapperBuilder withQualityVersionActive(Boolean qvActive)
    {
        _qualityVersionActive = qvActive;
        return this;
    }

    public QualityVersionWrapperBuilder withQualityVersionQueryHistory(Boolean qvQueryHistory)
    {
        _qualityVersionQueryHistory = qvQueryHistory;
        return this;
    }

    public QualityVersionWrapperBuilder withQualityVersionUI(Integer qvUI)
    {
        _qualityVersionUI = qvUI;
        return this;
    }
    
    public QualityVersionWrapper build()
    {
        QualityVersions qualityVersion = new QualityVersions();
        qualityVersion.setQvID(_qualityVersionID);
        qualityVersion.setQvActive(_qualityVersionActive);
        qualityVersion.setQvDescription(_qualityVersionDescription);
        qualityVersion.setQvName(_qualityVersionName);
        qualityVersion.setQvUI(_qualityVersionUI);
        qualityVersion.setQvQueryHistory(_qualityVersionQueryHistory);
        return new QualityVersionWrapper(qualityVersion);
    }
}
