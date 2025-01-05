package network.dto;

import java.io.Serializable;

public class SellRequest implements Serializable {

    String name;
    int playerId;
    int sellerId;
    double price;

    public SellRequest(int playerId, int sellerId, double price) {
        this.playerId = playerId;
        this.sellerId = sellerId;
        this.price = price;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {return  name;}
}
