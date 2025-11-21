package kg.mega.kindergarten.models.dtos;

public class ChildGroupHistoryDebtDto{
    private Long childId;
    private double debtAmount;

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }

    public double getDebtAmount() {
        return debtAmount;
    }

    public void setDebtAmount(double debtAmount) {
        this.debtAmount = debtAmount;
    }
}
