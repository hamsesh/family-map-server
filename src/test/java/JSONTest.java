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
        Assertions.assertEquals(-90f, locations[locations.length - 1].getLatitude());
    }

    @Test
    @DisplayName("Parsing names")
    public void testParseNames() throws IOException, DecodeException {
        String fnamesJsonString = Service.parseFileToString("json" + File.separator + "fnames.json");
        Decoder jsonDecoder = new Decoder();
        String[] fnames = jsonDecoder.parseNames(fnamesJsonString);

        Assertions.assertEquals("Jolynn", fnames[0]);
        Assertions.assertEquals("Ludivina", fnames[fnames.length - 1]);
        Assertions.assertEquals(147, fnames.length);
    }

}
