package cn.wopaipai.event;

public class ChoiceContactAddressEventBus {
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    String address;

    public ChoiceContactAddressEventBus(String address) {
        this.address = address;
    }
}
