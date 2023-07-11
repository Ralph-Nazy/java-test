package com.adaptris.interview;

import java.util.Objects;

/**
 * <p>
 * A key-value pair of <code>String</code>s. Used mainly to get round
 * <i>Castor</i>'s <code>Map</code> problems. 
 * </p>
 */
public class KeyValuePair {

  private String pairKey;
  private String pairValue;

  public KeyValuePair() {
    this.setKey("Test");
    this.setValue("Test");
  }

  public KeyValuePair(String key, String value) {
    this.setKey(key);
    this.setValue(value);
  }

  public void setKey(String key) {
      pairKey = key;
  }

  public String getKey() {
    return pairKey;
  }

  
  public void setValue(String value) {
      pairValue = value;
  }

  public String getValue() {
    return pairValue;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    KeyValuePair other = (KeyValuePair) obj;
    return Objects.equals(pairKey, other.pairKey) && Objects.equals(pairValue, other.pairValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pairKey, pairValue);
  }

  public String toString() {
    return "key [" + pairKey + "] value [" + pairValue + "]";
  }
}
