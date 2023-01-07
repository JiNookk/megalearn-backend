package jinookk.ourlms.models.vos.kakao;

public class AmountVO {
    private Integer total, tax_free, vat, point, discount;

    public AmountVO() {
    }

    public AmountVO(Integer total, Integer tax_free, Integer vat, Integer point, Integer discount) {
        this.total = total;
        this.tax_free = tax_free;
        this.vat = vat;
        this.point = point;
        this.discount = discount;
    }

    public static AmountVO fake(Integer total) {
        return new AmountVO(total, 2000, 200, 0, 0);
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getTax_free() {
        return tax_free;
    }

    public Integer getVat() {
        return vat;
    }

    public Integer getPoint() {
        return point;
    }

    public Integer getDiscount() {
        return discount;
    }
}
