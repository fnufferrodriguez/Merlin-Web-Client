package gov.usbr.wq.dataaccess.model;

import gov.usbr.wq.dataaccess.MerlinTimeSeriesDataAccess;
import gov.usbr.wq.dataaccess.http.HttpAccessException;
import gov.usbr.wq.dataaccess.http.TokenContainer;
import gov.usbr.wq.dataaccess.json.Template;
import gov.usbr.wq.dataaccess.mapper.MerlinObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class TemplateWrapperTest extends ModelTest
{
    @Test
    void testValidate() throws IOException, HttpAccessException
    {
        String expectedJson = readFileAsString("templates/all_templates.json");
        List<TemplateWrapper> templateWrappers = MerlinObjectMapper.mapJsonToListOfObjectsUsingClass(expectedJson, Template.class).stream()
                .map(TemplateWrapper::new)
                .collect(toList());
        List<String> templatesJson = new ArrayList<>();
        for(TemplateWrapper templateWrapper : templateWrappers)
        {
            templatesJson.add(templateWrapper.toJsonString());
        }
        String json = "[" + String.join(",", templatesJson) + "]";
        assertEquals(json, expectedJson);
    }

    @Test
    void testJsonUnwrapped() throws IOException
    {
        String expectedJson = readFileAsString("templates/all_templates.json");
        List<TemplateWrapper> templateWrappers = MerlinObjectMapper.mapJsonToListOfObjectsUsingClass(expectedJson, Template.class).stream()
                .map(TemplateWrapper::new)
                .collect(toList());
        String json = MerlinObjectMapper.mapObjectListToJson(templateWrappers);
        assertEquals(json, expectedJson);
    }
}
