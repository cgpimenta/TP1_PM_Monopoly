package Game;

/**
 * Created by cgpimenta on 09/04/17.
 */
public class Property extends BoardPosition {

    private double purchaseValue;
    private double rentValue;
    private PropertyType propertyType;
    private int ownerId;

    public Property(double purchaseValue, double rentTax, int propertyType) {
        // Call superclass constructor (property has position type = 3):
        super(3);

        this.purchaseValue = purchaseValue;
        this.rentValue = purchaseValue * rentTax / 100;
        this.ownerId = 0;   // Id 0 = Bank

        switch (propertyType) {
            case 1:
                this.propertyType = PropertyType.HOUSE;
                break;
            case 2:
                this.propertyType = PropertyType.SHOP;
                break;
            case 3:
                this.propertyType = PropertyType.INDUSTRY;
                break;
            case 4:
                this.propertyType = PropertyType.HOTEL;
                break;
            case 5:
                this.propertyType = PropertyType.HOSPITAL;
                break;
            default:
                this.propertyType = null;
        }
    }

    public double getPurchaseValue() {
        return this.purchaseValue;
    }

    public double getRentValue() {
        return this.rentValue;
    }

    public PropertyType getPropertyType() {
        return this.propertyType;
    }

    public int getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

}
