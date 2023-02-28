package gov.usbr.wq.dataaccess.model;

import gov.usbr.wq.dataaccess.json.Template;

public final class TemplateWrapperBuilder
{
    private Integer _dprID;
    private String _dprName;

    public TemplateWrapperBuilder withDprID(Integer dprID)
    {
        _dprID = dprID;
        return this;
    }

    public TemplateWrapperBuilder withDprName(String dprName)
    {
        _dprName = dprName;
        return this;
    }

    public TemplateWrapper build()
    {
        Template template = new Template();
        template.setDprID(_dprID);
        template.setDprName(_dprName);
        return new TemplateWrapper(template);
    }
}
