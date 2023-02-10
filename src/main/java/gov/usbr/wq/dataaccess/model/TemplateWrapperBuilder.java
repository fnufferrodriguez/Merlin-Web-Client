package gov.usbr.wq.dataaccess.model;

import gov.usbr.wq.dataaccess.json.Template;

public final class TemplateWrapperBuilder
{
    private Integer _dprID;
    private String _dprName;

    public void withDprID(Integer dprID)
    {
        _dprID = dprID;
    }

    public void withDprName(String dprName)
    {
        _dprName = dprName;
    }

    public TemplateWrapper build()
    {
        Template template = new Template();
        template.setDprID(_dprID);
        template.setDprName(_dprName);
        return new TemplateWrapper(template);
    }
}
