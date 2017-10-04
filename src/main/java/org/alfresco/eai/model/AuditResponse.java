package org.alfresco.eai.model;

import java.io.Serializable;
import java.util.List;

public class AuditResponse implements Serializable{
  private  int count;
  private List<AuditEntry> entries;

  public AuditResponse() {
  }

  private AuditResponse(int count, List<AuditEntry> entries) {
    this.count = count;
    this.entries = entries;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    AuditResponse response = (AuditResponse) o;

    if (count != response.count) return false;
    return entries != null ? entries.equals(response.entries) : response.entries == null;
  }

  @Override
  public int hashCode() {
    int result = count;
    result = 31 * result + (entries != null ? entries.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "AuditResponse{" +
        "count=" + count +
        ", entries=" + entries +
        '}';
  }

  public int getCount() {
    return count;
  }

  public List<AuditEntry> getEntries() {
    return entries;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private int count;
    private List<AuditEntry> entries;

    public Builder setCount(int count) {
      this.count = count;
      return this;
    }

    public Builder setEntries(List<AuditEntry> entries) {
      this.entries = entries;
      return this;
    }

    public AuditResponse createAuditResponse() {
      return new AuditResponse(count, entries);
    }
  }
}
