import json.DecodeException;
import json.Decoder;
import model.Location;
import org.junit.jupiter.api.*;
import service.Service;

import java.io.File;
import java.io.IOException;

public class JSONTest {
    @Test
    @DisplayName("Parsing locations")
    public void testParseLocations() throws IOException, DecodeException {
        String locationsJsonString = Service.parseFileToString("json" + File.separator + "locations.json");
        Decoder jsonDecoder = new Decoder();
        Location[] locations = jsonDecoder.parseLocations(locationsJsonString);

        Assertions.assertEquals("Canada", locations[0].getCountry());
        Assertions.assertEquals(-90f, locations[locations.length-1].getLatitude());
    }
}
