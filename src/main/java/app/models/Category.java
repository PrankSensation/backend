package app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(length = 36)
    @JsonView(view.Id.class)
    private String uuid;

    @JsonView(view.Category.class)
    private String title;

    @Column(columnDefinition = "text")
    private String threat_law;

    @Column(columnDefinition = "text")
    private String threat_market;

    private double weight = 1.0;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView(view.Category.class)
    @JsonIgnoreProperties("category")
    private List<SubCategory> subCategories = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "category")
    private List<StrengthOrWeakness> strengthOrWeaknesses = new ArrayList<>();

    public Category() {
    }

    public Category(String title, String threat_law, String threat_market) {
        this.title = title;
        this.threat_law = threat_law;
        this.threat_market = threat_market;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThreat_law() {
        return threat_law;
    }

    public void setThreat_law(String threat_law) {
        this.threat_law = threat_law;
    }

    public String getThreat_market() {
        return threat_market;
    }

    public void setThreat_market(String threat_market) {
        this.threat_market = threat_market;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void addSubCategory(SubCategory subCategory) {
        subCategory.setCategory(this);
        this.subCategories.add(subCategory);
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    public List<StrengthOrWeakness> getStrengthOrWeaknesses() {
        return strengthOrWeaknesses;
    }

    public void addStrengthOrWeakness(StrengthOrWeakness strengthOrWeakness) {
        strengthOrWeakness.setCategory(this);
        this.strengthOrWeaknesses.add(strengthOrWeakness);
    }

    public void setStrengthOrWeaknesses(List<StrengthOrWeakness> strengthOrWeaknesses) {
        this.strengthOrWeaknesses = strengthOrWeaknesses;
    }
}


