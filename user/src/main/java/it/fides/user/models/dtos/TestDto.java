package it.fides.user.models.dtos;

public class TestDto {

    private String userResponse;
    private String bankResponse;

    public TestDto() {}

    public String getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(String userResponse) {
        this.userResponse = userResponse;
    }

    public String getBankResponse() {
        return bankResponse;
    }

    public void setBankResponse(String bankResponse) {
        this.bankResponse = bankResponse;
    }
}
