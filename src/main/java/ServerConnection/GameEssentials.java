package ServerConnection;

import controller.MapController;
import model.Government;

import java.io.Serializable;
import java.util.ArrayList;

public record GameEssentials(ArrayList<Cell> myHolds, MapController mapController,
                             ArrayList<Government> governments) implements Serializable {
}
