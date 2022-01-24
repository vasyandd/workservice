package work.service.entity;

import java.time.Month;
import java.util.Map;

public class Shipment {

    private Short period;
    private DeliveryStatement deliveryStatement;
    private Map<Month, Short> scheduledShipment;
    private Map<Month, Short> actualShipment;
}
