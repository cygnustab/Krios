package com.cygnus.krios;


import java.util.ArrayList;
import java.util.List;

public class UserData {

    String entity_id;
    String entity_type_id;
    String attribute_set_id;
    String parent_id;
    String created_at;
    String updated_at;
    String path;
    String position;
    String level;
    String children_count;
    String name;
    String is_active;
    String type_id;
    String sku;
    String meta_title;
    String meta_description;
    String material;
    String product_finish;
    String product_dimension;
    String product_unit;
    String description;
    String short_description;
    String meta_keyword;
    int regular_price_with_tax;
    int regular_price_without_tax;
    int final_price_with_tax;

    public double getQty() {
        return Qty;
    }

    public void setQty(double qty) {
        Qty = qty;
    }

    int final_price_without_tax;
    double Qty;
    String is_saleable;
    String image_url;
    List<Temp_Data> tier_price = new ArrayList<Temp_Data>();

    public int getRegular_price_with_tax() {
        return regular_price_with_tax;
    }

    public void setRegular_price_with_tax(int regular_price_with_tax) {
        this.regular_price_with_tax = regular_price_with_tax;
    }

    public int getRegular_price_without_tax() {
        return regular_price_without_tax;
    }

    public void setRegular_price_without_tax(int regular_price_without_tax) {
        this.regular_price_without_tax = regular_price_without_tax;
    }

    public int getFinal_price_with_tax() {
        return final_price_with_tax;
    }

    public void setFinal_price_with_tax(int final_price_with_tax) {
        this.final_price_with_tax = final_price_with_tax;
    }

    public int getFinal_price_without_tax() {
        return final_price_without_tax;
    }

    public void setFinal_price_without_tax(int final_price_without_tax) {
        this.final_price_without_tax = final_price_without_tax;
    }

    public List<Temp_Data> getTier_price() {
        return tier_price;
    }

    public void setTier_price(List<Temp_Data> tier_price) {
        this.tier_price = tier_price;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getMeta_title() {
        return meta_title;
    }

    public void setMeta_title(String meta_title) {
        this.meta_title = meta_title;
    }

    public String getMeta_description() {
        return meta_description;
    }

    public void setMeta_description(String meta_description) {
        this.meta_description = meta_description;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getProduct_finish() {
        return product_finish;
    }

    public void setProduct_finish(String product_finish) {
        this.product_finish = product_finish;
    }

    public String getProduct_dimension() {
        return product_dimension;
    }

    public void setProduct_dimension(String product_dimension) {
        this.product_dimension = product_dimension;
    }

    public String getProduct_unit() {
        return product_unit;
    }

    public void setProduct_unit(String product_unit) {
        this.product_unit = product_unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getMeta_keyword() {
        return meta_keyword;
    }

    public void setMeta_keyword(String meta_keyword) {
        this.meta_keyword = meta_keyword;
    }


    public String getIs_saleable() {
        return is_saleable;
    }

    public void setIs_saleable(String is_saleable) {
        this.is_saleable = is_saleable;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }


    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChildren_count() {
        return children_count;
    }

    public void setChildren_count(String children_count) {
        this.children_count = children_count;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getAttribute_set_id() {
        return attribute_set_id;
    }

    public void setAttribute_set_id(String attribute_set_id) {
        this.attribute_set_id = attribute_set_id;
    }

    public String getEntity_type_id() {
        return entity_type_id;
    }

    public void setEntity_type_id(String entity_type_id) {
        this.entity_type_id = entity_type_id;
    }

    public String getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(String entity_id) {
        this.entity_id = entity_id;
    }


}
