package gov.usbr.wq.dataaccess.model;

import gov.usbr.wq.dataaccess.MerlinTimeSeriesDataAccess;
import gov.usbr.wq.dataaccess.http.HttpAccessException;
import gov.usbr.wq.dataaccess.http.TokenContainer;
import gov.usbr.wq.dataaccess.json.Measure;
import gov.usbr.wq.dataaccess.mapper.MerlinObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class MeasureWrapperTest extends ModelTest
{
    @Test
    void testValidate() throws IOException
    {
        String expectedJson = readFileAsString("measurements/Sac._BC_Shasta Boundary Flow.json");
        List<MeasureWrapper> measureWrappers = MerlinObjectMapper.mapJsonToListOfObjectsUsingClass(expectedJson, Measure.class).stream()
                .map(MeasureWrapper::new)
                .collect(toList());
        List<String> measuresJson = new ArrayList<>();
        for(MeasureWrapper measureWrapper : measureWrappers)
        {
            measuresJson.add(measureWrapper.toJsonString());
        }
        String json = "[" + String.join(",", measuresJson) + "]";
        assertEquals(json, expectedJson);

        for(MeasureWrapper measureWrapper : measureWrappers)
        {
            assertEquals(measureWrapper.getType(), "Auto");
        }

    }

    @Test
    void testJsonUnwrapped() throws IOException {
        String expectedJson = readFileAsString("measurements/Sac._BC_Shasta Boundary Flow.json");
        List<MeasureWrapper> measureWrappers = MerlinObjectMapper.mapJsonToListOfObjectsUsingClass(expectedJson, Measure.class).stream()
                .map(MeasureWrapper::new)
                .collect(toList());
        String json = MerlinObjectMapper.mapObjectListToJson(measureWrappers);
        assertEquals(json, expectedJson);
    }
}
