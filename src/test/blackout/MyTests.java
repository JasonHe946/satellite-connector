package blackout;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import unsw.blackout.BlackoutController;
import unsw.blackout.FileTransferException;
import unsw.response.models.FileInfoResponse;
import unsw.response.models.EntityInfoResponse;
import unsw.utils.Angle;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unsw.utils.MathsHelper.RADIUS_OF_JUPITER;

import java.util.Arrays;

import static blackout.TestHelpers.assertListAreEqualIgnoringOrder;

public class MyTests {
    //make sure relaysatellites start moving the right direction when spawned outside 140-190 region
    @Test
    public void testRelayMovementFromSpawn() {

        // Satellite 1 should move in position direction but Satellite 2 should move in negative direction

        BlackoutController controller = new BlackoutController();

        controller.createSatellite("Satellite1", "RelaySatellite", 100 + RADIUS_OF_JUPITER,
                                Angle.fromDegrees(30));
        controller.createSatellite("Satellite2", "RelaySatellite", 100 + RADIUS_OF_JUPITER,
                                Angle.fromDegrees(240));

        

        
        assertEquals(
                        new EntityInfoResponse("Satellite1", Angle.fromDegrees(30), 100 + RADIUS_OF_JUPITER,
                                        "RelaySatellite"),
                        controller.getInfo("Satellite1"));
        controller.simulate();
        
        assertEquals(new EntityInfoResponse("Satellite1", Angle.fromDegrees(31.23), 100 + RADIUS_OF_JUPITER,
                        "RelaySatellite"), controller.getInfo("Satellite1"));

        assertEquals(new EntityInfoResponse("Satellite2", Angle.fromDegrees(238.77), 100 + RADIUS_OF_JUPITER,
        "RelaySatellite"), controller.getInfo("Satellite2"));

    }
    @Test
    public void testRelayMovementAtEdgeSpawn() {

        // Satellite 1 spawned at threshold 345 should move in positive direction

        BlackoutController controller = new BlackoutController();

        controller.createSatellite("Satellite1", "RelaySatellite", 100 + RADIUS_OF_JUPITER,
                                Angle.fromDegrees(345));

        

        
        assertEquals(
                        new EntityInfoResponse("Satellite1", Angle.fromDegrees(345), 100 + RADIUS_OF_JUPITER,
                                        "RelaySatellite"),
                        controller.getInfo("Satellite1"));
        controller.simulate();
        
        assertEquals(new EntityInfoResponse("Satellite1", Angle.fromDegrees(346.23), 100 + RADIUS_OF_JUPITER,
                        "RelaySatellite"), controller.getInfo("Satellite1"));


    }

    @Test
    public void testTeleportingAtSpawn() {
         // Both Satellites should move anticlockwise despite where they're spawned

        BlackoutController controller = new BlackoutController();

        controller.createSatellite("Satellite1", "TeleportingSatellite", 100 + RADIUS_OF_JUPITER,
                                Angle.fromDegrees(270));
        controller.createSatellite("Satellite2", "TeleportingSatellite", 100 + RADIUS_OF_JUPITER,
        Angle.fromDegrees(90));                        

    
        controller.simulate();
        
        assertEquals(new EntityInfoResponse("Satellite1", Angle.fromDegrees(270.82), 100 + RADIUS_OF_JUPITER,
                        "TeleportingSatellite"), controller.getInfo("Satellite1"));
        assertEquals(new EntityInfoResponse("Satellite2", Angle.fromDegrees(90.82), 100 + RADIUS_OF_JUPITER,
        "TeleportingSatellite"), controller.getInfo("Satellite2"));

    }
}
