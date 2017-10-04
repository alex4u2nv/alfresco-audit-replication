package org.alfresco.eai.model;

import java.util.Map;

public class TicketModel {
  private Map<String,String> data;

  public TicketModel() {
  }

  public TicketModel(Map<String, String> data) {
    this.data = data;
  }

  public Map<String, String> getData() {
    return data;
  }

  public void setData(Map<String, String> data) {
    this.data = data;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TicketModel that = (TicketModel) o;

    return data != null ? data.equals(that.data) : that.data == null;
  }

  @Override
  public int hashCode() {
    return data != null ? data.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "TicketModel{" +
        "data=" + data +
        '}';
  }
}
