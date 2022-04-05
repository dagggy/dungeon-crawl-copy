package com.example.dungeaoncrawler.logic.status;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CharacterAttributes {
    private final String AttributeName;
    private int AttributeValue;

    public CharacterAttributes(String attributeName, int attributeValue) {
        AttributeName = attributeName;
        AttributeValue = attributeValue;
    }

    public StringProperty getAttributeName() {
        return new SimpleStringProperty(AttributeName);
    }


    public IntegerProperty getAttributeValue() {
        return new SimpleIntegerProperty(AttributeValue);
    }

    public void setAttributeValue(int attributeValue) {
        AttributeValue = attributeValue;
    }
}
