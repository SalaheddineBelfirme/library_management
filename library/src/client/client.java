package client;

public class client {

    private  int memberNumber;
    private  String name;


    public int getMemberNumber() {
        return memberNumber;
    }

    public String getName() {
        return name;
    }

    public void setMemberNumber(int memberNumber) {
        this.memberNumber = memberNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public client(int memberNumber, String name) {
        this.memberNumber = memberNumber;
        this.name = name;
    }
}
