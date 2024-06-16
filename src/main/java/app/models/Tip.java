package app.models;
public class Tip {
    private String category;

    private String SubCategory;

    private String tip;

    public Tip(String category, String subCategory, String tip) {
        this.category = category;
        SubCategory = subCategory;
        this.tip = tip;
    }

    public String getCategory() {
        return category;
    }

    public String getSubCategory() {
        return SubCategory;
    }

    public String getTip() {
        return tip;
    }
}
