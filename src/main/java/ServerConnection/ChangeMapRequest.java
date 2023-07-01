package ServerConnection;

import model.building.Enums.BuildingsDetails;

import java.io.Serializable;

public record ChangeMapRequest(BuildingsDetails buildingsDetails, Cell dropPosition) implements Serializable {
}
