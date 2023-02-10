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

    public void withQualityVersionID(Integer qvID)
    {
        _qualityVersionID = qvID;
    }

    public void withQualityVersionName(String qvName)
    {
        _qualityVersionName = qvName;
    }

    public void withQualityVersionDescription(String qvDescription)
    {
        _qualityVersionDescription = qvDescription;
    }

    public void withQualityVersionActive(Boolean qvActive)
    {
        _qualityVersionActive = qvActive;
    }

    public void withQualityVersionQueryHistory(Boolean qvQueryHistory)
    {
        _qualityVersionQueryHistory = qvQueryHistory;
    }

    public void withQualityVersionUI(Integer qvUI)
    {
        _qualityVersionUI = qvUI;
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
