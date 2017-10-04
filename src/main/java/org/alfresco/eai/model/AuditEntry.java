package org.alfresco.eai.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class AuditEntry implements Serializable {

  private long id;
  private String application;
  private String user;
  private String time;
  private Map<String,String> values;

  public AuditEntry() {
  }

  private AuditEntry(long id,
                     String application,
                     String user,
                     String time,
                     Map<String, String> values) {
    this.id = id;
    this.application = application;
    this.user = user;
    this.time = time;
    this.values = values;
  }

  @Override
  public String toString() {
    return "AuditEntry{" +
        "id=" + id +
        ", application='" + application + '\'' +
        ", user='" + user + '\'' +
        ", time=" + time +
        ", values=" + values +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    AuditEntry that = (AuditEntry) o;

    if (id != that.id) return false;
    if (application != null ? !application.equals(that.application) : that.application != null)
      return false;
    if (user != null ? !user.equals(that.user) : that.user != null) return false;
    if (time != null ? !time.equals(that.time) : that.time != null) return false;
    return values != null ? values.equals(that.values) : that.values == null;
  }

  @Override
  public int hashCode() {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + (application != null ? application.hashCode() : 0);
    result = 31 * result + (user != null ? user.hashCode() : 0);
    result = 31 * result + (time != null ? time.hashCode() : 0);
    result = 31 * result + (values != null ? values.hashCode() : 0);
    return result;
  }

  public long getId() {
    return id;
  }

  public String getApplication() {
    return application;
  }

  public String getUser() {
    return user;
  }

  public String getTime() {
    return time;
  }

  public Map<String, String> getValues() {
    return values;
  }

  public static Builder builder() {
    return new Builder();
  }
  public static class Builder {

    private long id;
    private String application;
    private String user;
    private String time;
    private Map<String, String> values;

    public Builder setId(long id) {
      this.id = id;
      return this;
    }

    public Builder setApplication(String application) {
      this.application = application;
      return this;
    }

    public Builder setUser(String user) {
      this.user = user;
      return this;
    }

    public Builder setTime(String time) {
      this.time = time;
      return this;
    }

    public Builder setValues(Map<String, String> values) {
      this.values = values;
      return this;
    }

    public AuditEntry createAuditEntry() {
      return new AuditEntry(id, application, user, time, values);
    }
  }
}
